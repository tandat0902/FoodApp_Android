package com.example.foodapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.models.UserModels;
import com.example.foodapp.utils.Utils;

public class UpdataAccount extends AppCompatActivity {

    ImageView btnBack;
    EditText phone, name, pass;
    Button btnLuu;
    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);

        initView();
        initBtnBack();
        initBtnLuu();
        setInfo();
    }

    private void initBtnLuu() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().isEmpty()){
                    String message = "Bạn chưa nhập họ tên!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else if (pass.getText().toString().isEmpty()) {
                    String message = "Bạn chưa nhập mật khẩu!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                }
                else{
                    if(!isValidName(name.getText().toString())){
                        String message = "Họ tên của bạn không hợp lệ!";
                        String negative = "OK";
                        thongBao(message, negative);
                        return;
                    } else if(!isValidPassword(pass.getText().toString())){
                        String message = "Mật khẩu của bạn không hợp lệ!";
                        String negative = "OK";
                        thongBao(message, negative);
                        return;
                    } else{
                        int id = Utils.user_current.getId();
                        String ten = name.getText().toString();
                        String sdt = phone.getText().toString();
                        String mk = pass.getText().toString();

                        db.updateUser(new UserModels(id, ten, sdt, mk));

                        String message = "Tài khoản của bạn đã được cập nhật thành công!";
                        String negative = "OK";
                        thongBao(message, negative);

                        Utils.user_current.setFullName(ten);
                        Utils.user_current.setPhoneNum(sdt);
                        Utils.user_current.setPassword(mk);
                        setInfo();
                        return;
                    }
                }
            }
        });
    }

    private boolean isValidName(String name) {
        if (name.matches("[0-9]+")) {
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 3) {
            return false;
        }
        return true;
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

    private void setInfo() {
        phone.setText(Utils.user_current.getPhoneNum());
        name.setText(Utils.user_current.getFullName());
        pass.setText(Utils.user_current.getPassword());
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
        phone = (EditText) findViewById(R.id.phone);
        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.pass);
        btnLuu = (Button) findViewById(R.id.btnLuu);
    }


}
