package com.prbansal.userecom.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Products implements Serializable {
    public static final int WEIGHT_BASED=0, VARIANT_BASED=1;

    public String name;
    public int type;

    // attributes of Weight based
    public int pricePerKg;
    public float minQty;

    // list of variants
    public List<Variants> variants;

    public Products() {
    }

    //Constructor for Weight based products
    public Products(String name, int pricePerKg, float minQty) {
        this.type= WEIGHT_BASED;
        this.name = name;
        this.pricePerKg = pricePerKg;
        this.minQty = minQty;
    }


    //Constructor for Variant based product

    public Products(String name) {
        this.type= VARIANT_BASED;
        this.name =name;

    }

    public Products(String name, List<Variants> variantsList) {
        type= VARIANT_BASED;
        this.name = name;
        this.variants = variantsList;
    }

    // Methods to get and set Attributes  of products

    public  String minQtyToString(){
        if(minQty<1){
            int gram = (int)(minQty*1000);
            return gram + "g";
        }

        return ((int) minQty)+"kg";
    }


    public String listOfVariants(){
        String listOfVariants = variants.toString();
        return listOfVariants.replaceFirst("\\[","")
                .replaceFirst("]","")
                .replaceAll(",","");
    }

    @Override
    public String toString() {
        return "Products{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", pricePerKg=" + pricePerKg +
                ", minQty=" + minQty +
                ", variantsList=" + variants +
                '}';
    }
    public String priceString(){
        if(type == Products.WEIGHT_BASED)
            return "Rs. " + pricePerKg + "/kg";

        return listOfVariants();
    }
}
