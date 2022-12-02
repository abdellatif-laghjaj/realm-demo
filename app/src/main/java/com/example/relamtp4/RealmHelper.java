package com.example.relamtp4;

import com.example.relamtp4.models.Product;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {
    private Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    //WRITE
    public void save(final Product product) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Product p = realm.copyToRealm(product);
            }
        });
    }

    //READ
    public ArrayList<String> retrieve() {
        ArrayList<String> product_names = new ArrayList<>();
        RealmResults<Product> products = realm.where(Product.class).findAll();
        for (Product p : products) {
            product_names.add(p.getName());
        }
        return product_names;
    }
}
