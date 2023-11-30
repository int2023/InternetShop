package com.example.internetshop.models;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Basket {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int basketID;
    private int clientINN;
    private int googID;
    private int goodQuantity;

    @ManyToOne
    @JoinColumn (name = "clientINN")
    private Client client;

    @OneToMany(mappedBy = "goods")
    private List<Goods> goods;

    @OneToMany (mappedBy = "orderPositions")
    private List<OrderPosition> orderPositions;

    public int getBasketID() {
        return basketID;
    }
    public void setBasketID(int basketID) {
        this.basketID = basketID;
    }
    public int getClientINN() {
        return clientINN;
    }
    public void setClientINN(int clientINN) {
        this.clientINN = clientINN;
    }
    public int getGoogID() {
        return googID;
    }
    public void setGoogID(int googID) {
        this.googID = googID;
    }
    public int getGoodQuantity() {
        return goodQuantity;
    }
    public void setGoodQuantity(int goodQuantity) {
        this.goodQuantity = goodQuantity;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public List<Goods> getGoods() {
        return goods;
    }
    public void setGoods(List<Goods> goods) {
        this.goods = goods;
    }
    public List<OrderPosition> getOrderPositions() {
        return orderPositions;
    }
    public void setOrderPositions(List<OrderPosition> orderPositions) {
        this.orderPositions = orderPositions;
    }
}
