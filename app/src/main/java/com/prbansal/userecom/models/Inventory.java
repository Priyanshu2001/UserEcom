package com.prbansal.userecom.models;

import java.util.ArrayList;

public class Inventory {
    public ArrayList<Products> myProductsList;

    public Inventory() {
    }

    public Inventory(ArrayList<Products> myProductsList) {
        this.myProductsList = myProductsList;
    }
}
