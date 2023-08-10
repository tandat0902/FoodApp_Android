package com.example.foodapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CartModels implements Parcelable {
    int id;
    int img;
    String name;
    long price;
    int quantity;

    public CartModels() {
    }

    public CartModels(int id, int img, String name, long price, int quantity) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public CartModels(int img, String name, long price, int quantity) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public CartModels(Parcel in) {
        img = in.readInt();
        name = in.readString();
        price = in.readLong();
        quantity = in.readInt();
    }

    public static final Parcelable.Creator<CartModels> CREATOR = new Parcelable.Creator<CartModels>() {
        @Override
        public CartModels createFromParcel(Parcel in) {
            return new CartModels(in);
        }

        @Override
        public CartModels[] newArray(int size) {
            return new CartModels[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(img);
        dest.writeString(name);
        dest.writeLong(price);
        dest.writeInt(quantity);
    }
}
