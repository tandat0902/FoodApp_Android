package com.example.foodapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodapp.adapters.CartAdapter;
import com.example.foodapp.models.CartModels;
import com.example.foodapp.models.EventBus.sumPriceEvent;
import com.example.foodapp.utils.Utils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    RecyclerView recyclerViewCart;
    List<CartModels> cartModels;
    CartAdapter cartAdapter;
    ImageView btnBack, cartEmpty;
    Button btnPay;
    EditText edtQuantity;
    TextView edtCartEmpty, productName, productPrice, tongTien, edtSearch;
    ConstraintLayout price_broad;
    CartModels cart;
    RoundedImageView productImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        initBtnBack();
        initView();
        initEdtSearch();
        getRelatedCart();
        initBtnPay();

    }

    private void initBtnPay() {
        btnPay = (Button) findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Utils.cartList.size() != 0){
                    startActivity(new Intent(getApplicationContext(), PayActivity.class));
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                    builder.setTitle("PANDA MART - THÔNG BÁO");
                    builder.setMessage("Giỏ hàng không có sản phẩm nên không thể thanh toán!");
                    builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create();
                    builder.show();
                }
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

    private void initView() {

        productImage = findViewById(R.id.img_product);
        productName = findViewById(R.id.name_product);
        productPrice = findViewById(R.id.price_product);
        edtQuantity = findViewById(R.id.quantity);
        cartEmpty = findViewById(R.id.img_cartEmpty);
        edtCartEmpty = findViewById(R.id.txt_cartEmpty);
        tongTien = findViewById(R.id.tongTien);
        price_broad = findViewById(R.id.price_broad);

    }

    private void initBtnBack(){
        btnBack = (ImageView) findViewById(R.id.back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.back:
                        onBackPressed();
                        break;
                }
            }
        });
    }

    private void getRelatedCart() {
        recyclerViewCart = findViewById(R.id.cart_list);
        recyclerViewCart.setLayoutManager(new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false));
        recyclerViewCart.setHasFixedSize(true);

        if(Utils.cartList.size() == 0){
            cartEmpty.setVisibility(View.VISIBLE);
            edtCartEmpty.setVisibility(View.VISIBLE);
        } else{
            cartAdapter = new CartAdapter(getApplicationContext(), Utils.cartList);
            recyclerViewCart.setAdapter(cartAdapter);
            price_broad.setVisibility(View.VISIBLE);
            tinhTien();
        }
    }

    private void tinhTien() {
        long t = 0;
        for(int i = 0; i < Utils.cartList.size(); i++){
            t += Utils.cartList.get(i).getPrice();
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongTien.setText(decimalFormat.format(t) + "đ");

    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)

    public void eventTinhTong(sumPriceEvent event){
        if(event != null){
            tinhTien();
        }
    }
}
