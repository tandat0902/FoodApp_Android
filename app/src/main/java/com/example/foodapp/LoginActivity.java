package com.example.foodapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodapp.models.EventBus.sumPriceEvent;
import com.example.foodapp.models.ProductModels;
import com.example.foodapp.models.UserModels;
import com.example.foodapp.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    TextView btnRegister;
    EditText phoneNum, password;
    int id;
    String name;

    DatabaseHandler db = new DatabaseHandler(this);
    List<UserModels> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(db.isTableEmpty(db.TABLE_PRODUCT)){
            setUser(db);
        }

        initView();
        initBtnLogin();
        initbtnRegister();

        if(Utils.rememberUser != null){
            phoneNum.setText(Utils.rememberUser.getPhoneNum());
            password.setText(Utils.rememberUser.getPassword());
            Utils.rememberUser = new UserModels();
        }

    }

    private void initBtnLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(phoneNum.getText().toString().isEmpty()){
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
                    if(!isValidPhone(phoneNum.getText().toString())){
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
                        String phone = phoneNum.getText().toString();
                        String ps = password.getText().toString();
                        if (isLogin(phone, ps) == -1){
                            String message = "Bạn chưa đăng ký tài khoản!";
                            String negative = "OK";
                            thongBao(message, negative);
                            return;
                        } else if (isLogin(phone, ps) == 0) {
                            String message = "Mật khẩu của bạn không đúng!";
                            String negative = "OK";
                            thongBao(message, negative);
                            return;
                        } else {
                            Utils.user_current = new UserModels();
                            Utils.user_current.setId(id);
                            Utils.user_current.setPhoneNum(phone);
                            Utils.user_current.setPassword(ps);
                            Utils.user_current.setFullName(name);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }

            }
        });
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


    private void initbtnRegister() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }

    private void initView() {
        userList = db.getAllUser();
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        password = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.login);
        btnRegister = (TextView) findViewById(R.id.register);
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


    private void setUser(DatabaseHandler db) {

        db.addUser(new UserModels("Phạm Trần Tấn Đạt", "0123456789", "123"));
        db.addUser(new UserModels("Phạm Ngọc Hân", "0123987456", "123"));
        db.addUser(new UserModels("Nguyễn Thanh Tùng", "0567234987", "123"));

    }

    private void clearText() {
        phoneNum.setText("");
        password.setText("");
    }

    private int isLogin(String phone, String ps){
        for (UserModels user : userList) {
            if(phone.equals(user.getPhoneNum())){
                if(ps.equals(user.getPassword())){
                    id = user.getId();
                    name = user.getFullName();
                    return 1;
                } else{
                    return 0;
                }
            }
        }
        return -1;
    }

}
