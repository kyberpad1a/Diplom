package com.example.diplom.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

/**
 * Класс, представляющий заказ в системе.
 */
@Entity
public class modelOrder {

    /**
     * Конструктор по умолчанию.
     */
    public modelOrder() {
    }

    /**
     * Идентификатор заказа.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Order;

    /**
     * Дата заказа.
     */
    private Date Order_Date;

    /**
     * Статус оплаты заказа.
     */
    private boolean paymentStatus;

    /**
     * Пользователь, совершивший заказ.
     *
     * @see modelUser
     */
    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelUser user;

    /**
     * Коллекция товаров, входящих в заказ.
     *
     * @see modelOrderGood
     */
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Collection<modelOrderGood> orderGoodCollection;

    /**
     * Информация о доставке заказа.
     *
     * @see ModelShipping
     */
    @OneToOne(optional = true, mappedBy = "order", cascade = CascadeType.ALL)
    private ModelShipping shipping;

    /**
     * Возвращает идентификатор заказа.
     *
     * @return идентификатор заказа
     */
    public Long getID_Order() {
        return ID_Order;
    }

    /**
     * Устанавливает идентификатор заказа.
     *
     * @param ID_Order идентификатор заказа
     */
    public void setID_Order(Long ID_Order) {
        this.ID_Order = ID_Order;
    }

    /**
     * Возвращает дату заказа.
     *
     * @return дата заказа
     */
    public Date getOrder_Date() {
        return Order_Date;
    }

    /**
     * Устанавливает дату заказа.
     *
     * @param order_Date дата заказа
     */
    public void setOrder_Date(Date order_Date) {
        Order_Date = order_Date;
    }

    /**
     * Возвращает статус оплаты заказа.
     *
     * @return статус оплаты заказа
     */
    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Устанавливает статус оплаты заказа.
     *
     * @param paymentStatus статус оплаты заказа
     */
    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Возвращает пользователя, совершившего заказ.
     *
     * @return пользователь, совершивший заказ
     * @see modelUser
     */
    public modelUser getUser() {
        return user;
    }

    /**
     * Устанавливает пользователя, совершившего заказ.
     *
     * @param user пользователь, совершивший заказ
     * @see modelUser
     */
    public void setUser(modelUser user) {
        this.user = user;
    }

    /**
     * Возвращает коллекцию товаров, входящих в заказ.
     *
     * @return коллекция товаров, входящих в заказ
     * @see modelOrderGood
     */
    public Collection<modelOrderGood> getOrderGoodCollection() {
        return orderGoodCollection;
    }

    /**
     * Устанавливает коллекцию товаров, входящих в заказ.
     *
     * @param orderGoodCollection коллекция товаров, входящих в заказ
     * @see modelOrderGood
     */
    public void setOrderGoodCollection(Collection<modelOrderGood> orderGoodCollection) {
        this.orderGoodCollection = orderGoodCollection;
    }

    /**
     * Возвращает информацию о доставке заказа.
     *
     * @return информация о доставке заказа
     * @see ModelShipping
     */
    public ModelShipping getShipping() {
        return shipping;
    }

    /**
     * Устанавливает информацию о доставке заказа.
     *
     * @param shipping информация о доставке заказа
     * @see ModelShipping
     */
    public void setShipping(ModelShipping shipping) {
        this.shipping = shipping;
    }


    /**
     * Создает экземпляр класса modelOrder.
     * @param ID_Order ID заказа.
     * @param order_Date Дата заказа.
     * @param paymentStatus Состояние оплаты заказа.
     * @param user Пользователь, который совершил заказ.
     * @param orderGoodCollection Коллекция товаров, добавленных в заказ.
     * @param shipping Параметры доставки заказа.
     */
    public modelOrder(Long ID_Order, Date order_Date, boolean paymentStatus, modelUser user,
                      Collection<modelOrderGood> orderGoodCollection, ModelShipping shipping) {
        this.ID_Order = ID_Order;
        Order_Date = order_Date;
        this.paymentStatus = paymentStatus;
        this.user = user;
        this.orderGoodCollection = orderGoodCollection;
        this.shipping = shipping;
    }
}
