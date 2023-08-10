package com.example.foodapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.models.EventBus.sumPriceEvent;
import com.example.foodapp.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class PayActivity extends AppCompatActivity {

    Button btnHoanTat;
    TextView tongTien, tongDonHang, txtSearch, txtPhiGiao;
    EditText thanhPho, Quan, Phuong, Duong, phone, name;
    ImageView btnBack;
    CheckBox chkRequest;
    RadioGroup rdoGender, rdoPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_activity);

        initView();
        initBtnBack();
        setTxtInfo();
        initBtnSearch();
        initBtnHoanTat();
        initCheckRequest();
        tinhTienCoPhi();
        tinhTien();
    }

    private void initView() {
        btnHoanTat = (Button) findViewById(R.id.btnHoanTat);
        tongTien = findViewById(R.id.txtTienHang);
        tongDonHang = findViewById(R.id.txtTongDon);
        chkRequest = findViewById(R.id.chkRequest);
        txtPhiGiao = findViewById(R.id.txtPhiGiao);
        phone = findViewById(R.id.phone);
        name = findViewById(R.id.fullname);
        thanhPho = findViewById(R.id.txtThanhPho);
        Quan = findViewById(R.id.txtQuan);
        Phuong = findViewById(R.id.txtPhuong);
        Duong = findViewById(R.id.txtTenDuong);
        rdoGender = findViewById(R.id.rdoGender);
        rdoPay = findViewById(R.id.rdiG_Pay);

    }

    private void initCheckRequest() {
        chkRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tinhTienCoPhi();
            }
        });
    }

    private void initBtnSearch() {
        txtSearch = (TextView) findViewById(R.id.txtSearch);
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });
    }

    private void initBtnBack() {
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

    private void initBtnHoanTat() {
        btnHoanTat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGender = rdoGender.getCheckedRadioButtonId();
                int selectedPay = rdoPay.getCheckedRadioButtonId();
                if(phone.getText().toString().isEmpty()){
                    String message = "Bạn chưa điền số điện thoại!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else if (selectedGender == -1) {
                    String message = "Bạn chưa chọn giới tính!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else if (name.getText().toString().isEmpty()){
                    String message = "Bạn chưa điền họ tên!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else if(thanhPho.getText().toString().isEmpty()){
                    String message = "Bạn chưa điền tỉnh/thành phố!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else if(Quan.getText().toString().isEmpty()){
                    String message = "Bạn chưa điền quận/huyện!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else if(Phuong.getText().toString().isEmpty()){
                    String message = "Bạn chưa điền phường/xã!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else if(Duong.getText().toString().isEmpty()){
                    String message = "Bạn chưa điền tên đường!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else if (selectedPay == -1) {
                    String message = "Bạn chưa chọn phương thức thanh toán!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else{
                    setThongTin();
                    startActivity(new Intent(PayActivity.this, BillActivity.class));
                    Utils.cartList = new ArrayList<>();
                }
            }
        });
    }

    private void tinhTienCoPhi() {
        long t = 0;
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        for(int i = 0; i < Utils.cartList.size(); i++){
            t += Utils.cartList.get(i).getPrice();
        }

        t += 15000;

        txtPhiGiao.setText(decimalFormat.format(15000) + "đ");
        if(chkRequest.isChecked()){
            t += 5000;
            txtPhiGiao.setText(decimalFormat.format(20000) + "đ");
        }

        Utils.tongGia = t;
        btnHoanTat.setText("HOÀN TẤT MUA " + decimalFormat.format(t) + "đ");
        tongDonHang.setText(decimalFormat.format(t) + "đ");

    }

    private void tinhTien() {
        long t = 0;
        for(int i = 0; i < Utils.cartList.size(); i++){
            t += Utils.cartList.get(i).getPrice();
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongTien.setText(decimalFormat.format(t) + "đ");

    }

    private void setThongTin(){
        String txtTenDuong = Duong.getText().toString().trim();
        String txtPhuong = Phuong.getText().toString().trim();
        String txtQuan = Quan.getText().toString().trim();
        String txtTP = thanhPho.getText().toString().trim();
        int selectedId = rdoGender.getCheckedRadioButtonId();

        if (selectedId == R.id.rdoMale) {
            Utils.gioiTinh = "Anh";
        } else if (selectedId == R.id.rdoMale) {
            Utils.gioiTinh = "Chị";
        }
        Utils.diaChi = txtTenDuong + ", phường " + txtPhuong + ", quận " + txtQuan + ", " + txtTP;
    }

    private void setTxtInfo(){
        phone.setText(Utils.user_current.getPhoneNum());
        name.setText(Utils.user_current.getFullName());
    }

    private void thongBao(String Message, String negative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("PANDA MART - THÔNG BÁO");
        builder.setMessage(Message);
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();
    }

}
