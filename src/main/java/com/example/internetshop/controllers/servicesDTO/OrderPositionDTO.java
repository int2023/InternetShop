package com.example.internetshop.controllers.servicesDTO;

public class OrderPositionDTO {
    private String positionName;
    private int priceForUnit;
    private int sumPriceOfPosition;
    private int goodsQuantity;

    public OrderPositionDTO(String positionName, int priceForUnit,
                            int sumPriceOfPosition, int goodsQuantity) {
        this.positionName = positionName;
        this.priceForUnit = priceForUnit;
        this.sumPriceOfPosition = sumPriceOfPosition;
        this.goodsQuantity = goodsQuantity;
    }

    public OrderPositionDTO() { }

    public String getPositionName() {
        return positionName;
    }
    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }
    public int getPriceForUnit() {
        return priceForUnit;
    }
    public void setPriceForUnit(int priceForUnit) {
        this.priceForUnit = priceForUnit;
    }
    public int getSumPriceOfPosition() {
        return sumPriceOfPosition;
    }
    public void setSumPriceOfPosition(int sumPriceOfPosition) {
        this.sumPriceOfPosition = sumPriceOfPosition;
    }
    public int getGoodsQuantity() {
        return goodsQuantity;
    }
    public void setGoodsQuantity(int goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

}
