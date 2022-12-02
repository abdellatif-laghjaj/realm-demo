package com.example.relamtp4.models;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

public class Purchase extends RealmObject {
    private RealmList<Product> products;
    private Date date;

    public RealmList<Product> getProducts() {
        return products;
    }

    public void setProducts(RealmList<Product> products) {
        this.products = products;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
