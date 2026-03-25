package br.com.algodaodoce.backend.controller;

import br.com.algodaodoce.backend.model.User;
import br.com.algodaodoce.backend.repository.UserRepository;
import br.com.algodaodoce.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailService emailService;
    
    private String generateCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Optional<User> existingOpt = userRepository.findByEmail(user.getEmail());
        String code = generateCode();
        
        if (existingOpt.isPresent()) {
            User existing = existingOpt.get();
            if (existing.isVerified()) {
                // Return generic 200 immediately (security: anti-enumeration). The user might get a generic email instead.
                System.out.println("Segurança: E-mail já existe [" + existing.getEmail() + "]. Simulando e-mail de aviso.");
            } else {
                existing.setVerificationCode(code);
                userRepository.save(existing);
                emailService.sendVerificationEmail(existing.getEmail(), code);
            }
        } else {
            user.setVerified(false);
            user.setVerificationCode(code);
            userRepository.save(user);
            emailService.sendVerificationEmail(user.getEmail(), code);
        }
        
        // Always return a standard 200 OK without exposing user ID or registration status directly.
        return ResponseEntity.ok(Map.of("message", "Processo de registro iniciado. Valide seu e-mail para continuar."));
    }
    
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String code = payload.get("code");
        Optional<User> uOpt = userRepository.findByEmail(email);
        
        if (uOpt.isPresent() && uOpt.get().getVerificationCode() != null && uOpt.get().getVerificationCode().equals(code)) {
            User u = uOpt.get();
            u.setVerified(true);
            u.setVerificationCode(null);
            userRepository.save(u);
            u.setPassword(null);
            return ResponseEntity.ok(u);
        }
        return ResponseEntity.status(400).body(Map.of("message", "Código inválido ou expirado."));
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        
        // Auto-create Admin if doesn't exist
        if ("admin@algodaodoce.com.br".equals(email) && "admin123".equals(password)) {
            Optional<User> adminOpt = userRepository.findByEmail(email);
            if (adminOpt.isEmpty()) {
                User admin = new User();
                admin.setEmail(email);
                admin.setName("Administrador");
                admin.setPassword(password);
                admin.setPhone("00000000000");
                admin.setVerified(true);
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }
        }
        
        Optional<User> uOpt = userRepository.findByEmail(email);
        if (uOpt.isPresent() && uOpt.get().getPassword().equals(password)) {
            User u = uOpt.get();
            u.setPassword(null);
            return ResponseEntity.ok(u);
        }
        return ResponseEntity.status(401).body(Map.of("message", "E-mail ou senha inválidos."));
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        Optional<User> uOpt = userRepository.findByEmail(email);
        if (uOpt.isPresent()) {
            User u = uOpt.get();
            String code = generateCode();
            u.setVerificationCode(code);
            userRepository.save(u);
            emailService.sendPasswordResetEmail(u.getEmail(), code);
            return ResponseEntity.ok(Map.of("message", "Código enviado."));
        }
        return ResponseEntity.ok(Map.of("message", "Se o e-mail existir, o código foi enviado."));
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        String code = payload.get("code");
        String newPassword = payload.get("password");
        
        Optional<User> uOpt = userRepository.findByEmail(email);
        if (uOpt.isPresent() && uOpt.get().getVerificationCode() != null && uOpt.get().getVerificationCode().equals(code)) {
            User u = uOpt.get();
            u.setPassword(newPassword);
            u.setVerificationCode(null);
            u.setVerified(true);
            userRepository.save(u);
            return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso!"));
        }
        return ResponseEntity.status(400).body(Map.of("message", "Código inválido."));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> uOpt = userRepository.findById(id);
        if (uOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        User existing = uOpt.get();
        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) existing.setName(updatedUser.getName());
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) existing.setEmail(updatedUser.getEmail());
        if (updatedUser.getPhone() != null && !updatedUser.getPhone().isEmpty()) existing.setPhone(updatedUser.getPhone());
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) existing.setPassword(updatedUser.getPassword());
        
        User saved = userRepository.save(existing);
        saved.setPassword(null);
        return ResponseEntity.ok(saved);
    }
}
