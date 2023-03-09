package com.example.diplom.config;

import com.vaadin.flow.component.Component;
import org.springframework.stereotype.Service;

@Service
public class AuthHandler {
    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {

    }

    public class AuthException extends Exception {

    }

    private final UserRepository userRepository;


}
