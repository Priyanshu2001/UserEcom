package com.prbansal.userecom.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Products implements Serializable {
    public static final int WEIGHT_BASED=0, VARIANT_BASED=1;

    public String name;
    public int type;

    // attributes of Weight based
    public int pricePerKg;
    public float minQty;

    // list of variants
    public ArrayList<Variants> variantsList;

    public Products() {
    }

    /*public Products(String name, int type, int pricePerKg, float minQty, ArrayList<Variants> variantsList) {
        this.name = name;
        this.type = type;
        this.pricePerKg = pricePerKg;
        this.minQty = minQty;
        this.variantsList = variantsList;
    }

    // Methods to get and set Attributes  of products

    public  String minQtyToString(){
        if(minQty<1){
            int gram = (int)(minQty*1000);
            return gram + "g";
        }

        return ((int) minQty)+"kg";
    }
    public void ExtractVariantsAndSet(String[] name, String[] price) {
        variantsList = new ArrayList<>();
        for (String s : name) {
            variantsList.add(new Variants(s,price[count],count+1));
            this.count++;
        }
    }

*/
    public String listOfVariants(){
        String listOfVariants = variantsList.toString();
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
                ", variantsList=" + variantsList +
                '}';
    }
    public String priceString(){
        if(type == Products.WEIGHT_BASED)
            return "Rs. " + pricePerKg + "/kg";

        return listOfVariants();
    }
}
