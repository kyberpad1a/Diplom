package com.example.diplom.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;

@Component
public class RandomCodeGenerator implements CodeGenerator {
    private final SecureRandom random = new SecureRandom();
    private final int codeLength;

    /**
     * Конструктор класса RandomCodeGenerator.
     * @param codeLength длина строки-кода, передаваемая через аргументы при создании объекта класса.
     */
    public RandomCodeGenerator(@Value("${app.reset.code-length}") int codeLength) {

        this.codeLength = codeLength;
    }

    /**
     * Генерирует случайную строку-код заданной длины.
     * @return Строка-код.
     */
    @Override
    public String generateCode() {
        byte[] bytes = new byte[codeLength];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
