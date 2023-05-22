package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Класс представляет собой модель фотографии,
 * которая связывается с конкретным товаром (modelGood).
 */
@Entity
public class modelPhoto {

    /**
     * Конструктор по умолчанию.
     */
    public modelPhoto(){}

    /**
     * Уникальный идентификатор фотографии.
     * @return ID_Photo Уникальный идентификатор фотографии.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Photo;

    /**
     * Путь к файлу фотографии в виде массива байтов.
     * @return Photo_Path Путь к файлу фотографии в виде массива байтов.
     */
    @Lob
    private byte[] Photo_Path;

    /**
     * Связь фотографии с конкретным товаром (modelGood).
     * @return good Связанный с фотографией товар (modelGood).
     */
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelGood good;

    /**
     * Геттер для уникального идентификатора фотографии.
     * @return ID_Photo Уникальный идентификатор фотографии.
     */
    public Long getID_Photo() {
        return ID_Photo;
    }

    /**
     * Сеттер для установки уникального идентификатора фотографии.
     * @param ID_Photo Уникальный идентификатор фотографии.
     */
    public void setID_Photo(Long ID_Photo) {
        this.ID_Photo = ID_Photo;
    }

    /**
     * Геттер для получения пути к файлу фотографии в виде массива байтов.
     * @return Photo_Path Путь к файлу фотографии в виде массива байтов.
     */
    public byte[] getPhoto_Path() {
        return Photo_Path;
    }

    /**
     * Сеттер для установки пути к файлу фотографии в виде массива байтов.
     * @param photo_Path Путь к файлу фотографии в виде массива байтов.
     */
    public void setPhoto_Path(byte[] photo_Path) {
        Photo_Path = photo_Path;
    }

    /**
     * Геттер для получения товара (modelGood), связанного с фотографией.
     * @return good Связанный с фотографией товар (modelGood).
     */
    public modelGood getGood() {
        return good;
    }

    /**
     * Сеттер для установки связанного с фотографией товара (modelGood).
     * @param good Связанный с фотографией товар (modelGood).
     */
    public void setGood(modelGood good) {
        this.good = good;
    }

    /**
     * Конструктор для создания новой фотографии.
     * @param ID_Photo Уникальный идентификатор фотографии.
     * @param photo_Path Путь к файлу фотографии в виде массива байтов.
     * @param good Связанный с фотографией товар (modelGood).
     */
    public modelPhoto(Long ID_Photo, @NotNull @NotBlank byte[] photo_Path, modelGood good) {
        this.ID_Photo = ID_Photo;
        Photo_Path = photo_Path;
        this.good = good;
    }
}
