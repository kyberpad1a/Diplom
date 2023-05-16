package com.example.diplom.api;

import java.awt.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonType;
import elemental.json.JsonValue;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SuppressWarnings("serial")
@Service
public class AsyncRestClientService implements Serializable {


    public String getAllCommentsAsync(String value) throws JsonProcessingException {

        System.out.println("Setting up fetching all Comment objects through REST..");
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("query", value);
        String json;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writeValueAsString(Collections.singletonMap("query", value));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }



//        ComboBoxValue comboboxValue = new ComboBoxValue(value);
//        WebClient client = WebClient.create("https://suggestions.dadata.ru");
//        Flux<AddressModel> addressModelFlux = client.post().uri("/suggestions/api/4_1/rs/suggest/address?token=014bfdd6619a09ffe416d9dc17a006f35602be47")
//                .body(BodyInserters.fromFormData("query", value)).retrieve().bodyToFlux(AddressModel.class);
//        RequestHeadersSpec<?> spec = WebClient.create().post().uri("https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/address?token=014bfdd6619a09ffe416d9dc17a006f35602be47");
//        List<AddressModel> addressModels =
//                WebClient.create("https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest").post().uri("/address?token=014bfdd6619a09ffe416d9dc17a006f35602be47")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .body(BodyInserters.fromFormData(formData)).retrieve().bodyToFlux(AddressModel.class).collectList().block();
                        // передаем объект ComboboxValue в качестве тела запроса в JSON-формате
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
//        addressModelFlux.subscribe(addressModel -> {
//
//            System.out.println(addressModel.getSuggestions().stream().toList());
//        });

    }

}
