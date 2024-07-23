package com.example.project_app_laptop.Admin.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.Model.Order;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import com.example.project_app_laptop.R;
import com.example.project_app_laptop.Admin.View.OrderInformation;

public class OrdersAdapter extends ArrayAdapter<Order> {
    Context context;
    int IDLayout;
    ArrayList<Order> lstOr;

    DBHelper db;

    public OrdersAdapter(@NonNull Context context, int IDLayout, ArrayList<Order> lstOr, DBHelper db) {
        super(context, IDLayout, lstOr);
        this.context = context;
        this.IDLayout = IDLayout;
        this.lstOr = lstOr;
        this.db = db;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Order order =lstOr.get(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(IDLayout, null, true);
        }
        TextView txtMaDonHang = (TextView) convertView.findViewById(R.id.txtMaDonHang);
        txtMaDonHang.setText(String.format("Mã đơn hàng: %s", order.getMaHoaDon()));
        TextView txtNgayMua = (TextView) convertView.findViewById(R.id.txtNgayMua);
        txtNgayMua.setText(String.format("Ngày mua: %s", order.getNgayMua()));
        TextView txtTrangThai = (TextView) convertView.findViewById(R.id.txtTrangThai);
        txtTrangThai.setText(String.format("Trạng thái: %s", order.getTrangThai()));
        Button btnXacNhan = (Button) convertView.findViewById(R.id.btnXacNhan);

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedDateTime = dateTime.format(formatter);
        String[] date = order.getNgayMua().split(" "), now = formattedDateTime.split(" ");
        String[] valueOfDate = date[0].split("/");
        LocalDate dateOfOrder = LocalDate.of(Integer.parseInt(valueOfDate[2]), Integer.parseInt(valueOfDate[1]), Integer.parseInt(valueOfDate[0]));
        LocalDate dateOfNow = LocalDate.now();
        Time timeOfOrder = Time.valueOf(date[1]);
        Time timeOfNow = Time.valueOf(now[1]);
        long millisecondsBetween = Math.abs(timeOfNow.getTime() - timeOfOrder.getTime());
        if ("Chưa xác nhận".equals(order.getTrangThai().trim()) && (dateOfOrder.isBefore(dateOfNow) || ((dateOfOrder.equals(dateOfNow) && (millisecondsBetween >= 2 * 60 * 1000))))) {
            btnXacNhan.setVisibility(View.VISIBLE);
        } else {
            btnXacNhan.setVisibility(View.GONE);
        }
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác nhận hóa đơn
                db.comfirmStatus(order.getMaHoaDon());
                btnXacNhan.setVisibility(View.GONE);
            }
        });
        Button btnThongTin = (Button) convertView.findViewById(R.id.btnThongTin);
        btnThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển tab sang thông tin Order
                Intent it = new Intent(context, OrderInformation.class);
                it.putExtra("Order", (Serializable) order);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(it);
            }
        });
        return convertView;
    }
}