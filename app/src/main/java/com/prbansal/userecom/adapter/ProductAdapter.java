package com.prbansal.userecom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.prbansal.userecom.controllers.MultiVBTypeOrWBTypeViewBinder;
import com.prbansal.userecom.controllers.SingleVBTypeViewBinder;
import com.prbansal.userecom.databinding.MultiVbtypeOrWbtyptBinding;
import com.prbansal.userecom.databinding.SingleVbtypeItemBinding;
import com.prbansal.userecom.models.Cart;

import com.prbansal.userecom.models.Products;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int SINGLE_VB_TYPE = 0, WB_OR_MULTI_VB_TYPE = 1;

    private Context context;
    private List<Products> productsList;
    public Cart cart;


    public ProductAdapter(Context context, List<Products> productsList,Cart cart) {
        this.context = context;
        this.productsList = productsList;
        this.cart = cart;

    }


    public int getItemViewType(int position) {
        Products p  = productsList.get(position);
        if(p.type == Products.WEIGHT_BASED ||   p.variantsList.size() > 1)
            return WB_OR_MULTI_VB_TYPE;

        return SINGLE_VB_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SINGLE_VB_TYPE){
            SingleVbtypeItemBinding singleVbtypeItemBinding =
                    SingleVbtypeItemBinding.inflate(LayoutInflater.from(context)
                    ,parent
                    ,false);
            return new SingleVbVH(singleVbtypeItemBinding);
        }
        else {
            MultiVbtypeOrWbtyptBinding multiVbtypeOrWbtyptBinding =
                    MultiVbtypeOrWbtyptBinding.inflate(LayoutInflater.from(context)
                    , parent
                    ,false);
            return new MultipleVbOrWbVH(multiVbtypeOrWbtyptBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
     final Products p = productsList.get(position);
     if(getItemViewType(position)==SINGLE_VB_TYPE){
         SingleVbtypeItemBinding singleVbtypeItemBinding= ((SingleVbVH) holder).sVBType;
         singleVbtypeItemBinding.ProductName.setText(p.name + " " + p.variantsList.get(0).name);
         singleVbtypeItemBinding.PriceView.setText(p.priceString());

         new SingleVBTypeViewBinder().bind(singleVbtypeItemBinding,p,cart);
     }
     else {
         MultiVbtypeOrWbtyptBinding multiVbtypeOrWbtyptBinding= ((MultipleVbOrWbVH) holder).mVBOrWBType;
         multiVbtypeOrWbtyptBinding.ProductName.setText(p.name);
         multiVbtypeOrWbtyptBinding.PriceView.setText(p.priceString());

         new MultiVBTypeOrWBTypeViewBinder().bind(multiVbtypeOrWbtyptBinding,p,cart);
     }
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    public class SingleVbVH extends RecyclerView.ViewHolder{

        private SingleVbtypeItemBinding sVBType;

        public SingleVbVH(@NonNull SingleVbtypeItemBinding sVBType) {
            super(sVBType.getRoot());
            this.sVBType = sVBType;
        }

    }

    public class MultipleVbOrWbVH extends RecyclerView.ViewHolder {

        private MultiVbtypeOrWbtyptBinding mVBOrWBType;

        public MultipleVbOrWbVH(@NonNull MultiVbtypeOrWbtyptBinding mVBOrWBType) {
            super(mVBOrWBType.getRoot());
            this.mVBOrWBType = mVBOrWBType;
        }
    }

    }
