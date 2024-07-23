package com.example.project_app_laptop.Admin.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.Model.Order;
import com.example.project_app_laptop.Admin.Model.ProductOrder;
import com.example.project_app_laptop.R;

import java.util.ArrayList;

public class Reports4BandFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private DBHelper db;
    private ArrayList<Order> lstOr = new ArrayList<>();
    public Reports4BandFragment() {
    }
    public static Reports4BandFragment newInstance(String param1, String param2) {
        Reports4BandFragment fragment = new Reports4BandFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rp4band_layout, container, false);
        db = new DBHelper(getContext());
        if(lstOr.isEmpty())
            lstOr = db.getOrders();
        TextView txtKQ = (TextView) view.findViewById(R.id.txtRPBands);
        ArrayList<String> Bands = db.getBands();
        String txtKQText = "Thống kê theo hãng: \n";
        for(int i=0;i<Bands.size();i++){
            txtKQText = txtKQText + (Bands.get(i)+": " + calculateBandRevenue(i+1)+" sản phẩm\n");
        }
        txtKQ.setText(txtKQText);
        return view;
    }
    private int calculateBandRevenue(int a) {
        int total = 0;
        for (Order order : lstOr) {
            if ("Đã xác nhận".equals(order.getTrangThai())) {
                for(ProductOrder po : order.getLstProduct()){
                    if(po.getMaHang() == a)
                        total += po.getSoLuongChiTiet();
                }
            }
        }
        return total;
    }
}