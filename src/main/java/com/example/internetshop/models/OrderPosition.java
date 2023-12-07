package com.example.internetshop.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
@Entity
public class OrderPosition {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private int goodsQuantity;
    @ManyToOne
    @JoinColumn (name = "goodsId")
    private Goods goods;
    @ManyToOne
    @JoinColumn(name = "bookingId")
    private Booking booking;

    public int getId() {
        return id;
    }
    public void setId(int goodsID) {
        this.id = goodsID;
    }
    public int getGoodsQuantity() {
        return goodsQuantity;
    }
    public void setGoodsQuantity(int goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }
    public Goods getGoods() {
        return goods;
    }
    public void setGoods(Goods goods) {
        this.goods = goods;
    }
    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
