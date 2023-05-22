package com.example.diplom.service;

import com.example.diplom.model.modelUser;
import com.example.diplom.model.roleEnum;
import com.example.diplom.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Класс, представляющий сервис регистрации для пользователей.
 */
@Service
public class registrationService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Регистрация нового пользователя.
     *
     * @param user новый пользователь, который должен быть зарегистрирован.
     */
    public void register(modelUser user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(roleEnum.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
}
