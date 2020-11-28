package com.prbansal.userecom.dialogs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import com.prbansal.userecom.R;
import com.prbansal.userecom.databinding.WeightPickerBinding;
import com.prbansal.userecom.models.Cart;
import com.prbansal.userecom.models.Products;

public class WeightPicker extends AppCompatActivity {
    public interface OnWeightPickedListener {
        void onWeightPicked(int kg, int g);

        void onRemoved();
    }

    private WeightPickerBinding weightPickerBinding;
    private Cart cart;
    private Products product;
    int value;

    public void show(Context context, final Cart cart, final Products products, OnWeightPickedListener listener) {
        weightPickerBinding = WeightPickerBinding.inflate(LayoutInflater.from(context));
        this.cart = cart;
        this.product = products;

        new AlertDialog.Builder(context)
                .setTitle(product.name)
                .setView(weightPickerBinding.getRoot())
                .setPositiveButton("SELECT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int kg = weightPickerBinding.kgPicker.getValue();
                        int g = weightPickerBinding.gPicker.getValue() * 50;

                        //GuardCode to prevent user from selecting 0kg 0g
                        if (kg == 0 && g == 0)
                            return;

                        changeInCart(kg + (g / 1000f));
                        listener.onWeightPicked(kg, g);
                    }
                })
                .setNegativeButton("REMOVE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        cart.removeWBP(product);
                        listener.onRemoved();
                    }
                })
                .show();

        setupPickers();
        showPreviouslySelectedQty();
    }


    public void setupPickers() {

        float minVal =  product.minQty*1000;
        weightPickerBinding.gPicker.setMinValue((int) ((minVal - 1000 )/ 50));
        weightPickerBinding.kgPicker.setMinValue((int) minVal/1000);
        weightPickerBinding.gPicker.setMaxValue(19);
        weightPickerBinding.kgPicker.setMaxValue(10);

        weightPickerBinding.gPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value * 50 + "g";
            }
        });
        setUpFirstNumberView(weightPickerBinding.gPicker);
        weightPickerBinding.kgPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return value + "kg";
            }
        });

        setUpFirstNumberView(weightPickerBinding.kgPicker);

        weightPickerBinding.kgPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int val = weightPickerBinding.kgPicker.getValue();
                if (val > (int) minVal) {
                    weightPickerBinding.gPicker.setMinValue(0);
                } else {
                    weightPickerBinding.gPicker.setMinValue( (int) ((minVal - 1000 )/ 50));
                }
            }
        });
    }

    private void setUpFirstNumberView(NumberPicker picker) {
        View firstItem = picker.getChildAt(0);
        if (firstItem != null)
            firstItem.setVisibility(View.INVISIBLE);
    }

    private void showPreviouslySelectedQty() {
        if (cart.AllTypeOfItemInCart.containsKey(product.name)) {
            float qty =(cart.AllTypeOfItemInCart.get(product.name).qty);

            weightPickerBinding.kgPicker.setValue((int) qty);
           value=Math.round((qty - (int) qty)* 1000/ 50);
            weightPickerBinding.gPicker.setValue (value);
        }
    }

    private void changeInCart(float qty) {
        cart.updateWBP(product, qty);
    }
}