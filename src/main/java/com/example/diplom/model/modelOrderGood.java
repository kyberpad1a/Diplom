package com.example.diplom.model;

import javax.persistence.*;

@Entity
public class modelOrderGood {
    public modelOrderGood() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDOrderGood;

    private  int GoodQuantity;

    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelGood goods;

    @ManyToOne(optional = true, cascade = CascadeType.MERGE)
    private modelOrder order;

    public Long getIDOrderGood() {
        return IDOrderGood;
    }

    public void setIDOrderGood(Long IDOrderGood) {
        this.IDOrderGood = IDOrderGood;
    }

    public int getGoodQuantity() {
        return GoodQuantity;
    }

    public void setGoodQuantity(int goodQuantity) {
        GoodQuantity = goodQuantity;
    }

    public modelGood getGoods() {
        return goods;
    }

    public void setGoods(modelGood goods) {
        this.goods = goods;
    }

    public modelOrder getOrder() {
        return order;
    }

    public void setOrder(modelOrder order) {
        this.order = order;
    }

    public modelOrderGood(Long IDOrderGood, int goodQuantity, modelGood goods, modelOrder order) {
        this.IDOrderGood = IDOrderGood;
        GoodQuantity = goodQuantity;
        this.goods = goods;
        this.order = order;
    }
}
