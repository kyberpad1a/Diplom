package com.example.diplom.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class ModelShipping {
    public ModelShipping(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDShipping;
    @NotNull
    private String ShippingAddress;
    private boolean ShippingStatus;
    @NotNull
    private Integer ShippingApartment;
    private boolean ShippingTaken;
    @OneToOne(optional = true, cascade = CascadeType.MERGE)
    @JoinColumn(name="order_id")
    private modelOrder order;

    public Long getIDShipping() {
        return IDShipping;
    }

    public void setIDShipping(Long IDShipping) {
        this.IDShipping = IDShipping;
    }

    public String getShippingAddress() {
        return ShippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        ShippingAddress = shippingAddress;
    }

    public boolean isShippingStatus() {
        return ShippingStatus;
    }

    public void setShippingStatus(boolean shippingStatus) {
        ShippingStatus = shippingStatus;
    }

    public Integer getShippingApartment() {
        return ShippingApartment;
    }

    public void setShippingApartment(Integer shippingApartment) {
        ShippingApartment = shippingApartment;
    }

    public boolean isShippingTaken() {
        return ShippingTaken;
    }

    public void setShippingTaken(boolean shippingTaken) {
        ShippingTaken = shippingTaken;
    }

    public modelOrder getOrder() {
        return order;
    }

    public void setOrder(modelOrder order) {
        this.order = order;
    }

    public ModelShipping(Long IDShipping, String shippingAddress, boolean shippingStatus, Integer shippingApartment, boolean shippingTaken, modelOrder order) {
        this.IDShipping = IDShipping;
        ShippingAddress = shippingAddress;
        ShippingStatus = shippingStatus;
        ShippingApartment = shippingApartment;
        ShippingTaken = shippingTaken;
        this.order = order;
    }
}
