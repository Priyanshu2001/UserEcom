package com.prbansal.userecom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.prbansal.userecom.adapter.ProductAdapter;
import com.prbansal.userecom.databinding.ActivityCatelogBinding;
import com.prbansal.userecom.models.Cart;
import com.prbansal.userecom.models.Inventory;
import com.prbansal.userecom.models.Products;

import java.util.ArrayList;

public class CatelogActivity extends AppCompatActivity {
    ActivityCatelogBinding activityCatelogBinding;
    public Cart cart= new Cart();
    private ProductAdapter productsAdapter;
    public ArrayList<Products> products;
    private int RECEIVED_CART=1;
    public MyApp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCatelogBinding = ActivityCatelogBinding.inflate(getLayoutInflater());
        setContentView(activityCatelogBinding.getRoot());

        app =  (MyApp) getApplicationContext();
        loadPreviousData();
        activityCatelogBinding.CheckoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchCartSumamaryActivity();
            }
        });
    }
/*
    private void saveData() {
        SharedPreferences preferences = getSharedPreferences("products_data", MODE_PRIVATE);
        preferences.edit()
                .putString("data", new Gson().toJson(products))
                .apply();


    }*/

    private void loadPreviousData() {
            if(app.isOffline()){
                app.showToast(this, "You are offline! Unable to save. Check your connection and try again.");
                return;
            }
            app.showLoadingDialog(this);

            app.db.collection("My Inventory").document("Products List")
                    .get()
       .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if(documentSnapshot.exists()&& documentSnapshot!=null){
                                Inventory inventory =  documentSnapshot.toObject(Inventory.class);
                                products = inventory.myProductsList;
                            }
                            else {
                                products = new ArrayList<>();
                                app.hideLoadingDialog();
                            }
                            setupProductList();
                            /*saveDataLocally();*/
                            app.hideLoadingDialog();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            app.showToast(CatelogActivity.this,"Failed to Fetch Data!" + e.getMessage());
                            e.printStackTrace();                        }
                    });
        }
/*        SharedPreferences preferences = getSharedPreferences("products_data", MODE_PRIVATE);
        String jsonData = preferences.getString("data", null);
        if (jsonData != null) {
            products = new Gson().fromJson(jsonData, new TypeToken<ArrayList<Products>>() {
            }.getType());
        }
        else loadFromFirebase();*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
/*        saveData();*/
    }


    private void setupProductList() {

        productsAdapter = new ProductAdapter(this, products, cart);

                activityCatelogBinding.recyclerView.setAdapter(productsAdapter);
                activityCatelogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
                activityCatelogBinding.recyclerView.addItemDecoration(
                        new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


    }

    public void updateCartSummary(Cart cart) {
        if (cart.noOfItems == 0) {
            activityCatelogBinding.CheckoutSummary.setVisibility(View.GONE);
        } else {
            activityCatelogBinding.CheckoutSummary.setVisibility(View.VISIBLE);

            activityCatelogBinding.cartSummary.setText("Total : Rs. " + cart.subtotal + "\n" + cart.noOfItems + " items");
        }

    }
    public void launchCartSumamaryActivity(){
        Intent cartIntent = new Intent(this, CartSummaryActivity.class);
        cartIntent.putExtra("myCart", cart);
        startActivityForResult(cartIntent,RECEIVED_CART);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECEIVED_CART) {
            if (resultCode == RESULT_OK) {
                Log.e("data ",(Cart)data.getSerializableExtra("return_myCart")+ "");
                cart.replaceCart((Cart) data.getSerializableExtra("return_myCart"));
                productsAdapter.notifyDataSetChanged();


                updateCartSummary((Cart) data.getSerializableExtra("return_myCart"));
            }
        }
    }
}
