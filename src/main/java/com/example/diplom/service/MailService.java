package com.example.diplom.service;


import com.example.diplom.model.modelUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailService {

    /**
     * JavaMailSender для отправки писем
     */
    @Autowired
    private final JavaMailSender mailSender;

    /**
     * Адрес электронной почты отправителя
     */
    private final String senderAddress;

    /**
     * Конструктор класса MailService
     *
     * @param mailSender        JavaMailSender для отправки писем
     * @param senderAddress     Адрес электронной почты отправителя
     */
    public MailService(JavaMailSender mailSender, String senderAddress) {
        this.mailSender = mailSender;
        this.senderAddress = senderAddress;
    }

    /**
     * Отправляет код для сброса пароля на адрес электронной почты пользователя
     *
     * @param user       модель пользователя
     * @param resetCode  Код для сброса пароля
     */
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

