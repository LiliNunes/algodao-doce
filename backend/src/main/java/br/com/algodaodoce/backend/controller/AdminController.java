package br.com.algodaodoce.backend.controller;

import br.com.algodaodoce.backend.model.Coupon;
import br.com.algodaodoce.backend.model.Stock;
import br.com.algodaodoce.backend.repository.CouponRepository;
import br.com.algodaodoce.backend.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private CouponRepository couponRepository;

    @GetMapping("/inventory")
    public ResponseEntity<List<Stock>> getInventory() {
        return ResponseEntity.ok(stockRepository.findAll());
    }

    @PostMapping("/inventory")
    public ResponseEntity<?> updateInventory(@RequestBody Map<String, Object> payload) {
        String productId = payload.get("productId").toString();
        int available = Integer.parseInt(payload.get("available").toString());
        
        Optional<Stock> sOpt = stockRepository.findByProductId(productId);
        Stock s = sOpt.orElse(new Stock());
        s.setProductId(productId);
        s.setAvailable(available);
        
        return ResponseEntity.ok(stockRepository.save(s));
    }
    
    @GetMapping("/coupons")
    public ResponseEntity<List<Coupon>> getCoupons() {
        return ResponseEntity.ok(couponRepository.findAll());
    }
    
    @PostMapping("/coupons")
    public ResponseEntity<?> saveCoupon(@RequestBody Map<String, Object> payload) {
        String code = payload.get("code").toString().toUpperCase();
        int maxUses = Integer.parseInt(payload.getOrDefault("maxUses", "10").toString());
        double discount = Double.parseDouble(payload.getOrDefault("discountPercentage", "0.10").toString());
        int currentUses = Integer.parseInt(payload.getOrDefault("currentUses", "0").toString());
        
        Optional<Coupon> opt = couponRepository.findByCode(code);
        Coupon c = opt.orElse(new Coupon());
        c.setCode(code);
        c.setMaxUses(maxUses);
        c.setDiscountPercentage(discount);
        c.setCurrentUses(currentUses);
        
        return ResponseEntity.ok(couponRepository.save(c));
    }
}
