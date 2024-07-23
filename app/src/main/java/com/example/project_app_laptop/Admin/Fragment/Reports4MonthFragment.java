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
import com.example.project_app_laptop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Reports4MonthFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private DBHelper db;
    private ArrayList<Order> lstOr = new ArrayList<>();
    public Reports4MonthFragment() {
    }
    public static Reports4MonthFragment newInstance(String param1, String param2) {
        Reports4MonthFragment fragment = new Reports4MonthFragment();
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
        View view = inflater.inflate(R.layout.rp4month_layout, container, false);
        db = new DBHelper(getContext());
        if(lstOr.isEmpty())
            lstOr = db.getOrders();
        TextView txtKQ = (TextView) view.findViewById(R.id.txtRPBands);
        String txtKQText = "Thống kê theo tháng:\n";
        for(int i = 1; i < 13; i++){
            String nam = "2024", thang = String.valueOf(i);
            if(i < 10)
                thang = "0" + String.valueOf(i);
            int kq = calculateTotalRevenue(thang + "/" + nam);
            DecimalFormat decimalFormat = new DecimalFormat("#,###" + "đ\n");
            String formattedResult = decimalFormat.format(kq);
            txtKQText = txtKQText + (thang + "/" + nam + ": " + formattedResult);
        }
        txtKQ.setText(txtKQText);
        return view;
    }
    private int calculateTotalRevenue(String s) {
        int total = 0;
        for (Order order : lstOr) {
            if ("Đã xác nhận".equals(order.getTrangThai())) {
                String[] ngayMua = order.getNgayMua().split(" ");
                if(ngayMua[0].endsWith(s))
                    total += order.getTongTien();
            }
        }
        return total;
    }
}
