package com.example.diplom.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;

@Entity
public class modelOrder {
    public modelOrder() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_Order;

    private Date Order_Date;


    private boolean paymentStatus;

    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelUser user;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Collection<modelOrderGood> orderGoodCollection;

    @OneToOne(optional = true, mappedBy = "order", cascade = CascadeType.ALL)
    private ModelShipping shipping;

    public Long getID_Order() {
        return ID_Order;
    }

    public void setID_Order(Long ID_Order) {
        this.ID_Order = ID_Order;
    }

    public Date getOrder_Date() {
        return Order_Date;
    }

    public void setOrder_Date(Date order_Date) {
        Order_Date = order_Date;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public modelUser getUser() {
        return user;
    }

    public void setUser(modelUser user) {
        this.user = user;
    }

    public Collection<modelOrderGood> getOrderGoodCollection() {
        return orderGoodCollection;
    }

    public void setOrderGoodCollection(Collection<modelOrderGood> orderGoodCollection) {
        this.orderGoodCollection = orderGoodCollection;
    }

    public ModelShipping getShipping() {
        return shipping;
    }

    public void setShipping(ModelShipping shipping) {
        this.shipping = shipping;
    }

    public modelOrder(Long ID_Order, Date order_Date, boolean paymentStatus, modelUser user, Collection<modelOrderGood> orderGoodCollection, ModelShipping shipping) {
        this.ID_Order = ID_Order;
        Order_Date = order_Date;
        this.paymentStatus = paymentStatus;
        this.user = user;
        this.orderGoodCollection = orderGoodCollection;
        this.shipping = shipping;
    }
}
