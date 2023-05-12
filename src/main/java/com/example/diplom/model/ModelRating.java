package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class ModelRating {
    public ModelRating(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDRating;

    @NotNull
    @NotBlank
    @Size(min = 10, max = 500)
    private String RatingText;

    @Positive
    @Max(5)
    @Min(1)
    private int RatingValue;

    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelGood good;

    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelUser user;


    public Long getIDRating() {
        return IDRating;
    }

    public void setIDRating(Long IDRating) {
        this.IDRating = IDRating;
    }

    public String getRatingText() {
        return RatingText;
    }

    public void setRatingText(String ratingText) {
        RatingText = ratingText;
    }

    public int getRatingValue() {
        return RatingValue;
    }

    public void setRatingValue(int ratingValue) {
        RatingValue = ratingValue;
    }

    public modelGood getGood() {
        return good;
    }

    public void setGood(modelGood good) {
        this.good = good;
    }

    public modelUser getUser() {
        return user;
    }

    public void setUser(modelUser user) {
        this.user = user;
    }
    public ModelRating(Long IDRating, String ratingText, int ratingValue, modelGood good, modelUser user) {
        this.IDRating = IDRating;
        RatingText = ratingText;
        RatingValue = ratingValue;
        this.good = good;
        this.user = user;
    }

}
