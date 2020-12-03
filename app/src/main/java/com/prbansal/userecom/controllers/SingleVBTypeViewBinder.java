package com.prbansal.userecom.controllers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.prbansal.userecom.CatelogActivity;

import com.prbansal.userecom.databinding.SingleVbtypeItemBinding;
import com.prbansal.userecom.models.Cart;
import com.prbansal.userecom.models.Products;

public class SingleVBTypeViewBinder {
     SingleVbtypeItemBinding singleVbtypeItemBinding;
     Cart cart;
     int qty;


    public void bind(SingleVbtypeItemBinding singleVbtypeItemBinding, Products p, Cart cart) {
        this.singleVbtypeItemBinding =singleVbtypeItemBinding;
        this.cart =cart;
        singleVbtypeItemBinding.AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.addVariantsToCart(p,p.variantsList.get(0));
                updateQty(1);
            }
        });
        singleVbtypeItemBinding.incQty.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
         int Qty = cart.addVariantsToCart(p,p.variantsList.get(0));
         updateQty(Qty);
    }
        });

        singleVbtypeItemBinding.decQty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Qty = cart.removeVariantsFromCart(p,p.variantsList.get(0));
                updateQty(Qty);
            }
        });

        updateQty(cart.getVariantQty(p,p.variantsList.get(0)));
    }

public void updateQty(int qty) {
   if(qty==1) {
       singleVbtypeItemBinding.AddBtn.setVisibility(View.GONE);
       singleVbtypeItemBinding.group.setVisibility(View.VISIBLE);
   }
   else  if (qty==0){
                singleVbtypeItemBinding.group.setVisibility(View.GONE);
                singleVbtypeItemBinding.AddBtn.setVisibility(View.VISIBLE);}

   singleVbtypeItemBinding.QtyView.setText(qty+"");
   updateCheckoutSummary();
        }


    private void updateCheckoutSummary() {
        Context context = singleVbtypeItemBinding.getRoot().getContext();
        if(context instanceof CatelogActivity){
            ((CatelogActivity) context).updateCartSummary(cart);
        } else {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}
