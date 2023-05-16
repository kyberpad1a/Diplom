package com.example.diplom.view.auth;

import com.example.diplom.model.modelUser;
import com.example.diplom.repo.UserRepository;
import com.example.diplom.service.MailService;
import com.example.diplom.service.RandomCodeGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Route("/restorePage")
public class restorePage extends VerticalLayout {
    private Binder<modelUser> binder = new BeanValidationBinder<>(modelUser.class);
    @Autowired
    UserRepository repository;
    JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    String code;
    String email;
    public restorePage(UserRepository repository, JavaMailSender mailSender) {
        this.repository = repository;
        this.mailSender=mailSender;
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setAlignItems(Alignment.STRETCH);
        VerticalLayout layout1 = new VerticalLayout();
        TextField codeField = new TextField("Код восстановления");
        PasswordField newPassword = new PasswordField("Новый пароль");
        Button confirmBtn = new Button("Сбросить пароль");
        H1 heading = new H1("Восстановление доступа");
        H1 heading1 = new H1("Восстановление доступа");
        layout1.setSizeFull();
        layout1.setAlignItems(Alignment.STRETCH);
        layout1.add(heading1, codeField, newPassword, confirmBtn);
        EmailField mailField = new EmailField("Адрес эл. почты");
        mailField.setPlaceholder("Введите адрес эл. почты");
        mailField.setWidthFull();

        Button sendBtn = new Button("Восстановить");
        layout.add(heading, mailField, sendBtn);
        add(layout);
        sendBtn.addClickListener(buttonClickEvent -> {
            email = mailField.getValue();
            if (repository.findByEmail(email)!=null) {
                MailService mailService = new MailService(mailSender, "ignatovnikita301203@gmail.com");
                RandomCodeGenerator codeGenerator = new RandomCodeGenerator(6);
                code = codeGenerator.generateCode();
                mailService.sendCodeToResetPassword(repository.findByEmail(email), code);
                Notification.show("Письмо с кодом восстановления отправлено на указанный адрес", 3000, Notification.Position.BOTTOM_CENTER);
                remove(layout);
                add(layout1);
            }
            else
                Notification.show("Адрес электронной почты не существует", 3000, Notification.Position.BOTTOM_CENTER);
        });
        binder.forField(newPassword).asRequired("Введите новый пароль").bind(modelUser::getPassword, modelUser::setPassword);
        binder.addStatusChangeListener(e -> confirmBtn.setEnabled(binder.isValid()));
        confirmBtn.addClickListener(buttonClickEvent -> {
           if (Objects.equals(codeField.getValue(), code)){
               modelUser user = repository.findByEmail(email);
                user.setPassword(passwordEncoder.encode(newPassword.getValue()));
                repository.save(user);

               UI.getCurrent().navigate(loginPage.class);
           }
           else Notification.show("Неверный код восстановления", 3000, Notification.Position.BOTTOM_CENTER);
        });

    }


}
