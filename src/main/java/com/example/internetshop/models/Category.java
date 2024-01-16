package com.example.internetshop.models;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryID;
    private String productType;
    @OneToMany (mappedBy = "category")
    private List<Goods> goodsList;

    public Category() {}

    public Category(int categoryID, String productType,
                    List<Goods> goodsList) {
        this.categoryID = categoryID;
        this.productType = productType;
        this.goodsList = goodsList;
    }

    public int getCategoryID() {
        return categoryID;
    }
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
    public String getProductType() {
        return productType;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public List<Goods> getGoodsList() {
        return goodsList;
    }
    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

}
