package com.example.diplom.api;

import java.awt.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Клиент для выполнения асинхронных REST-запросов.
 */
@SuppressWarnings("serial")
@Service
public class AsyncRestClientService implements Serializable {

    /**
     * Получение всех комментариев асинхронно через REST.
     *
     * @param value значение запроса.
     * @return строку с адресом.
     * @throws JsonProcessingException если происходит ошибка сериализации объекта в JSON.
     */
    public String getAllCommentsAsync(String value) throws JsonProcessingException {

        System.out.println("Настройка получения всех объектов Comment через REST..");
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("query", value);
        String json;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writeValueAsString(Collections.singletonMap("query", value));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        WebClient webClient = WebClient.create();
        String responseJson = webClient.post()
                .uri("https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address?token=014bfdd6619a09ffe416d9dc17a006f35602be47")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(json))
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();

        AddressModel myResponse = objectMapper.readValue(responseJson, AddressModel.class);
        List<Suggestion> suggestions = myResponse.getSuggestions();
        String finalAddress = suggestions.get(0).getValue();
        System.out.println(finalAddress);
        return finalAddress;

    }

}
