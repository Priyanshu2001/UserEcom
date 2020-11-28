package com.prbansal.userecom.controllers;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.prbansal.userecom.CatelogActivity;
import com.prbansal.userecom.databinding.MultiVbtypeOrWbtyptBinding;
import com.prbansal.userecom.dialogs.VariantPicker;
import com.prbansal.userecom.dialogs.WeightPicker;
import com.prbansal.userecom.models.Cart;
import com.prbansal.userecom.models.Products;

public class MultiVBTypeOrWBTypeViewBinder {
  MultiVbtypeOrWbtyptBinding multiVbtypeOrWbtyptBinding;
  Products products;
  Cart cart;


        public void bind(MultiVbtypeOrWbtyptBinding multiVbtypeOrWbtyptBinding, Products product, Cart cart){
           this.multiVbtypeOrWbtyptBinding=multiVbtypeOrWbtyptBinding;
           this.products=product;
           this.cart=cart;

           multiVbtypeOrWbtyptBinding.AddBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   showDialog();
               }
           });

           multiVbtypeOrWbtyptBinding.editBtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   showDialog();
               }
           });
        }

    private void showDialog() {
            if(products.type== Products.WEIGHT_BASED){
                showWeightPickerDialog();

            }
            else
                showVarientPickerDialog();
    }

    private void showVarientPickerDialog() {
        Context context = multiVbtypeOrWbtyptBinding.getRoot().getContext();
        new VariantPicker().show(context,cart,products, new VariantPicker.OnVariantPickedListener() {
            @Override
            public void onQtyUpdated(int qty) {
                updateQty(qty+"");
            }

            @Override
            public void onRemoved() {
                 hide();
            }
        });
    }


    private void showWeightPickerDialog() {
        Context context = multiVbtypeOrWbtyptBinding.getRoot().getContext();
        new WeightPicker().show(context, cart, products, new WeightPicker.OnWeightPickedListener() {
            @Override
            public void onWeightPicked(int kg, int g) {
                updateQty(kg +"kg" + g + "g");
            }

            @Override
            public void onRemoved() {
               hide();
            }
        });
    }

    public void hide() {
            multiVbtypeOrWbtyptBinding.QtyandEditGrp.setVisibility(View.GONE);
        multiVbtypeOrWbtyptBinding.AddBtn.setVisibility(View.VISIBLE);
        updateCheckoutSummary();
    }

    public void updateQty(String s) {
         multiVbtypeOrWbtyptBinding.QtyandEditGrp.setVisibility(View.VISIBLE);
         multiVbtypeOrWbtyptBinding.AddBtn.setVisibility(View.GONE);
         multiVbtypeOrWbtyptBinding.qtyWb.setText(s);

         updateCheckoutSummary();
    }
    private void updateCheckoutSummary() {
        Context context = multiVbtypeOrWbtyptBinding.getRoot().getContext();
        if(context instanceof CatelogActivity){
            ((CatelogActivity) context).updateCartSummary(cart);
        } else {
            Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }
}

