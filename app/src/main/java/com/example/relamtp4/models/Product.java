package com.example.relamtp4.models;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Product extends RealmObject {
    Realm realm;
    @PrimaryKey
    private long id;
    private String name;
    private double price;
    private int image;

    public Product() {
    }

    public Product(String name, double price, int image) {
        this.name = name;
        this.price = price;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNextId() {
        Number max_id = realm.where(Product.class).max("id");
        if (max_id == null) return 1;
        else return max_id.longValue() + 1;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
