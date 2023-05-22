/**
 * Класс, представляющий подсказки при запросе на сервер.
 */
package com.example.diplom.api;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "value",
        "unrestricted_value",
        "data"
})
@Generated("jsonschema2pojo")
public class Suggestion {

    /**
     * Подсказка с основной информацией.
     */
    @JsonProperty("value")
    private String value;

    /**
     * Подсказка с дополнительной информацией.
     */
    @JsonProperty("unrestricted_value")
    private String unrestrictedValue;

    /**
     * Дополнительные данные, связанные с подсказкой.
     */
    @JsonProperty("data")
    @Valid
    private Data data;

    /**
     * Дополнительные параметры в виде `Map`, которые могут быть прочитаны и записаны из/в JSON.
     */
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * Возвращает значение подсказки с основной информацией.
     * @return значение подсказки с основной информацией.
     */
    @JsonProperty("value")
    public String getValue() {
        return value;
    }

    /**
     * Устанавливает значение подсказки с основной информацией.
     * @param value значение, которое необходимо установить для подсказки с основной информацией.
     */
    @JsonProperty("value")
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Возвращает значение подсказки с дополнительной информацией.
     * @return значение подсказки с дополнительной информацией.
     */
    @JsonProperty("unrestricted_value")
    public String getUnrestrictedValue() {
        return unrestrictedValue;
    }

    /**
     * Устанавливает значение подсказки с дополнительной информацией.
     * @param unrestrictedValue значение, которое необходимо установить для подсказки с дополнительной информацией.
     */
    @JsonProperty("unrestricted_value")
    public void setUnrestrictedValue(String unrestrictedValue) {
        this.unrestrictedValue = unrestrictedValue;
    }

    /**
     * Возвращает данные, связанные с подсказкой.
     * @return данные, связанные с подсказкой.
     */
    @JsonProperty("data")
    public Data getData() {
        return data;
    }

    /**
     * Устанавливает данные, связанные с подсказкой.
     * @param data данные, которые необходимо установить для подсказки.
     */
    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
    }

    /**
     * Возвращает карту со всеми дополнительными параметрами в виде (`ключ`, `значение`),
     * которые могут быть прочитаны и записаны из/в JSON.
     * @return карта со всеми дополнительными параметрами в виде (`ключ`, `значение`).
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * Устанавливает новую пару `ключ-значение` для дополнительного параметра с заданным именем.
     * @param name имя дополнительного параметра.
     * @param value значение дополнительного параметра.
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
