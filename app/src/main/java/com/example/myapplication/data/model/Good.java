package com.example.myapplication.data.model;

import java.io.Serializable;

/**
 * Created by jszx on 2019/9/23.
 */

public class Good implements Serializable {

    private String name;
    private double price;
    private int pictureId;

    public Good(String name, double price, int pictureId) {
        this.name = name;
        this.price = price;
        this.pictureId = pictureId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
