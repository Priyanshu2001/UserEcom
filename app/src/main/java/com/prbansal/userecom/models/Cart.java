package com.prbansal.userecom.models;

import java.io.Serializable;
import java.util.HashMap;

public class Cart implements Serializable {
    public HashMap<String,CartItem> AllTypeOfItemInCart = new HashMap<>();
    public HashMap<String,Integer> TotalVariantsOfItem =new HashMap<>();
    public  int subtotal, noOfItems;

    public  int addVariantsToCart(Products product, Variants variants){
        String key= product.name + " " + variants.name;

        if(AllTypeOfItemInCart.containsKey(key)) {
            CartItem existingItem = AllTypeOfItemInCart.get(key);
            AllTypeOfItemInCart.get(key).qty++;
            existingItem.price += variants.price;
        }
        else
            AllTypeOfItemInCart.put(key,new CartItem(variants.name, variants.price));
        noOfItems++;
        subtotal+= variants.price;

        if (TotalVariantsOfItem.containsKey(product.name)) {
            int newQty = TotalVariantsOfItem.get(product.name) + 1;
            TotalVariantsOfItem.put(product.name,newQty);
        }
        else {
            TotalVariantsOfItem.put(product.name,1);
        }
       return (int)AllTypeOfItemInCart.get(key).qty;
    }

    public int removeVariantsFromCart(Products product, Variants variants){
        String key= product.name + " " + variants.name;
        CartItem existingCartItem = AllTypeOfItemInCart.get(key);
        existingCartItem.qty--;
        existingCartItem.price -= variants.price;


        if(AllTypeOfItemInCart.get(key).qty==0)
            AllTypeOfItemInCart.remove(key);

        noOfItems--;
        subtotal-=variants.price;

        int newQty = TotalVariantsOfItem.get(product.name) - 1;
        TotalVariantsOfItem.put(product.name,newQty);

       if (TotalVariantsOfItem.get(product.name)==0)
           TotalVariantsOfItem.remove(product.name);
           /*AllTypeOfItemInCart.remove(key);*/

       return AllTypeOfItemInCart.containsKey(key) ? (int)AllTypeOfItemInCart.get(key).qty : 0;
    }

    public void removeAllVariantsFromCart(Products product){
        for (Variants variant : product.variants){
            String key = product.name + " " + variant.name;

            if(AllTypeOfItemInCart.containsKey(key)){
                noOfItems-=AllTypeOfItemInCart.get(key).qty;
                subtotal-=AllTypeOfItemInCart.get(key).price;
                AllTypeOfItemInCart.remove(key);
            }
        }
        if (TotalVariantsOfItem.containsKey(product.name))
        TotalVariantsOfItem.remove(product.name);

    }

    public int getVariantQty(Products product, Variants variant){
        String key = product.name + " " + variant.name;

        if(AllTypeOfItemInCart.containsKey(key))
            return (int) AllTypeOfItemInCart.get(key).qty;

        return 0;
    }

    public void updateWBP(Products product,float qty){
        int newPrice = (int)(product.pricePerKg*qty);

        if(AllTypeOfItemInCart.containsKey(product.name))
            subtotal-=AllTypeOfItemInCart.get(product.name).price;
        else
            noOfItems++;
        AllTypeOfItemInCart.put(product.name,new CartItem(product.name,newPrice,qty,product.pricePerKg));
        subtotal+=newPrice;
        }

    public void removeWBP(Products product){
        if(AllTypeOfItemInCart.containsKey(product.name)){
            noOfItems--;
            subtotal-=AllTypeOfItemInCart.get(product.name).price;
            AllTypeOfItemInCart.remove(product.name);
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "AllTypeOfItemInCart=" + AllTypeOfItemInCart +
                ",\n TotalVariantsOfItem=" + TotalVariantsOfItem +
                ",\n subtotal=" + subtotal +
                ",\n noOfItems=" + noOfItems +
                '}';
    }

    public void replaceCart(Cart newCart){
        this.AllTypeOfItemInCart= newCart.AllTypeOfItemInCart;
        this.subtotal = newCart.subtotal;
        this.noOfItems= newCart.noOfItems;
    }
}
