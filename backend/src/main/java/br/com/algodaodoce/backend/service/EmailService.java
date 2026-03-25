package br.com.algodaodoce.backend.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendVerificationEmail(String toEmail, String code) {
        System.out.println("==========================================================");
        System.out.println("📧 SIMULANDO ENVIO DE E-MAIL (MOCK)");
        System.out.println("Para: " + toEmail);
        System.out.println("Assunto: Código de Confirmação Algodão Doce");
        System.out.println("Corpo: Seu código de segurança é: " + code);
        System.out.println("==========================================================");
    }
    
    public void sendPasswordResetEmail(String toEmail, String code) {
        System.out.println("==========================================================");
        System.out.println("📧 SIMULANDO ENVIO DE E-MAIL (MOCK)");
        System.out.println("Para: " + toEmail);
        System.out.println("Assunto: Recuperação de Senha Algodão Doce");
        System.out.println("Corpo: Seu código para redefinir a senha é: " + code);
        System.out.println("==========================================================");
    }
}
