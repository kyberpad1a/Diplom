package com.example.diplom.repo;

import com.example.diplom.model.modelUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс репозитория пользователей. Определяет методы для доступа к данным пользователей в базе данных.
 */
@Repository
public interface UserRepository extends JpaRepository<modelUser, Long> {

    /**
     * Находит пользователя по имени пользователя.
     * @param username имя пользователя.
     * @return найденный пользователь или null.
     */
    modelUser findByUsername(String username);

    /**
     * Находит пользователя по электронной почте.
     * @param email адрес электронной почты.
     * @return найденный пользователь или null.
     */
    modelUser findByEmail(String email);

    /**
     * Проверяет, существует ли активный пользователь с заданным именем пользователя.
     * @param username имя пользователя.
     * @return true если существует активный пользователь с указанным именем пользователя, иначе false.
     */
    boolean existsByActiveIsTrueAndUsername(String username);
}
