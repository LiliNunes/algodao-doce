package br.com.algodaodoce.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String code;
    
    private int maxUses = 10;
    private int currentUses = 0;
    private double discountPercentage = 0.10;
}
