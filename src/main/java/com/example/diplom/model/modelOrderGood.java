package com.example.diplom.model;

import javax.persistence.*;

/**
 * Класс-сущность, представляющий модель для таблицы связей заказов и товаров
 */
@Entity
public class modelOrderGood {
    public modelOrderGood() {
    }

    /**
     * Идентификатор записи в таблице связей заказов и товаров
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDOrderGood;

    /**
     * Количество товара в заказе
     */
    private  int GoodQuantity;

    /**
     * Связь с моделью товара
     */
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelGood goods;

    /**
     * Связь с моделью заказа
     */
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelOrder order;

    /**
     * Возвращает идентификатор записи в таблице связей заказов и товаров
     *
     * @return идентификатор
     */
    public Long getIDOrderGood() {
        return IDOrderGood;
    }

    /**
     * Устанавливает идентификатор записи в таблице связей заказов и товаров
     *
     * @param IDOrderGood идентификатор
     */
    public void setIDOrderGood(Long IDOrderGood) {
        this.IDOrderGood = IDOrderGood;
    }

    /**
     * Возвращает количество товара в заказе
     *
     * @return количество товара
     */
    public int getGoodQuantity() {
        return GoodQuantity;
    }

    /**
     * Устанавливает количество товара в заказе
     *
     * @param goodQuantity количество товара
     */
    public void setGoodQuantity(int goodQuantity) {
        GoodQuantity = goodQuantity;
    }

    /**
     * Возвращает связь с моделью товара
     *
     * @return модель товара
     */
    public modelGood getGoods() {
        return goods;
    }

    /**
     * Устанавливает связь с моделью товара
     *
     * @param goods модель товара
     */
    public void setGoods(modelGood goods) {
        this.goods = goods;
    }

    /**
     * Возвращает связь с моделью заказа
     *
     * @return модель заказа
     */
    public modelOrder getOrder() {
        return order;
    }

    /**
     * Устанавливает связь с моделью заказа
     *
     * @param order модель заказа
     */
    public void setOrder(modelOrder order) {
        this.order = order;
    }

    /**
     * Конструктор класса modelOrderGood
     *
     * @param IDOrderGood   идентификатор записи в таблице связей заказов и товаров
     * @param goodQuantity  количество товара в заказе
     * @param goods         связь с моделью товара
     * @param order         связь с моделью заказа
     */
    public modelOrderGood(Long IDOrderGood, int goodQuantity, modelGood goods, modelOrder order) {
        this.IDOrderGood = IDOrderGood;
        GoodQuantity = goodQuantity;
        this.goods = goods;
        this.order = order;
    }
}
