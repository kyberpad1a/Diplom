package com.example.diplom.service;


import com.example.diplom.model.modelUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailService {
@Autowired
    private final JavaMailSender mailSender;
    private final String senderAddress;

    public MailService(JavaMailSender mailSender, String senderAddress) {
        this.mailSender = mailSender;
        this.senderAddress = senderAddress;
    }

    public void sendCodeToResetPassword(modelUser user, String resetCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Сброс пароля");
        message.setText("Здравствуйте, " + user.getName()
                + "!\n\nДля сброса пароля введите следующий код: "
                + resetCode);
        message.setFrom(senderAddress);
        mailSender.send(message);
    }
}
