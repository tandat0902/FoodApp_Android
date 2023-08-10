package com.example.foodapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodapp.adapters.ProductTypeAdapter;
import com.example.foodapp.adapters.ProductAdapter;
import com.example.foodapp.adapters.SliderAdapter;
import com.example.foodapp.models.CartModels;
import com.example.foodapp.models.ProductModels;
import com.example.foodapp.models.ProductTypeModels;
import com.example.foodapp.models.SliderData;
import com.example.foodapp.utils.Utils;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerViewProductType;
    List<ProductTypeModels> productTypeModels;
    ProductTypeAdapter productTypeAdapter;

    // -----------------   DANH SÁCH SẢN PHẨM
    RecyclerView recyclerViewProduct;
    List<ProductModels> productModels;
    ProductAdapter productAdapter;

    ImageView btnMenu, btnCart;
    TextView edtSearch;
    DatabaseHandler db = new DatabaseHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBtnMenu();
        initBtnCart();
        initEdtSearch();

        if(Utils.cartList == null){
            Utils.cartList = new ArrayList<>();
        }

        if(db.isTableEmpty(db.TABLE_PRODUCTTYPE)){
            setProductType(db);
        }

        if(db.isTableEmpty(db.TABLE_PRODUCT)){
            setProduct(db);
        }

        getMeat(db);
        getBeer(db);
        getMilk(db);

        getSlider();
        getCategory(db);

    }

    private void initBtnCart() {
        btnCart = (ImageView) findViewById(R.id.cart);
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
            }
        });
    }

    private void initBtnMenu() {
        btnMenu = (ImageView) findViewById(R.id.user);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AccountInfomation.class));
            }
        });
    }

    private void initEdtSearch() {
        edtSearch = (TextView) findViewById(R.id.search);
        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
    }


    private void setProductType(DatabaseHandler db) {

        db.addProductType(new ProductTypeModels(R.drawable.group1, "Thịt các loại"));
        db.addProductType(new ProductTypeModels(R.drawable.group2, "Kem cây, kem hộp"));
        db.addProductType(new ProductTypeModels(R.drawable.group3, "Nước giặt"));
        db.addProductType(new ProductTypeModels(R.drawable.group4, "Mì ăn liền"));
        db.addProductType(new ProductTypeModels(R.drawable.group5, "Giấy vệ sinh"));
        db.addProductType(new ProductTypeModels(R.drawable.group6, "Nước mắm"));
        db.addProductType(new ProductTypeModels(R.drawable.group7, "Rau, củ, nấm, trái cây"));
        db.addProductType(new ProductTypeModels(R.drawable.group8, "Chả giò, chả ram"));
        db.addProductType(new ProductTypeModels(R.drawable.group9, "Sữa tươi"));
        db.addProductType(new ProductTypeModels(R.drawable.group10, "Bia, Nước ngọt"));

    }

    private void setProduct(DatabaseHandler db) {

        //-------------- THỊT CÁC LOẠI
        db.addProduct(new ProductModels(R.drawable.meat1, "Cốt lết heo C.P 500g", "94000", 1));
        db.addProduct(new ProductModels(R.drawable.meat2, "Má đùi gà C.P 500g", "37000", 1));
        db.addProduct(new ProductModels(R.drawable.meat3, "Trứng cút tươi 4KFarm hộp 30 quả", "26000", 1));
        db.addProduct(new ProductModels(R.drawable.meat4, "Trứng gà ta hồng Năm Hưởng hộp 10 quả tặng 2", "36000", 1));
        db.addProduct(new ProductModels(R.drawable.meat5, "Râu mực nhập khẩu 500g", "49000", 1));

        //-------------- BIA, NƯỚC NGỌT
        db.addProduct(new ProductModels(R.drawable.beer1, "Thùng 24 lon bia Heineken Sleek 330ml", "430000", 10));
        db.addProduct(new ProductModels(R.drawable.beer2, "6 lon Strongbow dâu đen 330ml", "103000", 10));
        db.addProduct(new ProductModels(R.drawable.beer3, "Thùng 12 lon bia Sài Gòn Export 330ml", "125000", 10));
        db.addProduct(new ProductModels(R.drawable.beer4, "Thùng 24 lon nước ngọt Pepsi Cola 320ml", "230000", 10));
        db.addProduct(new ProductModels(R.drawable.beer5, "6 chai Sting hương dâu 330ml", "49000", 10));
        db.addProduct(new ProductModels(R.drawable.beer6, "6 chai trà chanh với sả Fuze Tea 1 lít", "81000", 10));

        //-------------- SỮA TƯƠI
        db.addProduct(new ProductModels(R.drawable.milk1, "Thùng 48 bịch sữa dinh dưỡng có đường Nutimilk 220ml", "259000", 9));
        db.addProduct(new ProductModels(R.drawable.milk2, "Thùng 48 hộp sữa tươi tiệt trùng ít đường TH true MILK 180ml", "410000", 9));
        db.addProduct(new ProductModels(R.drawable.milk3, "Thùng 48 hộp sữa tươi tiệt trùng ít đường Dalat Milk 180ml", "385000", 9));
        db.addProduct(new ProductModels(R.drawable.milk4, "Thùng 24 hộp sữa đậu đen óc chó hạnh nhân Sahmyook 190ml", "285000", 9));
        db.addProduct(new ProductModels(R.drawable.milk5, "Thùng 30 hộp sữa chua uống từ thực vật vị dâu nhật Veyo hộp 180ml", "245000", 9));
        db.addProduct(new ProductModels(R.drawable.milk6, "Kem đặc có đường Ngôi sao Phương Nam Xanh lá hộp 380g", "19500", 9));

    }

    private void getMeat(DatabaseHandler db) {
        recyclerViewProduct = findViewById(R.id.rec_meat);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        recyclerViewProduct.setHasFixedSize(true);

        productModels = db.getAllProductsID(1);

        productAdapter = new ProductAdapter(productModels);
        recyclerViewProduct.setAdapter(productAdapter);
    }

    private void getBeer(DatabaseHandler db) {
        recyclerViewProduct = findViewById(R.id.rec_beer);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        recyclerViewProduct.setHasFixedSize(true);

        productModels = db.getAllProductsID(10);

        productAdapter = new ProductAdapter(productModels);
        recyclerViewProduct.setAdapter(productAdapter);
    }

    private void getMilk(DatabaseHandler db) {
        recyclerViewProduct = findViewById(R.id.rec_milk);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        recyclerViewProduct.setHasFixedSize(true);

        productModels = db.getAllProductsID(9);

        productAdapter = new ProductAdapter(productModels);
        recyclerViewProduct.setAdapter(productAdapter);
    }

    private void getCategory(DatabaseHandler db) {

        recyclerViewProductType = findViewById(R.id.rec_cat);
        recyclerViewProductType.setLayoutManager(new GridLayoutManager(this, 4, RecyclerView.VERTICAL, false));
        recyclerViewProductType.setHasFixedSize(true);

        productTypeModels = db.getAllProductsType();

        productTypeAdapter = new ProductTypeAdapter(productTypeModels);
        recyclerViewProductType.setAdapter(productTypeAdapter);

    }

    private void getSlider() {
        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();
        SliderView sliderView = findViewById(R.id.slider);

        sliderDataArrayList.add(new SliderData(R.drawable.slider1));
        sliderDataArrayList.add(new SliderData(R.drawable.slider2));
        sliderDataArrayList.add(new SliderData(R.drawable.slider3));
        sliderDataArrayList.add(new SliderData(R.drawable.slider4));
        sliderDataArrayList.add(new SliderData(R.drawable.slider5));
        sliderDataArrayList.add(new SliderData(R.drawable.slider6));
        sliderDataArrayList.add(new SliderData(R.drawable.slider7));
        sliderDataArrayList.add(new SliderData(R.drawable.slider8));
        sliderDataArrayList.add(new SliderData(R.drawable.slider9));
        sliderDataArrayList.add(new SliderData(R.drawable.slider10));
        sliderDataArrayList.add(new SliderData(R.drawable.slider11));
        sliderDataArrayList.add(new SliderData(R.drawable.slider12));

        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setSliderAdapter(adapter);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }


}