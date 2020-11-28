package com.prbansal.userecom.models;

public class Variants {
    public String name;
    public int price;


    public Variants(String name, int price) {
        this.name = name;
        this.price = price;
    }


    @Override
    public String toString() {
        return "Rs. " + price + "\n" ;
    }

    public String nameAndPriceString() {
        return name+ "- Rs. "+ price ;
    }
}
