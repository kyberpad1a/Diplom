package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
public class ModelShipping {

    /**
     * Конструктор по умолчанию
     */
    public ModelShipping(){}

    /**
     * Уникальный идентификатор доставки
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDShipping;

    /**
     * Адрес доставки
     */
    @NotNull
    private String ShippingAddress;

    /**
     * Статус доставки
     */
    private boolean ShippingStatus;

    /**
     * Номер квартиры
     */
    @NotNull
    private Integer ShippingApartment;

    /**
     * Маркер получения
     */
    private boolean ShippingTaken;

    /**
     * Сущность заказа, с которым связана доставка.
     */
    @OneToOne(optional = true, cascade = CascadeType.MERGE)
    @JoinColumn(name="order_id")
    private modelOrder order;

    /**
     * Пользователь, которому принадлежит доставка.
     */
    @Null
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelUser user;

    /**
     * Возвращает уникальный идентификатор доставки.
     * @return уникальный идентификатор доставки
     */
    public Long getIDShipping() {
        return IDShipping;
    }

    /**
     * Устанавливает уникальный идентификатор доставки.
     * @param IDShipping уникальный идентификатор доставки
     */
    public void setIDShipping(Long IDShipping) {
        this.IDShipping = IDShipping;
    }

    /**
     * Возвращает адрес доставки.
     * @return адрес доставки
     */
    public String getShippingAddress() {
        return ShippingAddress;
    }

    /**
     * Устанавливает адрес доставки.
     * @param shippingAddress адрес доставки
     */
    public void setShippingAddress(String shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    /**
     * Возвращает статус доставки.
     * @return статус доставки
     */
    public boolean isShippingStatus() {
        return ShippingStatus;
    }

    /**
     * Устанавливает статус доставки.
     * @param shippingStatus статус доставки
     */
    public void setShippingStatus(boolean shippingStatus) {
        ShippingStatus = shippingStatus;
    }

    /**
     * Возвращает номер квартиры.
     * @return номер квартиры
     */
    public Integer getShippingApartment() {
        return ShippingApartment;
    }

    /**
     * Устанавливает номер квартиры.
     * @param shippingApartment номер квартиры
     */
    public void setShippingApartment(Integer shippingApartment) {
        ShippingApartment = shippingApartment;
    }

    /**
     * Возвращает маркер получения.
     * @return маркер получения
     */
    public boolean isShippingTaken() {
        return ShippingTaken;
    }

    /**
     * Устанавливает маркер получения.
     * @param shippingTaken маркер получения
     */
    public void setShippingTaken(boolean shippingTaken) {
        ShippingTaken = shippingTaken;
    }

    /**
     * Возвращает сущность заказа, связанную с доставкой.
     * @return сущность заказа, связанная с доставкой
     */
    public modelOrder getOrder() {
        return order;
    }

    /**
     * Устанавливает сущность заказа, связанную с доставкой.
     * @param order сущность заказа, связанная с доставкой
     */
    public void setOrder(modelOrder order) {
        this.order = order;
    }

    /**
     * Получает пользователя, которому принадлежит доставка.
     * @return пользователь, которому принадлежит доставка.
     */
    public modelUser getUser() {
        return user;
    }

    /**
     * Устанавливает пользователя, которому принадлежит доставка.
     */
    public void setUser(modelUser user) {
        this.user = user;
    }

    /**
     * Создает новый объект доставки с заданным уникальным идентификатором, адресом, статусом, номером квартиры/офиса, информацией о том, забрал ли курьер доставку, заказом и пользователем, которому принадлежит заказ.
     * @param IDShipping уникальный идентификатор доставки.
     * @param shippingAddress адрес доставки.
     * @param shippingStatus статус доставки (выполнена или нет).
     * @param shippingApartment номер квартиры/офиса для доставки.
     * @param shippingTaken флаг, показывающий, забрал ли курьер доставку.
     * @param order заказ, который должен быть доставлен.
     * @param user пользователь, которому принадлежит заказ.
     */
    public ModelShipping(Long IDShipping, String shippingAddress, boolean shippingStatus, Integer shippingApartment, boolean shippingTaken, modelOrder order, modelUser user) {
        this.IDShipping = IDShipping;
        ShippingAddress = shippingAddress;
        ShippingStatus = shippingStatus;
        ShippingApartment = shippingApartment;
        ShippingTaken = shippingTaken;
        this.order = order;
        this.user = user;
    }
}
