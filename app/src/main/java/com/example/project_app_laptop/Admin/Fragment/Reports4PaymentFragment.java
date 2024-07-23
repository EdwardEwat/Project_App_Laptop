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

import java.util.ArrayList;

public class Reports4PaymentFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private DBHelper db;
    private ArrayList<Order> lstOr = new ArrayList<>();
    public Reports4PaymentFragment() {
    }
    public static Reports4PaymentFragment newInstance(String param1, String param2) {
        Reports4PaymentFragment fragment = new Reports4PaymentFragment();
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
        View view = inflater.inflate(R.layout.rp4payment_layout, container, false);
        db = new DBHelper(getContext());
        if(lstOr.isEmpty())
            lstOr = db.getOrders();
        int cart = calculatePaymentRevenueCart(), COD = calculatePaymentRevenueCOD();
        double percentOfCart = cart*1.0/(cart + COD), percentOfCOD = COD*1.0/(cart + COD);
        TextView txtKQ = (TextView) view.findViewById(R.id.txtRPPayment);
        String txtKQText = "Thống kê tỷ lệ các phương thức thanh toán(thẻ/tiền mặt):\n";
        txtKQText = txtKQText +(String.format("Phần trăm thanh toán bằng thẻ: %.2f\n", percentOfCart));
        txtKQText = txtKQText +(String.format("Phần trăm thanh toán bằng tiền mặt: %.2f\n", percentOfCOD));
        txtKQ.setText(txtKQText);
        return view;
    }
    private int calculatePaymentRevenueCart() {
        int total = 0;
        for (Order order : lstOr) {
            if ("Đã xác nhận".equals(order.getTrangThai()) && "Thẻ".equals(order.getHinhThucThanhToan().trim())) {
                total += 1;
            }
        }
        return total;
    }
    private int calculatePaymentRevenueCOD() {
        int total = 0;
        for (Order order : lstOr) {
            if ("Đã xác nhận".equals(order.getTrangThai()) && "Tiền mặt".equals(order.getHinhThucThanhToan().trim())) {
                total += 1;
            }
        }
        return total;
    }
}
