package com.example.foodapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.MainActivity;
import com.example.foodapp.R;
import com.example.foodapp.ProductDetailActivity;
import com.example.foodapp.models.ProductModels;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {


    private List<ProductModels> list;

    public ProductAdapter(List<ProductModels> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ProductModels product = list.get(position);
        holder.roundedImageView.setImageResource(list.get(position).getImg());
        holder.name.setText(list.get(position).getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.price.setText("Giá: " + decimalFormat.format(Double.parseDouble(product.getPrice())) + "đ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
                intent.putExtra("product", product);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView roundedImageView;
        TextView name, price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            roundedImageView = itemView.findViewById(R.id.img_product);
            name = itemView.findViewById(R.id.name_product);
            price = itemView.findViewById(R.id.price_product);
        }
    }
}
