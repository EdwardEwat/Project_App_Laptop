package com.example.project_app_laptop.User.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project_app_laptop.User.Adapters.CTHDAdapter;
import com.example.project_app_laptop.User.Controller.CTHDHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.ChiTiet;
import com.example.project_app_laptop.User.Models.HoaDon;
import com.example.project_app_laptop.R;

import java.util.ArrayList;

public class frmDanhSachSP_CTHD extends Fragment {
    private HoaDon hoaDon;
    RecyclerView recycthd;
    ArrayList<Integer> lstMaSP;
    ArrayList<ChiTiet> lstCT;
    CTHDAdapter cthdAdapter;
    CTHDHandler cthdHandler;
    Account account;
    public frmDanhSachSP_CTHD() {
    }

    public static frmDanhSachSP_CTHD newInstance() {
        frmDanhSachSP_CTHD fragment = new frmDanhSachSP_CTHD();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frm_danh_sach_s_p__c_t_h_d, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recycthd = view.findViewById(R.id.recycthd);

        cthdHandler = new CTHDHandler(view.getContext(), "LaptopShop.db", null, 1);

        lstMaSP = cthdHandler.getAllMaSP(hoaDon.getMahd());
        lstCT = cthdHandler.getCTHD(hoaDon.getMahd(), lstMaSP);

        cthdAdapter = new CTHDAdapter(lstCT, view.getContext(), account);

        recycthd.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycthd.setItemAnimator(new DefaultItemAnimator());

        recycthd.setAdapter(cthdAdapter);
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }
    public void setAccount(Account account)
    {
        this.account = account;
    }
}