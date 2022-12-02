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
        long product_id = UUID.randomUUID().getMostSignificantBits();
        Product product = realm.createObject(Product.class, product_id);
        product.setName("Product 1");
        product.setPrice(10.0);
        product.setImage(R.drawable.ic_launcher_background);

        Purchase purchase = realm.createObject(Purchase.class);
        purchase.addProduct(product);
        realm.commitTransaction();

        RealmResults<Product> results = realm.where(Product.class).findAll();
        for (Product p : results) {
            Dialog dialog = new Dialog(this);
            dialog.setTitle(p.getName());
            dialog.show();
        }
    }
}