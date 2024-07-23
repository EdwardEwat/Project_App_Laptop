package com.example.project_app_laptop.User.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project_app_laptop.User.Controller.AccountHandler;
import com.example.project_app_laptop.User.Models.HoaDon;
import com.example.project_app_laptop.R;

public class frmThongTinKH_CTHD extends Fragment {
    private HoaDon hoaDon;
    AccountHandler accountHandler;
    TextView tvTenKH, tvSDT, tvDiaChi, tvHinhThucThanhToan;
    public frmThongTinKH_CTHD() {
    }

    public static frmThongTinKH_CTHD newInstance() {
        frmThongTinKH_CTHD fragment = new frmThongTinKH_CTHD();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frm_thong_tin_k_h__c_t_h_d, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountHandler = new AccountHandler(view.getContext(), "LaptopShop.db", null, 1);

        tvTenKH = view.findViewById(R.id.tvTenKH);
        tvSDT = view.findViewById(R.id.tvSDT);
        tvDiaChi = view.findViewById(R.id.tvDiaChi);
        tvHinhThucThanhToan = view.findViewById(R.id.tvHinhThucThanhToan);

        tvTenKH.setText(accountHandler.getTenKH(hoaDon.getMatk()));
        tvSDT.setText(hoaDon.getSdt());
        tvDiaChi.setText(hoaDon.getDiachi());
        tvHinhThucThanhToan.setText(hoaDon.getHinhthucthanhtoan());
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }
}