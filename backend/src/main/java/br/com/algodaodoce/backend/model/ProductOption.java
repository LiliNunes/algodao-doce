package br.com.algodaodoce.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ProductOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String weight;
    private Double price;
}
