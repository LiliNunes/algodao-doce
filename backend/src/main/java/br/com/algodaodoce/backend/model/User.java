package br.com.algodaodoce.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @Column(unique = true)
    private String email;
    
    private String password;
    private String phone;
    
    private boolean isVerified = false;
    private String verificationCode;
    
    private String role = "USER";
}
