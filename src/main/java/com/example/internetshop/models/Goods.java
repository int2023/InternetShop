package com.example.internetshop.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Goods {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int goodID;
    private String goodName;
    private int price;
    @OneToMany (mappedBy = "goods")
    @JsonIgnore
    private List<OrderPosition> orderPositions;
    public List<OrderPosition> getOrderPositions() {
        return orderPositions;
    }
    public void setOrderPositions(List<OrderPosition> orderPositions) {
        this.orderPositions = orderPositions;
    }
    public int getGoodID() {
        return goodID;
    }
    public void setGoodID(int goodID) {
        this.goodID = goodID;
    }
    public String getGoodName() {
        return goodName;
    }
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }
}
