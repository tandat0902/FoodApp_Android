package com.example.foodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.models.UserModels;
import com.example.foodapp.utils.Utils;

public class AccountInfomation extends AppCompatActivity {
    ImageView btnBack;
    TextView tvSua, tvDangXuat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_menu);

        initView();
        initBtnBack();
        initSua();
        initDangXuat();


    }

    private void initDangXuat() {
        tvDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.user_current = new UserModels();
                startActivity(new Intent(AccountInfomation.this, LoginActivity.class));
            }
        });
    }

    private void initSua() {
        tvSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountInfomation.this, UpdataAccount.class));
            }
        });
    }

    private void initBtnBack() {
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

    private void initView() {
        btnBack = (ImageView) findViewById(R.id.back);
        tvSua = (TextView) findViewById(R.id.suaThongTin);
        tvDangXuat = (TextView) findViewById(R.id.tvDangXuat);
    }
}
