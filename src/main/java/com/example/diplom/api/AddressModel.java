package com.example.diplom.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Модель адреса, используемая в API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "suggestions"
})
@Generated("jsonschema2pojo")
public class AddressModel {

    /**
     * Список подсказок адресов.
     */
    @JsonProperty("suggestions")
    @Valid
    private List<Suggestion> suggestions;

    /**
     * Дополнительные свойства адреса, не представленные явно в классе.
     */
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * Возвращает список подсказок адресов.
     *
     * @return Список подсказок адресов.
     */
    @JsonProperty("suggestions")
    public List<Suggestion> getSuggestions() {
        return suggestions;
    }

    /**
     * Устанавливает список подсказок адресов.
     *
     * @param suggestions Список подсказок адресов.
     */
    @JsonProperty("suggestions")
    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }

    /**
     * Возвращает дополнительные свойства адреса, не представленные явно в классе.
     *
     * @return Дополнительные свойства адреса.
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * Устанавливает дополнительное свойство адреса.
     *
     * @param name  Имя свойства.
     * @param value Значение свойства.
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
