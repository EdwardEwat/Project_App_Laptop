package com.example.project_app_laptop.User.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.project_app_laptop.User.Adapters.ViewPagerAdapterCTHD;
import com.example.project_app_laptop.User.Controller.CTHDHandler;
import com.example.project_app_laptop.User.Controller.HoaDonHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.ChiTiet;
import com.example.project_app_laptop.User.Models.HoaDon;
import com.example.project_app_laptop.R;
import com.example.project_app_laptop.User.Fragments.frmDanhSachSP_CTHD;
import com.google.android.material.tabs.TabLayout;
import com.example.project_app_laptop.User.Fragments.frmThongTinKH_CTHD;
import com.example.project_app_laptop.User.Fragments.frmThongTinDH_CTHD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChiTietHoaDonActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;
    ViewPagerAdapterCTHD viewPagerAdapterCTHD;
    ImageView imgBack;
    TextView tvMaHoaDon, tvTB;
    HoaDon hoaDon;
    Account account;
    Button btnHuyDonHang;
    HoaDonHandler hoaDonHandler;
    CTHDHandler cthdHandler;
    ArrayList<Integer> lstMasp;
    ArrayList<ChiTiet> lstCT;
    Dialog huyDonHangdialog;
    boolean isWithin2Minutes = false;
    Handler handler = new Handler();
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        getIt();
        addcontrols();
        addevents();

        runnable = new Runnable() {
            @Override
            public void run() {
                checkOrderTime();
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }
    void addcontrols()
    {
        imgBack = findViewById(R.id.imgBack);
        tvTB = findViewById(R.id.tvTB);
        btnHuyDonHang = findViewById(R.id.btnHuyDonHang);

        hoaDonHandler = new HoaDonHandler(ChiTietHoaDonActivity.this, "LaptopShop.db", null, 1);
        cthdHandler = new CTHDHandler(ChiTietHoaDonActivity.this, "LaptopShop.db", null, 1);

        tvMaHoaDon = findViewById(R.id.tvMaHoaDon);
        tvMaHoaDon.setText(String.valueOf(hoaDon.getMahd()));

        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.view_pager);

        viewPagerAdapterCTHD = new ViewPagerAdapterCTHD(getSupportFragmentManager());

        frmThongTinKH_CTHD fragment1 = frmThongTinKH_CTHD.newInstance();
        fragment1.setHoaDon(hoaDon);
        viewPagerAdapterCTHD.addFragment(fragment1);

        frmThongTinDH_CTHD fragment2 = frmThongTinDH_CTHD.newInstance();
        fragment2.setHoaDon(hoaDon);
        viewPagerAdapterCTHD.addFragment(fragment2);

        frmDanhSachSP_CTHD fragment3 = frmDanhSachSP_CTHD.newInstance();
        fragment3.setHoaDon(hoaDon);
        fragment3.setAccount(account);
        viewPagerAdapterCTHD.addFragment(fragment3);

        mViewPager.setAdapter(viewPagerAdapterCTHD);

        mTabLayout.setupWithViewPager(mViewPager);
    }
    void addevents()
    {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnHuyDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPopHuyDonHang();
            }
        });
    }
    void getIt()
    {
        Intent intent = getIntent();
        hoaDon = (HoaDon) intent.getSerializableExtra("HoaDon");
        account = (Account) intent.getSerializableExtra("Account");
    }
    void checkOrderTime() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date purchaseDate = sdf.parse(hoaDon.getNgaymua());
            Date currentDate = new Date();
            long diffInMillies = Math.abs(currentDate.getTime() - purchaseDate.getTime());
            long diffInMinutes = diffInMillies / (60 * 1000);
            if (diffInMinutes <= 1) {
                isWithin2Minutes = true;
                btnHuyDonHang.setVisibility(View.VISIBLE);
                tvTB.setVisibility(View.VISIBLE);
            } else {
                isWithin2Minutes = false;
                btnHuyDonHang.setVisibility(View.GONE);
                tvTB.setVisibility(View.GONE);
            }
            if (!isWithin2Minutes && huyDonHangdialog != null && huyDonHangdialog.isShowing())
            {
                huyDonHangdialog.dismiss();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);

    }
    void showSuccessDialog()
    {
        View view = LayoutInflater.from(ChiTietHoaDonActivity.this).inflate(R.layout.success_dialog, null);

        final Dialog dialog = new Dialog(ChiTietHoaDonActivity.this);
        dialog.setContentView(view);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        TextView tvThongBao;
        tvThongBao = dialog.findViewById(R.id.tvThongBao);
        tvThongBao.setText("Hủy đơn hàng thành công");

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                    finish();
                }
            }
        }, 2000);
    }
    void OpenPopHuyDonHang()
    {
        huyDonHangdialog = new Dialog(ChiTietHoaDonActivity.this);
        huyDonHangdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        huyDonHangdialog.setContentView(R.layout.popup_huydonhang);

        Window window = huyDonHangdialog.getWindow();
        if (window == null)
        {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowattri = window.getAttributes();
        windowattri.gravity = Gravity.CENTER;
        window.setAttributes(windowattri);

        TextView tvCo, tvKo;

        tvCo = huyDonHangdialog.findViewById(R.id.tvCo);
        tvKo = huyDonHangdialog.findViewById(R.id.tvKo);

        tvKo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                huyDonHangdialog.dismiss();
            }
        });
        tvCo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstMasp = cthdHandler.getAllMaSP(hoaDon.getMahd());
                lstCT = cthdHandler.getCTHD(hoaDon.getMahd(), lstMasp);
                hoaDonHandler.UpdateLaiSoLuongSPKhiHuy(lstCT);
                hoaDonHandler.deleteHoaDon(hoaDon.getMahd());
                showSuccessDialog();
            }
        });
        huyDonHangdialog.setCanceledOnTouchOutside(false);
        huyDonHangdialog.show();
    }
}