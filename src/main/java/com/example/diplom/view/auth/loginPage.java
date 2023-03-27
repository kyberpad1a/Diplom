package com.example.diplom.view.auth;

import com.example.diplom.view.user.user;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.InputStream;

@Route("/login")
//@CssImport(value = "")
//@Theme("login-rich-content.css")
public class loginPage extends VerticalLayout {
    private final AuthenticationManager authenticationManager;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
    public loginPage(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
       addClassName("login-rich-content");
        //loginForm.getElement().getThemeList().add("dark");
        TextField usernameField = new TextField("Логин");
        PasswordField passwordField = new PasswordField("Пароль");
        Button loginButton = new Button("Войти");
        loginButton.addClickListener(buttonClickEvent->{
            try{
            String username = usernameField.getValue();
            String password = passwordField.getValue();
            final Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
                if(authentication != null ) {

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                    //Access to view by role
                    if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("USER"))) {
                        UI.getCurrent().navigate(user.class);
                    } else
                        if (authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).anyMatch(role -> role.equals("ADMIN"))){
                            UI.getCurrent().navigate(user.class);
                    }
                }}
            catch (AuthenticationException ex) { //
                Notification.show("Неверное имя пользователя или пароль");
            }


        });

        Button registerButton = new Button("Регистрация");
        //registerButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        registerButton.addClickListener(buttonClickEvent -> {
            UI.getCurrent().navigate(registrationPage.class);
        });
        H1 label = new H1("Авторизация");
        RouterLink forgotPwd = new RouterLink("Забыли пароль?", restorePage.class);
        HorizontalLayout btns = new HorizontalLayout(loginButton, registerButton);
        VerticalLayout loginFormLayout = new VerticalLayout(label, usernameField, passwordField, btns, forgotPwd
        );
        loginFormLayout.setHeightFull();
        loginFormLayout.setMargin(true);

        loginFormLayout.setAlignItems(Alignment.CENTER);
        HorizontalLayout loginPageLayout = new HorizontalLayout(
                loginFormLayout
        );
        loginPageLayout.setSizeFull();
        add(loginPageLayout);
        Image backgroundImage = new Image("./images/Screenshot_2.png", "Login Background");
        backgroundImage.setWidth("100%");
        backgroundImage.setHeight("700px");



        VerticalLayout imageLayout = new VerticalLayout(
                backgroundImage
        );
        imageLayout.setSizeFull();
        imageLayout.setAlignItems(Alignment.CENTER);


        loginPageLayout.add(imageLayout);
        loginPageLayout.setFlexGrow(1, imageLayout);

    }

    }




