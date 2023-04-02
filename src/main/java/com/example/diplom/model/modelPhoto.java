package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class modelPhoto {
    public modelPhoto(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Photo;
    @NotNull
    @NotBlank
    private String Photo_Path;
    @ManyToOne(optional = true, cascade = CascadeType.ALL)
    private modelGood good;

    public Long getID_Photo() {
        return ID_Photo;
    }

    public void setID_Photo(Long ID_Photo) {
        this.ID_Photo = ID_Photo;
    }

    public String getPhoto_Path() {
        return Photo_Path;
    }

    public void setPhoto_Path(String photo_Path) {
        Photo_Path = photo_Path;
    }

    public modelGood getGood() {
        return good;
    }

    public void setGood(modelGood good) {
        this.good = good;
    }

    public modelPhoto(Long ID_Photo, String photo_Path, modelGood good) {
        this.ID_Photo = ID_Photo;
        Photo_Path = photo_Path;
        this.good = good;
    }
}
