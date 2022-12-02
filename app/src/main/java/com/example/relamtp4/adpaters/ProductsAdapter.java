package com.example.relamtp4.adpaters;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.relamtp4.R;
import com.example.relamtp4.models.Product;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Product> products;
    private ArrayList<Product> filteredProducts;
    private Realm realm;

    public ProductsAdapter(Context context, Realm realm, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
        this.filteredProducts = products;
        this.realm = realm;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.product_item, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = products.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));

        //add click listener to the card view
        holder.itemView.setOnClickListener(v -> {
            Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.update_delete_product_dialog);
            dialog.setCancelable(true);
            dialog.show();

            //get the views
            TextInputEditText name = dialog.findViewById(R.id.product_name_input);
            TextInputEditText price = dialog.findViewById(R.id.product_price_input);
            Button updateButton = dialog.findViewById(R.id.update_product_button);
            Button deleteButton = dialog.findViewById(R.id.delete_product_button);

            //set the views
            name.setText(product.getName());
            price.setText(String.valueOf(product.getPrice()));

            //add click listener to delete button
            deleteButton.setOnClickListener(v1 -> {
                realm.executeTransaction(realm -> {
                    Product productToDelete = realm.where(Product.class).equalTo("id", product.getId()).findFirst();
                    productToDelete.deleteFromRealm();
                    notifyItemRemoved(position);
                    dialog.dismiss();
                });
            });

            //add click listener to update button
            updateButton.setOnClickListener(v12 -> {
                realm.executeTransaction(realm -> {
                    Product productToUpdate = realm.where(Product.class).equalTo("id", product.getId()).findFirst();
                    productToUpdate.setName(name.getText().toString());
                    productToUpdate.setPrice(Double.parseDouble(price.getText().toString()));
                    notifyItemChanged(position);
                    dialog.dismiss();
                });
            });
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
        this.filteredProducts = products;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, price;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
        }
    }
}
