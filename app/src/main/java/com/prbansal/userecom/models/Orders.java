package com.prbansal.userecom.models;

import com.google.firebase.Timestamp;
import java.util.Map;

public class Orders {
    public static final int PLACED = 1, DELIVERED = 0, DECLINED = -1;
    public int status;
 /*   public String orderId;*/
    public Timestamp orderPlacedTs;

    public String userName, userPhoneNo, userAddress;

    public Map<String,CartItem> cartItems;
    public int subTotal;

    public Orders() {
    }

    public Orders(int status, String userName, String userPhoneNo, String userAddress
            , Map<String,CartItem> cartItems, int subTotal) {
        this.status = status;
        this.userName = userName;
        this.userPhoneNo = userPhoneNo;
        this.userAddress = userAddress;
        this.cartItems = cartItems;
        this.subTotal = subTotal;
        this.orderPlacedTs = Timestamp.now();
    }
}
