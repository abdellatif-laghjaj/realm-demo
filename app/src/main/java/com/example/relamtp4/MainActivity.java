package com.example.relamtp4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;

import com.example.relamtp4.models.Product;
import com.example.relamtp4.models.Purchase;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private RecyclerView productsRecyclerView;
    private ExtendedFloatingActionButton addProductButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        productsRecyclerView = findViewById(R.id.product_list);
        addProductButton = findViewById(R.id.add_product_fab);

        addProductButton.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.add_product_dialog);
            dialog.show();
        });

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