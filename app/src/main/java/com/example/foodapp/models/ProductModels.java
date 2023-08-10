package com.example.foodapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class ProductModels implements Parcelable {

    int id;
    int img;
    String name;
    String price;
    int idType;

    public ProductModels(){ }

    public ProductModels(int id, int img, String name, String price, int idType){
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.idType = idType;
    }

    public ProductModels(int img, String name, String price, int idType){
        this.img = img;
        this.name = name;
        this.price = price;
        this.idType = idType;
    }

    public ProductModels(int img, String name, String price){
        this.img = img;
        this.name = name;
        this.price = price;
    }

    public ProductModels(Parcel in) {
        img = in.readInt();
        name = in.readString();
        price = in.readString();
        idType = in.readInt();
    }

    public static final Creator<ProductModels> CREATOR = new Creator<ProductModels>() {
        @Override
        public ProductModels createFromParcel(Parcel in) {
            return new ProductModels(in);
        }

        @Override
        public ProductModels[] newArray(int size) {
            return new ProductModels[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(img);
        dest.writeString(name);
        dest.writeString(price);
        dest.writeInt(idType);
    }
}
