package com.prbansal.userecom.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AlertDialog;

import com.prbansal.userecom.databinding.DialogVpBinding;
import com.prbansal.userecom.databinding.VariantPickerBinding;

import com.prbansal.userecom.models.Cart;
import com.prbansal.userecom.models.Products;
import com.prbansal.userecom.models.Variants;

public class VariantPicker {

Products product;
Cart cart;
 Context context;
    DialogVpBinding dvpb;

    public interface OnVariantPickedListener {
        void onQtyUpdated(int qty);
        void onRemoved();
    }
    public void  show(Context context, final Cart cart, final Products products, OnVariantPickedListener listener){
         dvpb=DialogVpBinding.inflate(LayoutInflater.from(context));
         this.context=context;
        this.cart=cart;
        this.product=products;

        new AlertDialog.Builder(context)
                .setTitle(product.name)
                .setCancelable(false)
                .setView(dvpb.getRoot())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!cart.TotalVariantsOfItem.isEmpty()) {

                            int qty = cart.TotalVariantsOfItem.get(product.name);

                            listener.onQtyUpdated(qty);}
                        else
                            listener.onRemoved();

                    }

                })
                .setNegativeButton("REMOVE ALL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cart.removeAllVariantsFromCart(product);
                        listener.onRemoved();
                    }
                })
                .show();

        showVariants();
    }

    private void showVariants() {
        for(Variants variant : product.variants){
              VariantPickerBinding variantPickerBinding = VariantPickerBinding.inflate(LayoutInflater.from(context),dvpb.getRoot(),true);
            variantPickerBinding.Variant.setText(variant.nameAndPriceString());

            showPreviousQty(variant, variantPickerBinding);
            setupButtons(variant, variantPickerBinding);
        }
    }

    private void showPreviousQty(Variants variant, VariantPickerBinding vpb) {
        int qty = cart.getVariantQty(product, variant);
        if(qty > 0){
            vpb.decQtyBtn.setVisibility(View.VISIBLE);
            vpb.qty.setVisibility(View.VISIBLE);

            vpb.qty.setText(qty + "");
        }
    }


    private void setupButtons(final Variants variant, final VariantPickerBinding vpb) {
        vpb.incQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Increase Qty and Update Qty View
                int qty = cart.addVariantsToCart(product,variant);
                vpb.qty.setText(qty + "");

                if(qty == 1){
                    vpb.decQtyBtn.setVisibility(View.VISIBLE);
                    vpb.qty.setVisibility(View.VISIBLE);
                }
            }
        });

        vpb.decQtyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Decrease Qty and Update Qty View
                int qty = cart.removeVariantsFromCart(product,variant);
                vpb.qty.setText(qty + "");

                if(qty == 0){
                    vpb.decQtyBtn.setVisibility(View.GONE);
                    vpb.qty.setVisibility(View.GONE);
                }
            }
        });
    }
}
