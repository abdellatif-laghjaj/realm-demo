package com.example.relamtp4.helper;

import android.view.View;
import android.widget.ImageView;

import com.example.relamtp4.models.Product;

import java.util.ArrayList;

public class Utils {
    public static void checkIfNoProducts(ArrayList<Product> products, ImageView noProductsImage) {
        if (products.size() == 0) {
            noProductsImage.setVisibility(View.VISIBLE);
        } else {
            noProductsImage.setVisibility(View.GONE);
        }
    }
}
