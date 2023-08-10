package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.utils.Utils;

import java.text.DecimalFormat;

public class BillActivity extends AppCompatActivity {

    TextView thongTinKH, tongTien;
    ImageView btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_activity);

        initView();
        setData();
        initBtnHome();

    }

    private void initView() {
        thongTinKH = findViewById(R.id.thongTinBill);
        tongTien = findViewById(R.id.tongTien);
        btnHome = findViewById(R.id.home);
    }

    private void initBtnHome() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BillActivity.this, MainActivity.class));
            }
        });
    }

    private void setData(){
        String ten = layTen(Utils.user_current.getFullName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongTien.setText(decimalFormat.format(Utils.tongGia) + "đ");
        String tt = Utils.gioiTinh + " " + ten + ", " + Utils.user_current.getPhoneNum() + "\n" + Utils.diaChi;
        thongTinKH.setText(tt);

    }

    private String layTen(String name){
        String ten = name.substring(name.lastIndexOf(" ") + 1);
        return ten;
    }
}
