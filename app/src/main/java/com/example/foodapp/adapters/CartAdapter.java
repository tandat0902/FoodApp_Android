package com.example.foodapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.Interface.IClickListenner;
import com.example.foodapp.ProductDetailActivity;
import com.example.foodapp.R;
import com.example.foodapp.models.CartModels;
import com.example.foodapp.models.EventBus.sumPriceEvent;
import com.example.foodapp.models.ProductModels;
import com.example.foodapp.utils.Utils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {


    Context context;
    private List<CartModels> list;

    public CartAdapter(Context context, List<CartModels> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CartModels cart = list.get(position);
        holder.roundedImageView.setImageResource(list.get(position).getImg());
        holder.name.setText(cart.getName());
        long giaGoc = cart.getPrice() / cart.getQuantity();
        long tongTien = cart.getPrice();
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price.setText("Giá: " + decimalFormat.format(tongTien) + "đ");
        holder.quantity.setText(cart.getQuantity() + "");
        holder.setiClickListenner(new IClickListenner() {
            @Override
            public void onBtnClick(View v, int position, int values) {
                if(values == 1){
                    if(list.get(position).getQuantity() > 1){
                        int newQuantity = list.get(position).getQuantity() - 1;
                        list.get(position).setQuantity(newQuantity);

                        holder.quantity.setText(list.get(position).getQuantity() + "");
                        long tongTien = giaGoc * list.get(position).getQuantity();
                        holder.price.setText("Giá: " + decimalFormat.format(tongTien) + "đ");
                        Utils.cartList.get(position).setPrice(tongTien);
                        EventBus.getDefault().postSticky(new sumPriceEvent());

                    } else if(list.get(position).getQuantity() == 1){
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
                        builder.setTitle("PANDA MART - THÔNG BÁO");
                        builder.setMessage("Bạn có muốn xóa sản phẩm này khỏi giỏ hàng?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.cartList.remove(position);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new sumPriceEvent());

                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }
                } else if(values == 2){
                    if(list.get(position).getQuantity() < 10){
                        int newQuantity = list.get(position).getQuantity() + 1;
                        list.get(position).setQuantity(newQuantity);
                    }
                    holder.quantity.setText(list.get(position).getQuantity() + "");
                    long tongTien = giaGoc * list.get(position).getQuantity();
                    holder.price.setText("Giá: " + decimalFormat.format(tongTien) + "đ");
                    Utils.cartList.get(position).setPrice(tongTien);
                    EventBus.getDefault().postSticky(new sumPriceEvent());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        RoundedImageView roundedImageView;
        TextView name, price;
        EditText quantity;
        Button btnAdd, btnMinus;
        IClickListenner iClickListenner;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            roundedImageView = itemView.findViewById(R.id.img_product);
            name = itemView.findViewById(R.id.name_product);
            price = itemView.findViewById(R.id.price_product);
            quantity = itemView.findViewById(R.id.quantity);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnMinus = itemView.findViewById(R.id.btnMinus);

            btnAdd.setOnClickListener(this);
            btnMinus.setOnClickListener(this);

        }

        public void setiClickListenner(IClickListenner iClickListenner) {
            this.iClickListenner = iClickListenner;
        }

        @Override
        public void onClick(View v) {
            if(v == btnMinus){
                iClickListenner.onBtnClick(v, getAdapterPosition(), 1);
            } else if(v == btnAdd){
                iClickListenner.onBtnClick(v, getAdapterPosition(), 2);
            }
        }
    }
}
