package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class ModelRating {

    /**
     * Конструктор по умолчанию, создающий экземпляр класса ModelRating
     */
    public ModelRating(){}

    /**
     * Идентификатор рейтинга.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDRating;

    /**
     * Текстовое описание рейтинга.
     */
    @NotNull
    @NotBlank
    @Size(min = 10, max = 500)
    private String RatingText;

    /**
     * Значение рейтинга.
     */
    @Positive
    @Max(5)
    @Min(1)
    private int RatingValue;

    /**
     * Экземпляр класса modelGood.
     */
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelGood good;

    /**
     * Экземпляр класса modelUser.
     */
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelUser user;

    /**
     * Возвращает IDRating.
     * @return IDRating
     */
    public Long getIDRating() {
        return IDRating;
    }

    /**
     * Задает IDRating.
     * @param IDRating идентификатор рейтинга
     */
    public void setIDRating(Long IDRating) {
        this.IDRating = IDRating;
    }

    /**
     * Возвращает RatingText.
     * @return RatingText
     */
    public String getRatingText() {
        return RatingText;
    }

    /**
     * Задает RatingText.
     * @param ratingText текстовое описание рейтинга
     */
    public void setRatingText(String ratingText) {
        RatingText = ratingText;
    }

    /**
     * Возвращает RatingValue.
     * @return RatingValue
     */
    public int getRatingValue() {
        return RatingValue;
    }

    /**
     * Задает RatingValue.
     * @param ratingValue значение рейтинга
     */
    public void setRatingValue(int ratingValue) {
        RatingValue = ratingValue;
    }

    /**
     * Возвращает экземпляр класса modelGood.
     * @return good
     */
    public modelGood getGood() {
        return good;
    }

    /**
     * Задает экземпляр класса modelGood.
     * @param good экземпляр класса modelGood
     */
    public void setGood(modelGood good) {
        this.good = good;
    }

    /**
     * Возвращает экземпляр класса modelUser.
     * @return user
     */
    public modelUser getUser() {
        return user;
    }

    /**
     * Задает экземпляр класса modelUser.
     * @param user экземпляр класса modelUser
     */
    public void setUser(modelUser user) {
        this.user = user;
    }

    /**
     * Конструктор класса ModelRating, создающий экземпляр класса ModelRating с заданными параметрами.
     * @param IDRating идентификатор рейтинга
     * @param ratingText текстовое описание рейтинга
     * @param ratingValue значение рейтинга
     * @param good экземпляр класса modelGood
     * @param user экземпляр класса modelUser
     */
    public ModelRating(Long IDRating, String ratingText, int ratingValue, modelGood good, modelUser user) {
        this.IDRating = IDRating;
        RatingText = ratingText;
        RatingValue = ratingValue;
        this.good = good;
        this.user = user;
    }

}

