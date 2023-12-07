package com.example.internetshop.models;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Basket {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int basketID;
    private int goodQuantity;

    @ManyToOne
    @JoinColumn (name = "clientINN")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "good")
    private Goods good;

    public int getBasketID() {
        return basketID;
    }
    public void setBasketID(int basketID) {
        this.basketID = basketID;
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
    public Goods getGood() {
        return good;
    }
    public void setGood(Goods good) {
        this.good = good;
    }
}
