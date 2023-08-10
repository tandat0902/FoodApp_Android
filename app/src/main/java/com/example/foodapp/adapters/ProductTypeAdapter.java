package com.example.foodapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.R;
import com.example.foodapp.models.ProductTypeModels;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ProductTypeAdapter extends RecyclerView.Adapter<ProductTypeAdapter.ViewHolder> {

    List<ProductTypeModels> list;

    public ProductTypeAdapter(List<ProductTypeModels> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.roundedImageView.setImageResource(list.get(position).getImageType());
        holder.name.setText(list.get(position).getNameType());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView roundedImageView;
        TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            roundedImageView = itemView.findViewById(R.id.cat_img);
            name = itemView.findViewById(R.id.cat_name);
        }
    }
}
