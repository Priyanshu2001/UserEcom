package com.prbansal.userecom.models;

import java.io.Serializable;

public class CartItem  implements Serializable {
    public String name;
    public int price;
    public float qty;
    public int pricePerKg;

    public CartItem(String name, int price, float qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public CartItem(String name, int price) {
        this.name = name;
        this.price = price;
        this.qty=1;
    }

    public CartItem(String name, int price, float qty, int pricePerKg) {
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.pricePerKg = pricePerKg;
    }
public String wbToString(){
        return qty + " x Rs. " + pricePerKg + "/kg";
}
    @Override
    public String toString() {
        return qty + " x Rs. "+ price/qty;
    }
}
