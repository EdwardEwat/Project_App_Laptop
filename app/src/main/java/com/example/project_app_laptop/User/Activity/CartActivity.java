package com.example.project_app_laptop.User.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_app_laptop.User.Adapters.CartAdapter;
import com.example.project_app_laptop.User.Controller.CTGHHandler;
import com.example.project_app_laptop.User.Controller.GioHangHandler;
import com.example.project_app_laptop.User.Controller.ProductHandler;
import com.example.project_app_laptop.User.Fragments.frmCart_main;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.ChiTiet;
import com.example.project_app_laptop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemClickListener {
    ImageView imgBack;
    RecyclerView recyCart;
    com.example.project_app_laptop.User.Controller.ProductHandler ProductHandler;
    Spinner spnThanhToan;
    TextView tvTongTienHang, tvTongTien, tvGioHang;
    String[] thanhtoan = { "Thanh toán khi nhận hàng", "Chuyển khoản ngân hàng" };
    ArrayAdapter<String> thanhtoanadapter;
    Account account;
    ArrayList<ChiTiet> lstCT;
    CartAdapter cartAdapter;
    CTGHHandler ctghHandler;
    GioHangHandler gioHangHandler;
    frmCart_main frmcart;
    String hinhthuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        getIt();
        addcontrols();
        addevents();
    }
    void addcontrols()
    {
        imgBack = findViewById(R.id.imgBack);
        recyCart = findViewById(R.id.recyCart);
        ProductHandler = new ProductHandler(CartActivity.this, "LaptopShop.db", null, 1);
        gioHangHandler = new GioHangHandler(CartActivity.this, "LaptopShop.db", null, 1);
        ctghHandler = new CTGHHandler(CartActivity.this, "LaptopShop.db", null, 1);

        lstCT = ctghHandler.loadCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()));
        cartAdapter = new CartAdapter(lstCT, CartActivity.this, account);

        recyCart.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
        recyCart.setItemAnimator(new DefaultItemAnimator());
        recyCart.setAdapter(cartAdapter);

        spnThanhToan = findViewById(R.id.spnThanhToan);
        tvTongTienHang = findViewById(R.id.tvTongTienHang);
        tvTongTien = findViewById(R.id.tvTongTien);

        tvGioHang = findViewById(R.id.tvGioHang);
        ArrayList<ChiTiet> temp = ctghHandler.loadCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()));
        tvGioHang.setText("GIỎ HÀNG" + " (" + String.valueOf(temp.size()) + ")");

        thanhtoanadapter = new ArrayAdapter<>(CartActivity.this, android.R.layout.simple_spinner_dropdown_item, thanhtoan);
        spnThanhToan.setAdapter(thanhtoanadapter);

        frmcart = new frmCart_main();
        frmcart.setCartActivity(this);

        Bundle bundle = new Bundle();
        bundle.putSerializable("Account", account);
        frmcart.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frmbottomcart, frmcart).commit();
    }
    void getIt()
    {
        Intent intent = getIntent();
        if (intent != null)
        {
            account = (Account) intent.getSerializableExtra("Account");
        }
    }
    void addevents()
    {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (account != null)
                {
                    Intent intent = new Intent(CartActivity.this, MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Account", account);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        spnThanhToan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hinhthuc = thanhtoan[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void updateAllCheckStatus(boolean isChecked) {
        if (isChecked == true)
        {
            int tongtienhang = 0;
            for (ChiTiet ct : lstCT) {
                ct.setChecked(isChecked);
                tongtienhang += ct.getThanhtien();
            }

            DecimalFormat formatter = new DecimalFormat("#,###");

            String tthFormatted = formatter.format(tongtienhang);
            tthFormatted = tthFormatted.replace(",", ".");
            tvTongTienHang.setText(tthFormatted + "đ");

            String ttFormatted = formatter.format(tongtienhang + 100000);
            ttFormatted = ttFormatted.replace(",", ".");
            tvTongTien.setText(ttFormatted + "đ");
        }
        else
        {
            for (ChiTiet ct : lstCT) {
                ct.setChecked(isChecked);
            }
            tvTongTienHang.setText(0 + "đ");
            tvTongTien.setText(0 + "đ");
        }

        cartAdapter = new CartAdapter(lstCT, CartActivity.this, account);

        recyCart.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
        recyCart.setItemAnimator(new DefaultItemAnimator());
        recyCart.setAdapter(cartAdapter);

    }
    public void UpdateNotify()
    {
        lstCT = ctghHandler.loadCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()));
        cartAdapter = new CartAdapter(lstCT, CartActivity.this, account);

        recyCart.setLayoutManager(new LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false));
        recyCart.setItemAnimator(new DefaultItemAnimator());
        recyCart.setAdapter(cartAdapter);
        tvTongTienHang.setText(0 + "đ");
        tvTongTien.setText(0 + "đ");

        ArrayList<ChiTiet> temp = ctghHandler.loadCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()));
        tvGioHang.setText("GIỎ HÀNG" + " (" + String.valueOf(temp.size()) + ")");
    }

    public ArrayList<ChiTiet> getLstCT() {
        return lstCT;
    }

    public String getHinhThucThanhToan()
    {
        return hinhthuc;
    }

    @Override
    public void sendData(ArrayList<ChiTiet> lstCT1) {
        boolean flag = false;
        if (frmcart != null)
        {
            frmcart.getlstchitiet(lstCT1);

            int tongtienhang = 0;
            for (ChiTiet ct : lstCT1) {
                if (ct.isChecked() == true)
                {
                    flag = true;
                    tongtienhang += ct.getThanhtien();
                }
            }

            if (flag == true)
            {
                DecimalFormat formatter = new DecimalFormat("#,###");

                String tthFormatted = formatter.format(tongtienhang);
                tthFormatted = tthFormatted.replace(",", ".");
                tvTongTienHang.setText(tthFormatted + "đ");

                String ttFormatted = formatter.format(tongtienhang + 100000);
                ttFormatted = ttFormatted.replace(",", ".");
                tvTongTien.setText(ttFormatted + "đ");
            }
            else
            {
                tvTongTienHang.setText(0 + "đ");
                tvTongTien.setText(0 + "đ");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateNotify();
    }
}