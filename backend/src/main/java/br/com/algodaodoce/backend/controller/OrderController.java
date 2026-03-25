package br.com.algodaodoce.backend.controller;

import br.com.algodaodoce.backend.model.Coupon;
import br.com.algodaodoce.backend.model.Order;
import br.com.algodaodoce.backend.model.Stock;
import br.com.algodaodoce.backend.model.User;
import br.com.algodaodoce.backend.repository.CouponRepository;
import br.com.algodaodoce.backend.repository.OrderRepository;
import br.com.algodaodoce.backend.repository.StockRepository;
import br.com.algodaodoce.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private CouponRepository couponRepository;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderRepository.findByUserIdOrderByCreatedAtDesc(userId);
        return ResponseEntity.ok(orders);
    }
    
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> payload) {
        Long userId = null;
        if (payload.get("userId") != null) {
            userId = Long.parseLong(payload.get("userId").toString());
        } else {
            return ResponseEntity.badRequest().body("Usuário é obrigatório para fechar pedido na plataforma.");
        }
        
        Optional<User> uOpt = userRepository.findById(userId);
        if(uOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Usuário não encontrado.");
        }
        
        if (payload.containsKey("items")) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) payload.get("items");
            for (Map<String, Object> item : items) {
                String pId = item.get("id").toString();
                int q = Integer.parseInt(item.get("quantity").toString());
                
                Optional<Stock> sOpt = stockRepository.findByProductId(pId);
                Stock s = sOpt.orElse(new Stock());
                s.setProductId(pId);
                s.setAvailable(Math.max(0, s.getAvailable() - q));
                stockRepository.save(s);
            }
        }
        
        if (payload.containsKey("couponCode") && payload.get("couponCode") != null) {
            String cCode = payload.get("couponCode").toString().toUpperCase();
            Optional<Coupon> cOpt = couponRepository.findByCode(cCode);
            cOpt.ifPresent(c -> {
                c.setCurrentUses(c.getCurrentUses() + 1);
                couponRepository.save(c);
            });
        }
        
        Order order = new Order();
        order.setUser(uOpt.get());
        order.setTotalPrice(Double.parseDouble(payload.get("totalPrice").toString()));
        order.setStatus("AGUARDANDO PAGAMENTO");
        order.setPaymentMethod(payload.getOrDefault("paymentMethod", "Pix").toString());
        order.setDeliveryMethod(payload.getOrDefault("deliveryMethod", "Retirar no local").toString());
        order.setItemsSummary(payload.getOrDefault("itemsSummary", "").toString());
        order.setAddress(payload.getOrDefault("address", "").toString());
        
        order.setOrderCode("#AD" + (int)(Math.random() * 90000 + 10000));
        
        if (order.getPaymentMethod().contains("Cartão")) {
            order.setStatus("PAGO - EM PRODUÇÃO");
        }
        
        Order saved = orderRepository.save(order);
        return ResponseEntity.ok(saved);
    }
}
