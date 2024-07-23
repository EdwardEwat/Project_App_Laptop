package com.example.project_app_laptop.User.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.User.Controller.CTGHHandler;
import com.example.project_app_laptop.User.Controller.GioHangHandler;
import com.example.project_app_laptop.User.Controller.ProductHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.Product;
import com.example.project_app_laptop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    ImageView imgBack, imgSP;
    TextView tvTenSP, tvSoLuong, tvDaBan, tvGiaBan, tvMoTa, tvCPU, tvRam, tvOcung, tvVGA, tvManhinh, tvHDH, tvPin, tvTrongLuong, tvTittleMoTa;
    Button btnAddToCart;
    ArrayList<Product> lst;
    ProductHandler productHandler;
    Account account;
    GioHangHandler gioHangHandler;
    CTGHHandler ctghHandler;
    Product pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        addcontrols();
        getIt();
        addevents();
    }
    void addcontrols()
    {
        imgBack = findViewById(R.id.imgBack);
        imgSP = findViewById(R.id.imgSP);
        tvTenSP = findViewById(R.id.tvTenSP);
        tvSoLuong = findViewById(R.id.tvSoLuong);
        tvDaBan = findViewById(R.id.tvDaBan);
        tvGiaBan = findViewById(R.id.tvGiaBan);
        tvMoTa = findViewById(R.id.tvMoTa);
        tvCPU = findViewById(R.id.tvCPU);
        tvRam = findViewById(R.id.tvRam);
        tvOcung = findViewById(R.id.tvOcung);
        tvVGA = findViewById(R.id.tvVGA);
        tvManhinh = findViewById(R.id.tvManhinh);
        tvHDH = findViewById(R.id.tvHDH);
        tvPin = findViewById(R.id.tvPin);
        tvTittleMoTa = findViewById(R.id.tvTittleMoTa);
        tvTrongLuong = findViewById(R.id.tvTrongLuong);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        productHandler = new ProductHandler(DetailActivity.this, "LaptopShop.db", null, 1);
        gioHangHandler = new GioHangHandler(DetailActivity.this, "LaptopShop.db", null, 1);
        ctghHandler = new CTGHHandler(DetailActivity.this, "LaptopShop.db", null, 1);
    }
    void addevents()
    {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account == null)
                {
                    Intent intent = new Intent(DetailActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    int magiohang = gioHangHandler.getMaGioHang(account.getMataikhoan());
                    if (ctghHandler.insertGHByButtonMua(magiohang, pro) == true)
                    {
                        showSuccessDialog();
                    }
                }
            }
        });
    }
    void getIt()
    {
        Bundle bundle = getIntent().getExtras();
        pro = (Product) bundle.get("Product");
        account = (Account) bundle.get("Account");


        int resourceid = getResources().getIdentifier(pro.getHinhanh(), "drawable", getPackageName());
        imgSP.setImageResource(resourceid);

        tvTenSP.setText(pro.getTensp());
        tvSoLuong.setText(String.valueOf(pro.getSoluong()) + tvSoLuong.getText());
        tvDaBan.setText(String.valueOf(productHandler.getSoLuongDaBanProduct(pro.getMasp())));

        DecimalFormat formatter = new DecimalFormat("#,###");
        String giaBanFormatted = formatter.format(pro.getGiaban());
        giaBanFormatted = giaBanFormatted.replace(",", ".");

        tvGiaBan.setText(giaBanFormatted + "đ");

        tvMoTa.setText(pro.getMota());
        tvCPU.setText(pro.getCpu());
        tvRam.setText(pro.getRam());
        tvOcung.setText(pro.getOcung());
        tvVGA.setText(pro.getVga());
        tvManhinh.setText(pro.getManhinh());
        tvHDH.setText(pro.getHedieuhanh());
        tvPin.setText(pro.getPin());
        tvTrongLuong.setText(pro.getTrongluong());
        tvTittleMoTa.setText(tvTittleMoTa.getText() + pro.getTensp());

        if (pro.getSoluong() == 0)
        {
            btnAddToCart.setEnabled(false);
            btnAddToCart.setBackgroundColor(Color.parseColor("#4C4C4C"));
        }
    }
    void showSuccessDialog()
    {
        View view = LayoutInflater.from(DetailActivity.this).inflate(R.layout.success_dialog, null);

        final Dialog dialog = new Dialog(DetailActivity.this);
        dialog.setContentView(view);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        TextView tvThongBao;
        tvThongBao = dialog.findViewById(R.id.tvThongBao);
        tvThongBao.setText("Sản phẩm đã được thêm vào Giỏ hàng");

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 2000);
    }
}