package br.com.algodaodoce.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private Double totalPrice;
    private String status;
    private String paymentMethod;
    private String deliveryMethod;
    
    @Column(length = 4000)
    private String itemsSummary;
    
    private String address;
    private String orderCode;
    
    private LocalDateTime createdAt = LocalDateTime.now();
}
