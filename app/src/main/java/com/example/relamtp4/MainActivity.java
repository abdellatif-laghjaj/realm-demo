package com.example.relamtp4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;

import com.example.relamtp4.models.Product;
import com.example.relamtp4.models.Purchase;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        realm = Realm.getDefaultInstance();
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.deleteAll();
//            }
//        });

        realm.beginTransaction();
        long id = UUID.randomUUID().getMostSignificantBits();
        Product product = realm.createObject(Product.class, id);
        product.setName("Product 1");
        product.setPrice(10.0);
        product.setImage(R.drawable.ic_launcher_background);

        Purchase purchase = realm.createObject(Purchase.class);
        purchase.addProduct(product);
        realm.commitTransaction();

        RealmResults<Product> product_list = realm.where(Product.class).findAll().sort("price");
        for (Product p : product_list) {
            //TODO: Do something with the product
        }
    }
}