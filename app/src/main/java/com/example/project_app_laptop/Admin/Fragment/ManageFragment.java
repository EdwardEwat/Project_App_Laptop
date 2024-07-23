package com.example.project_app_laptop.Admin.Fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.project_app_laptop.R;
import com.example.project_app_laptop.Admin.View.BrandActivity;
import com.example.project_app_laptop.Admin.View.CustomerActivity;
import com.example.project_app_laptop.Admin.View.OrdersActivity;
import com.example.project_app_laptop.Admin.View.ProductActivity;
import com.example.project_app_laptop.Admin.View.ReportsActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageFragment extends Fragment {
    LinearLayout btnSanPham, btnDonHang, btnKhachHang, btnNhanVien, btnBaoCao, btnHang;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ManageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageFragment newInstance(String param1, String param2) {
        ManageFragment fragment = new ManageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage, container, false);
        addControl(view);
        addEvent();
        return view;
    }

    void addControl(View view) {
        btnSanPham = view.findViewById(R.id.btnSanPham);
        btnDonHang = view.findViewById(R.id.btnDonHang);
        btnKhachHang = view.findViewById(R.id.btnKhachHang);
        btnNhanVien = view.findViewById(R.id.btnNhanVien);
        btnBaoCao = view.findViewById(R.id.btnBaoCao);
        btnHang = view.findViewById(R.id.btnHang);
    }
    void addEvent() {
        btnSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                startActivity(intent);
            }
        });

        btnHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BrandActivity.class);
                startActivity(intent);
            }
        });

        btnKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerActivity.class);
                startActivity(intent);
            }
        });
        btnBaoCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReportsActivity.class);
                startActivity(intent);
            }
        });
        btnDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrdersActivity.class);
                startActivity(intent);
            }
        });
    }
}