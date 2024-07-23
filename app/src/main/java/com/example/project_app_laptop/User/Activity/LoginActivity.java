package com.example.project_app_laptop.User.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.User.Controller.AccountHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.R;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtTenTK, edtMatKhau;
    TextView tvDangKy, tvLoi1, tvLoi2, tvLoi3;
    ImageView btnHome;
    AccountHandler accountHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addcontrols();
        addevents();
    }
    void addcontrols()
    {
        btnLogin = findViewById(R.id.btnLogin);
        edtTenTK = findViewById(R.id.edtTenTK);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        tvDangKy = findViewById(R.id.tvDangKy);
        btnHome = findViewById(R.id.btnHome);
        tvLoi1 = findViewById(R.id.tvLoi1);
        tvLoi2 = findViewById(R.id.tvLoi2);
        tvLoi3 = findViewById(R.id.tvLoi3);
        accountHandler = new AccountHandler(LoginActivity.this, "LaptopShop.db", null, 1);
    }
    void addevents()
    {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkTenTK() && checkMatKhau())
                {
                    Account acc = accountHandler.CheckLogin(edtTenTK.getText().toString(), edtMatKhau.getText().toString());
                    if (acc != null)
                    {
                        tvLoi3.setText("");
                        tvLoi3.setVisibility(View.GONE);
                        Intent myit = new Intent(LoginActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Account", acc);
                        myit.putExtras(bundle);
                        startActivity(myit);
                    }
                    else
                    {
                        tvLoi3.setText("Tên tài khoản của bạn hoặc Mật khẩu không đúng, vui lòng thử lại");
                        tvLoi3.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    boolean checkTenTK()
    {
        if (edtTenTK.getText().toString().isEmpty())
        {
            tvLoi1.setVisibility(View.VISIBLE);
            tvLoi1.setText("Vui lòng điền vào mục này.");
            return false;
        }
        tvLoi1.setText("");
        tvLoi1.setVisibility(View.GONE);
        return true;
    }
    boolean checkMatKhau()
    {
        if (edtMatKhau.getText().toString().isEmpty())
        {
            tvLoi2.setVisibility(View.VISIBLE);
            tvLoi2.setText("Vui lòng điền vào mục này.");
            return false;
        }
        tvLoi2.setText("");
        tvLoi2.setVisibility(View.GONE);
        return true;
    }
}