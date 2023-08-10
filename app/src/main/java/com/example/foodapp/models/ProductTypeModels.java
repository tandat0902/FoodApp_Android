package com.example.foodapp.models;

public class ProductTypeModels {

    int id;
    int imageType;
    String nameType;

    public ProductTypeModels() {
    }

    public ProductTypeModels(int id, int imageType, String nameType) {
        this.id = id;
        this.imageType = imageType;
        this.nameType = nameType;
    }

    public ProductTypeModels(int imageType, String nameType) {
        this.imageType = imageType;
        this.nameType = nameType;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }
}
