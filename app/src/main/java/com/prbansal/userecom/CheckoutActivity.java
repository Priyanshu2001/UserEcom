package com.prbansal.userecom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.prbansal.userecom.databinding.ActivityCheckoutBinding;
import com.prbansal.userecom.fcm.FCMSender;
import com.prbansal.userecom.fcm.MessageFormatter;
import com.prbansal.userecom.models.Cart;
import com.prbansal.userecom.models.Orders;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CheckoutActivity extends AppCompatActivity {

    public Cart finalCart;
    private ActivityCheckoutBinding activityCheckoutBinding;
    public Orders thisOrder;
    public FirebaseFirestore db;
    private MyApp app;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCheckoutBinding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(activityCheckoutBinding.getRoot());
        app = (MyApp) getApplicationContext();

        Intent intent = getIntent();
        finalCart = (Cart) (intent.getSerializableExtra("finalCart"));
        activityCheckoutBinding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDetails();
                setDetails();

            }
        });


    }

    private void setDetails() {
        app.db.collection("orders").add(thisOrder)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                      String  orderId = documentReference.getId();
                        sendNotification(orderId);
                        app.showToast(CheckoutActivity.this, "Order Placed!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        app.showToast(CheckoutActivity.this, "Failure!");
                    }
                });
    }

    private void getDetails() {
        String name = activityCheckoutBinding.userName.getText().toString();
        String address = activityCheckoutBinding.Adress.getText().toString();
        String mobileNo = activityCheckoutBinding.phoneNumber.getText().toString();
        thisOrder = new Orders(Orders.PLACED, name, mobileNo, address, finalCart.AllTypeOfItemInCart, finalCart.subtotal);
    }

    private void sendNotification(String orderId) {

        String message = MessageFormatter
                .getSampleMessage("admin", "Order Id="+ orderId, "New Order");


        new FCMSender()
                .send(message
                        , new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull final IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(CheckoutActivity.this)
                                                .setTitle("Failure")
                                                .setMessage(e.toString())
                                                .show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new AlertDialog.Builder(CheckoutActivity.this)
                                                .setTitle("Success")
                                                .setMessage(response.toString())
                                                .show();
                                    }
                                });


                            }
                        });
    }
}