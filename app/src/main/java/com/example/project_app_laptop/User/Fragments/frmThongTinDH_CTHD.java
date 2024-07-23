package com.example.project_app_laptop.User.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project_app_laptop.User.Models.HoaDon;
import com.example.project_app_laptop.R;

import java.text.DecimalFormat;

public class frmThongTinDH_CTHD extends Fragment {
    private HoaDon hoaDon;
    TextView tvNgayMua, tvTrangThai, tvTongTien;
    public frmThongTinDH_CTHD() {
    }

    public static frmThongTinDH_CTHD newInstance() {
        frmThongTinDH_CTHD fragment = new frmThongTinDH_CTHD();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frm_thong_tin_d_h__c_t_h_d, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvNgayMua = view.findViewById(R.id.tvNgayMua);
        tvTrangThai = view.findViewById(R.id.tvTrangThai);
        tvTongTien = view.findViewById(R.id.tvTongTien);

        tvNgayMua.setText(hoaDon.getNgaymua());
        tvTrangThai.setText(hoaDon.getTrangthai());

        DecimalFormat formatter = new DecimalFormat("#,###");
        String tongtienFormatted = formatter.format(hoaDon.getTongtien());
        tongtienFormatted = tongtienFormatted.replace(",", ".");
        tvTongTien.setText(tongtienFormatted + "đ");
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }
}