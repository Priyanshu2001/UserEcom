package com.prbansal.userecom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.prbansal.userecom.adapter.ProductAdapter;
import com.prbansal.userecom.databinding.ActivityCartSummaryBinding;
import com.prbansal.userecom.databinding.ItemsInCartBinding;
import com.prbansal.userecom.models.Cart;
import com.prbansal.userecom.models.CartItem;

import java.util.Map;

public class CartSummaryActivity extends AppCompatActivity {
    ActivityCartSummaryBinding activityCartSummaryBinding;
    Cart myCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      activityCartSummaryBinding= ActivityCartSummaryBinding.inflate(getLayoutInflater());
      setContentView(activityCartSummaryBinding.getRoot());

        Intent intent =getIntent();
         myCart=(Cart)(intent.getSerializableExtra("myCart"));

        showItemsSummary();

    }

    private void setUpDeleteBtn(ItemsInCartBinding itemsInCartBinding, Map.Entry<String, CartItem> entries) {
        itemsInCartBinding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityCartSummaryBinding.linearLayout.removeView((View)v.getParent());

                    myCart.subtotal -= entries.getValue().price;
                    myCart.noOfItems-= entries.getValue().qty;
                    myCart.AllTypeOfItemInCart.remove(entries.getKey());

            }
        });
    }

    private void showItemsSummary() {
        for (Map.Entry<String, CartItem> entries : myCart.AllTypeOfItemInCart.entrySet()){
            ItemsInCartBinding itemsInCartBinding = ItemsInCartBinding.inflate(getLayoutInflater());

            setUpDeleteBtn(itemsInCartBinding,entries);
            itemsInCartBinding.itemName.setText(entries.getKey()+"");

           if(entries.getValue().pricePerKg ==0)
            itemsInCartBinding.itemPriceSummary.setText(entries.getValue().toString());
          else
              itemsInCartBinding.itemPriceSummary.setText(entries.getValue().wbToString());
              itemsInCartBinding.ItemPrice.setText("Rs. "+entries.getValue().price);
              activityCartSummaryBinding.linearLayout.addView(itemsInCartBinding.getRoot());
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("return_myCart",myCart);
        setResult(RESULT_OK,intent);
        finish();

    }
}