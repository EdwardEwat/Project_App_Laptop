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
import android.widget.TextView;

import com.example.project_app_laptop.User.Adapters.BillAdapter;
import com.example.project_app_laptop.User.Controller.HoaDonHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.HoaDon;
import com.example.project_app_laptop.R;

import java.util.ArrayList;


public class frmBill_main extends Fragment {
    RecyclerView recyBill;
    TextView tvCheck;
    ArrayList<Integer> lstmaHD;
    ArrayList<HoaDon> lstHD;
    BillAdapter billAdapter;
    HoaDonHandler hoaDonHandler;
    Account account;
    public frmBill_main() {
    }

    public static frmBill_main newInstance() {
        frmBill_main fragment = new frmBill_main();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frm_bill_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyBill = view.findViewById(R.id.recybill);
        tvCheck = view.findViewById(R.id.tvCheck);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("Account")) {
            account = (Account) bundle.getSerializable("Account");
        }

        hoaDonHandler = new HoaDonHandler(getContext(), "LaptopShop.db", null, 1);

        lstmaHD = hoaDonHandler.getAllMaHD(account.getMataikhoan());
        if (lstmaHD != null && !lstmaHD.isEmpty())
        {
            tvCheck.setVisibility(View.GONE);
        }
        else
        {
            tvCheck.setVisibility(View.VISIBLE);
            tvCheck.setText("Không có đơn hàng nào");
        }
        lstHD = hoaDonHandler.loadHoaDon(lstmaHD);

        billAdapter = new BillAdapter(lstHD, getContext(), account);
        recyBill.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyBill.setItemAnimator(new DefaultItemAnimator());

        recyBill.setAdapter(billAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        lstHD = hoaDonHandler.loadHoaDon(lstmaHD);

        billAdapter = new BillAdapter(lstHD, getContext(), account);
        recyBill.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyBill.setItemAnimator(new DefaultItemAnimator());

        recyBill.setAdapter(billAdapter);
    }
}