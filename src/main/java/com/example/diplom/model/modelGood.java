package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;

/**
 * Класс-сущность для хранения информации о товаре.
 */
@Entity
public class modelGood {

    /**
     * Пустой конструктор.
     */
    public modelGood(){}

    /**
     * Идентификатор товара.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDGood;

    /**
     * Название товара.
     */
    @NotNull
    @NotBlank
    private String Good_Name;

    /**
     * Материал, из которого изготовлен товар.
     */
    @NotNull
    @NotBlank
    private String Good_Material;

    /**
     * Цена товара.
     */
    @NotNull
    @Positive
    @Digits(fraction = 2, integer = 5)
    private double Good_Price;

    /**
     * Описание товара.
     */
    @NotNull
    @NotBlank
    @Size(min = 10, max = 1000)
    private String Good_Description;

    /**
     * Флаг для логического удаления товара.
     */
    private boolean logicalFlag;

    /**
     * Франшиза, к которой относится товар.
     */
    @NotNull
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelFranchise franchise;

    /**
     * Категория, к которой относится товар.
     */
    @NotNull
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelCategory category;

    /**
     * Фотографии товара.
     */
    @OneToMany(mappedBy = "good", fetch = FetchType.LAZY)
    private Collection<modelPhoto> usedPhotos;

    /**
     * Заказы товара.
     */
    @OneToMany(mappedBy = "goods", fetch = FetchType.LAZY)
    private Collection<modelOrderGood> orderGoodCollection;

    /**
     * Отзывы на товар.
     */
    @OneToMany(mappedBy = "good", fetch = FetchType.LAZY)
    private Collection<ModelRating> writtenRatings;

    /**
     * Метод для получения идентификатора товара.
     *
     * @return идентификатор товара
     */
    public Long getIDGood() {
        return IDGood;
    }

    /**
     * Метод для установки идентификатора товара.
     *
     * @param IDGood идентификатор товара
     */
    public void setIDGood(Long IDGood) {
        this.IDGood = IDGood;
    }

    /**
     * Метод для получения названия товара.
     *
     * @return название товара
     */
    public String getGood_Name() {
        return Good_Name;
    }

    /**
     * Метод для установки названия товара.
     *
     * @param good_Name название товара
     */
    public void setGood_Name(String good_Name) {
        Good_Name = good_Name;
    }

    /**
     * Метод для получения материала, из которого изготовлен товар.
     *
     * @return материал, из которого изготовлен товар
     */
    public String getGood_Material() {
        return Good_Material;
    }

    /**
     * Метод для установки материала, из которого изготовлен товар.
     *
     * @param good_Material материал, из которого изготовлен товар
     */
    public void setGood_Material(String good_Material) {
        Good_Material = good_Material;
    }

    /**
     * Метод для получения цены товара.
     *
     * @return цена товара
     */
    public double getGood_Price() {
        return Good_Price;
    }

    /**
     * Метод для установки цены товара.
     *
     * @param good_Price цена товара
     */
    public void setGood_Price(double good_Price) {
        Good_Price = good_Price;
    }

    /**
     * Метод для получения описания товара.
     *
     * @return описание товара
     */
    public String getGood_Description() {
        return Good_Description;
    }

    /**
     * Метод для установки описания товара.
     *
     * @param good_Description описание товара
     */
    public void setGood_Description(String good_Description) {
        Good_Description = good_Description;
    }

    /**
     * Возвращает франшизу товара
     * @return Франшиза товара
     */

    public modelFranchise getFranchise() {
        return franchise;
    }

    /**
     * Устанавливает франшизу товара
     * @param franchise Франшиза товара
     */
    public void setFranchise(modelFranchise franchise) {
        this.franchise = franchise;
    }

    /**
     * Возвращает категорию товара
     * @return Категория товара
     */
    public modelCategory getCategory() {
        return category;
    }

    /**
     * Устанавливает категорию товара
     * @param category Категория товара
     */
    public void setCategory(modelCategory category) {
        this.category = category;
    }

    /**
     * Возвращает использованные фотографии товара
     * @return Использованные фотографии товара
     */
    public Collection<modelPhoto> getUsedPhotos() {
        return usedPhotos;
    }

    /**
     * Устанавливает использованные фотографии товара
     * @param usedPhotos Использованные фотографии товара
     */
    public void setUsedPhotos(Collection<modelPhoto> usedPhotos) {
        this.usedPhotos = usedPhotos;
    }

    /**
     * Возвращает флаг логического состояния товара
     * @return Флаг логического состояния товара
     */
    public boolean isLogicalFlag() {
        return logicalFlag;
    }

    /**
     * Установить логический флаг товара.
     *
     * @param logicalFlag логический флаг товара.
     */
    public void setLogicalFlag(boolean logicalFlag) {
        this.logicalFlag = logicalFlag;
    }

    /**
     * Получить коллекцию товаров заказа.
     *
     * @return коллекцию товаров заказа.
     */
    public Collection<modelOrderGood> getOrderGoodCollection() {
        return orderGoodCollection;
    }

    /**
     * Установить коллекцию товаров заказа.
     *
     * @param orderGoodCollection коллекция товаров заказа.
     */
    public void setOrderGoodCollection(Collection<modelOrderGood> orderGoodCollection) {
        this.orderGoodCollection = orderGoodCollection;
    }

    /**
     * Получить коллекцию написанных оценок.
     *
     * @return коллекцию написанных оценок.
     */
    public Collection<ModelRating> getWrittenRatings() {
        return writtenRatings;
    }

    /**
     * Установить коллекцию написанных оценок.
     *
     * @param writtenRatings коллекция написанных оценок.
     */
    public void setWrittenRatings(Collection<ModelRating> writtenRatings) {
        this.writtenRatings = writtenRatings;
    }

    /**
     * Создать объект модели товара.
     *
     * @param IDGood идентификатор товара.
     * @param good_Name наименование товара.
     * @param good_Material материал товара.
     * @param good_Price цена товара.
     * @param good_Description описание товара.
     * @param logicalFlag логический флаг товара.
     * @param franchise франшиза товара.
     * @param category категория товара.
     * @param usedPhotos использованные фотографии товара.
     * @param orderGoodCollection коллекция товаров заказа.
     * @param writtenRatings коллекция написанных оценок.
     */
    public modelGood(Long IDGood, String good_Name, String good_Material, double good_Price, String good_Description, boolean logicalFlag, modelFranchise franchise, modelCategory category, Collection<modelPhoto> usedPhotos, Collection<modelOrderGood> orderGoodCollection, Collection<ModelRating> writtenRatings) {
        this.IDGood = IDGood;
        Good_Name = good_Name;
        Good_Material = good_Material;
        Good_Price = good_Price;
        Good_Description = good_Description;
        this.logicalFlag = logicalFlag;
        this.franchise = franchise;
        this.category = category;
        this.usedPhotos = usedPhotos;
        this.orderGoodCollection = orderGoodCollection;
        this.writtenRatings = writtenRatings;
    }
}
