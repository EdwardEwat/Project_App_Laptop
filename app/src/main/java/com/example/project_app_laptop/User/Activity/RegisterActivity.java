package com.example.project_app_laptop.User.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.User.Controller.AccountHandler;
import com.example.project_app_laptop.User.Controller.GioHangHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.R;

public class RegisterActivity extends AppCompatActivity {
    EditText edtHoTenKH, edtTenTK, edtMatKhau, edtXacNhanMatKhau;
    TextView tvLoi1, tvLoi2, tvLoi3, tvLoi4, tvLoi5, tvDangNhap;
    Button btnRegister;
    ImageView btnHome;
    AccountHandler accountHandler;
    GioHangHandler gioHangHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addcontrols();
        addevents();
    }
    void addcontrols()
    {
        edtHoTenKH = findViewById(R.id.edtHoTenKH);
        edtTenTK = findViewById(R.id.edtTenTK);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        edtXacNhanMatKhau = findViewById(R.id.edtXacNhanMatKhau);
        tvLoi1 = findViewById(R.id.tvLoi1);
        tvLoi2 = findViewById(R.id.tvLoi2);
        tvLoi3 = findViewById(R.id.tvLoi3);
        tvLoi4 = findViewById(R.id.tvLoi4);
        tvLoi5 = findViewById(R.id.tvLoi5);
        tvDangNhap = findViewById(R.id.tvDangNhap);
        btnRegister = findViewById(R.id.btnRegister);
        btnHome = findViewById(R.id.btnHome);
        accountHandler = new AccountHandler(RegisterActivity.this, "LaptopShop.db", null, 1);
        gioHangHandler = new GioHangHandler(RegisterActivity.this, "LaptopShop.db", null, 1);
    }
    void addevents()
    {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckHoTenKH() && CheckTenTK() && CheckMatKhau() && CheckXacNhanMatKhau())
                {
                    if (accountHandler.KTTrungTenTK(edtTenTK.getText().toString()))
                    {
                        accountHandler.insertAccount(edtHoTenKH.getText().toString(), edtTenTK.getText().toString(), edtMatKhau.getText().toString());
                        Account account = accountHandler.CheckLogin(edtTenTK.getText().toString(), edtMatKhau.getText().toString());
                        gioHangHandler.insertGioHang(account.getMataikhoan());
                        showSuccessDialog();
                    }
                    else
                    {
                        tvLoi4.setText("Trùng tên đăng nhập!");
                        tvLoi4.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
    boolean CheckHoTenKH()
    {
        if (edtHoTenKH.getText().toString().isEmpty())
        {
            tvLoi1.setVisibility(View.VISIBLE);
            tvLoi1.setText("Vui lòng điền vào mục này.");
            return false;
        }
        tvLoi1.setText("");
        tvLoi1.setVisibility(View.GONE);
        return true;
    }
    boolean CheckTenTK()
    {
        if (edtTenTK.getText().toString().isEmpty())
        {
            tvLoi2.setVisibility(View.VISIBLE);
            tvLoi2.setText("Vui lòng điền vào mục này.");
            return false;
        }
        tvLoi2.setText("");
        tvLoi2.setVisibility(View.GONE);
        return true;
    }
    boolean CheckMatKhau()
    {
        if (edtMatKhau.getText().toString().isEmpty())
        {
            tvLoi3.setVisibility(View.VISIBLE);
            tvLoi3.setText("Vui lòng điền vào mục này.");
            return false;
        }
        tvLoi3.setText("");
        tvLoi3.setVisibility(View.GONE);
        return true;
    }
    boolean CheckXacNhanMatKhau()
    {
        if (edtXacNhanMatKhau.getText().toString().isEmpty())
        {
            tvLoi5.setVisibility(View.VISIBLE);
            tvLoi5.setText("Vui lòng điền vào mục này.");
            return false;
        }
        if (!edtXacNhanMatKhau.getText().toString().isEmpty())
        {
            if (!edtXacNhanMatKhau.getText().toString().equals(edtMatKhau.getText().toString()))
            {
                tvLoi5.setVisibility(View.VISIBLE);
                tvLoi5.setText("Không trùng mật khẩu");
                return false;
            }
        }
        tvLoi5.setText("");
        tvLoi5.setVisibility(View.GONE);
        return true;
    }
    void showSuccessDialog()
    {
        View view = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.success_dialog, null);

        final Dialog dialog = new Dialog(RegisterActivity.this);
        dialog.setContentView(view);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        TextView tvThongBao;
        tvThongBao = dialog.findViewById(R.id.tvThongBao);
        tvThongBao.setText("Đăng ký tài khoản thành công!");

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        }, 2000);
    }
}