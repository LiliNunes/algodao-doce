package br.com.algodaodoce.backend.controller;

import br.com.algodaodoce.backend.model.Coupon;
import br.com.algodaodoce.backend.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/coupons")
@CrossOrigin(origins = "*")
public class CouponController {

    @Autowired
    private CouponRepository couponRepository;

    @GetMapping("/validate")
    public ResponseEntity<?> validateCoupon(@RequestParam String code) {
        Optional<Coupon> opt = couponRepository.findByCode(code.toUpperCase());
        if (opt.isPresent()) {
            Coupon c = opt.get();
            if (c.getCurrentUses() < c.getMaxUses()) {
                return ResponseEntity.ok(c);
            } else {
                return ResponseEntity.badRequest().body(Map.of("message", "Cupom esgotado. Já bateu o limite."));
            }
        }
        return ResponseEntity.badRequest().body(Map.of("message", "Cupom inválido ou não encontrado."));
    }
}
