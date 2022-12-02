package com.example.relamtp4.adpaters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.relamtp4.R;
import com.example.relamtp4.models.Product;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Product> products;
    private ArrayList<Product> filteredProducts;

    public ProductsAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
        this.filteredProducts = products;
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
            Snackbar.make(v, "Are you sure you want to delete this product?", Snackbar.LENGTH_LONG)
                    .setAction("Yup", v1 -> {
                        //delete product from realm
//                        realm.executeTransaction(realm -> {
//                            product.deleteFromRealm();
//                        });
                        //remove product from the list
                        products.remove(position);
                        //notify adapter that the data has changed
                        notifyDataSetChanged();
                    }).show();
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
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
