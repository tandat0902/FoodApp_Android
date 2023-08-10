package com.example.foodapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.models.UserModels;
import com.example.foodapp.utils.Utils;

import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    Button btnRegister;

    TextView btnLogin;

    EditText fullName, phoneNum, password;
    List<UserModels> userList;

    DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        if(db.isTableEmpty(db.TABLE_PRODUCT)){
            setUser(db);
        }

        initView();
        initBtnRegister();
        initBtnLogin();

    }

    private void initBtnRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fullName.getText().toString().isEmpty()){
                    String message = "Họ tên không được bỏ trống nha!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else if(phoneNum.getText().toString().isEmpty()) {
                    String message = "Số điện thoại không được bỏ trống nha!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else if(password.getText().toString().isEmpty()){
                    String message = "Mật khẩu không được bỏ trống nha!";
                    String negative = "OK";
                    thongBao(message, negative);
                    return;
                } else{
                    if(!isValidName(fullName.getText().toString())){
                        String message = "Họ tên của bạn không hợp lệ!";
                        String negative = "OK";
                        thongBao(message, negative);
                        return;
                    } else if(!isValidPhone(phoneNum.getText().toString())){
                        String message = "Sô điện thoại của bạn không hợp lệ!";
                        String negative = "OK";
                        thongBao(message, negative);
                        return;
                    } else if(!isValidPassword(password.getText().toString())){
                        String message = "Mật khẩu của bạn không hợp lệ!";
                        String negative = "OK";
                        thongBao(message, negative);
                        return;
                    } else{
                        String name = fullName.getText().toString();
                        String phone = phoneNum.getText().toString();
                        String ps = password.getText().toString();
                        if (isRegister(phone)){
                            Utils.rememberUser = new UserModels();
                            Utils.rememberUser.setPhoneNum(phone);
                            Utils.rememberUser.setPassword(ps);

                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                            builder.setTitle("PANDA MART - THÔNG BÁO");
                            builder.setMessage("Bạn đã đăng ký thành công tài khoản!");
                            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    db.addUser(new UserModels(name, phone, ps));
                                    startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                }
                            });
                            builder.create();
                            builder.show();
                            return;
                        } else {
                            String message = "Số điện thoại đã tồn tại!";
                            String negative = "OK";
                            thongBao(message, negative);
                            return;
                        }
                    }
                }

            }
        });
    }

    private void initBtnLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
    }

    private void initView() {
        userList = db.getAllUser();
        fullName = (EditText) findViewById(R.id.fullName);
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        password = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.register);
        btnLogin = (TextView) findViewById(R.id.login);

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

    private boolean isValidPhone(String phone) {
        if (phone.length() < 10 || phone.length() > 11) {
            return false;
        }
        if (!phone.matches("[0-9]+")) {
            return false;
        }
        if (!phone.startsWith("0")) {
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

    private boolean isValidName(String name) {
        if (name.matches("[0-9]+")) {
            return false;
        }
        return true;
    }

    private boolean isRegister(String phone){
        for (UserModels user : userList) {
            if(phone.equals(user.getPhoneNum())){
                return false;
            }
        }
        return true;
    }

    private void setUser(DatabaseHandler db) {

        db.addUser(new UserModels("Phạm Trần Tấn Đạt", "0123456789", "123"));
        db.addUser(new UserModels("Phạm Ngọc Hân", "0123987456", "123"));
        db.addUser(new UserModels("Nguyễn Thanh Tùng", "0567234987", "123"));

    }

}