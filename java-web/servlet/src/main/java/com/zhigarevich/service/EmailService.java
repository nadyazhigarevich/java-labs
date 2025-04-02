package com.zhigarevich.service;

public class EmailService {
    public static void sendVerificationEmail(String email, String verificationLink) {
        // Реальная реализация будет использовать JavaMail API или другой сервис
        System.out.println("Отправка email на " + email + " с ссылкой: " + verificationLink);
        // В реальном приложении здесь будет код отправки email
    }
}