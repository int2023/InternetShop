package com.example.internetshop.controllers.servicesDTO;

public class BasketDTO {

    private int basketID;
    private int goodsQuantity;
    private int clientINN;
    private int goodID;
    private int goodPrice;
    private String goodName;

    public BasketDTO(int basketID, int goodsQuantity, int clientINN,
                     int goodID, int goodPrice, String goodName) {
        this.basketID = basketID;
        this.goodsQuantity = goodsQuantity;
        this.clientINN = clientINN;
        this.goodID = goodID;
        this.goodPrice = goodPrice;
        this.goodName = goodName;
    }

    public BasketDTO() {
    }

    public int getBasketID() {
        return basketID;
    }

    public void setBasketID(int basketID) {
        this.basketID = basketID;
    }

    public int getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public int getClientINN() {
        return clientINN;
    }

    public void setClientINN(int clientINN) {
        this.clientINN = clientINN;
    }

    public int getGoodID() {
        return goodID;
    }

    public void setGoodID(int goodID) {
        this.goodID = goodID;
    }

    public int getGoodPrice() {
        return goodPrice;
    }

    public void setGoodPrice(int goodPrice) {
        this.goodPrice = goodPrice;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }
}
