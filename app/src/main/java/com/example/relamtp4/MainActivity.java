package com.example.relamtp4;

import static com.example.relamtp4.helper.Utils.checkIfNoProducts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.relamtp4.adpaters.ProductsAdapter;
import com.example.relamtp4.models.Product;
import com.example.relamtp4.models.Purchase;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private RecyclerView productsRecyclerView;
    private ExtendedFloatingActionButton addProductButton;
    private ProductsAdapter productsAdapter;
    public static ImageView noProductsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .allowWritesOnUiThread(true)
                .allowQueriesOnUiThread(true)
                .build();
        realm = Realm.getInstance(config);

        ArrayList<Product> fetchedProducts = getProducts();

        productsRecyclerView = findViewById(R.id.product_list);
        addProductButton = findViewById(R.id.add_product_fab);
        noProductsImage = findViewById(R.id.no_products);

        checkIfNoProducts(fetchedProducts, noProductsImage);

        productsAdapter = new ProductsAdapter(this, realm, fetchedProducts);
        productsRecyclerView.setAdapter(productsAdapter);
        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        addProductButton.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.add_product_dialog);

            //make dialog full width
            int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.95);
            dialog.getWindow().setLayout(width, RecyclerView.LayoutParams.WRAP_CONTENT);

            //initialize dialog views
            TextInputEditText productNameEditText = dialog.findViewById(R.id.product_name_input);
            TextInputEditText productPriceEditText = dialog.findViewById(R.id.product_price_input);
            Button addProductButton = dialog.findViewById(R.id.add_product_button);

            addProductButton.setOnClickListener(v1 -> {
                String productName = productNameEditText.getText().toString();
                double productPrice = Double.parseDouble(productPriceEditText.getText().toString());
                int productImage = R.drawable.ic_launcher_background;

                addProduct(productName, productPrice, dialog);
            });

            dialog.show();
        });
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.deleteAll();
//            }
//        });

//        realm.beginTransaction();
//        long id = UUID.randomUUID().getMostSignificantBits();
//        Product product = realm.createObject(Product.class, id);
//        product.setName("Product 1");
//        product.setPrice(10.0);
//        product.setImage(R.drawable.ic_launcher_background);
//
//        Purchase purchase = realm.createObject(Purchase.class);
//        purchase.addProduct(product);
//        realm.commitTransaction();
    }

    private void addProduct(String name, double price, Dialog dialog) {
        if (name.isEmpty() || price <= 0) {
            Snackbar.make(productsRecyclerView, "Invalid product name or price", Snackbar.LENGTH_LONG).show();
            return;
        }

        realm.executeTransactionAsync(realm -> {
            long id = UUID.randomUUID().getMostSignificantBits();
            Product product = realm.createObject(Product.class, id);
            product.setName(name);
            product.setPrice(price);
            product.setImage(R.drawable.ic_launcher_background);
        }, () -> {
            Snackbar.make(productsRecyclerView, "Product added successfully", Snackbar.LENGTH_LONG).show();
            dialog.dismiss();
            checkIfNoProducts(getProducts(), noProductsImage);
            //refresh products list
            productsAdapter.setProducts(getProducts());
        }, error -> {
            Snackbar.make(productsRecyclerView, "Error adding product", Snackbar.LENGTH_LONG).show();
        });
    }

    private ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        RealmResults<Product> product_list = realm.where(Product.class).findAll();
        for (Product p : product_list) {
            products.add(p);
        }
        return products;
    }
}