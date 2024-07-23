package com.example.project_app_laptop.Admin.View;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.Model.Order;
import com.example.project_app_laptop.Admin.Model.ProductOrder;
import com.example.project_app_laptop.R;

import java.io.Serializable;
import java.sql.Time;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class OrderInformation extends AppCompatActivity {
    TextView txtID, txtUID, txtDate, txtAddress, txtPhone, txtPayment, txtTotal, txtStatus;
    Button btnXacNhan1, btnPO;
    Order od;
    ImageView btnBack;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_information);
        addControls();
        addEvents();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addControls(){
        Intent it = getIntent();
        txtID = (TextView) findViewById(R.id.txtID);
        txtUID = (TextView) findViewById(R.id.txtUserID);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtPhone = (TextView) findViewById(R.id.txtPhone);
        txtPayment = (TextView) findViewById(R.id.txtPayment);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        btnXacNhan1 = (Button) findViewById(R.id.btnXacNhan1);
        btnPO = (Button) findViewById(R.id.btnPO);
        btnBack = findViewById(R.id.btnBack);
        od = (Order) it.getSerializableExtra("Order");
        if(od != null){
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDateTime = dateTime.format(formatter);
            String[] date = od.getNgayMua().split(" "), now = formattedDateTime.split(" ");
            String[] valueOfDate = date[0].split("/");
            LocalDate dateOfOrder = LocalDate.of(Integer.parseInt(valueOfDate[2]), Integer.parseInt(valueOfDate[1]), Integer.parseInt(valueOfDate[0]));
            LocalDate dateOfNow = LocalDate.now();
            Time timeOfOrder = Time.valueOf(date[1]);
            Time timeOfNow = Time.valueOf(now[1]);
            long millisecondsBetween = Math.abs(timeOfNow.getTime() - timeOfOrder.getTime());
            if ("Chưa xác nhận".equals(od.getTrangThai().trim()) && (dateOfOrder.isBefore(dateOfNow) || ((dateOfOrder.equals(dateOfNow) && (millisecondsBetween >= 2 * 60 * 1000))))) {
                btnXacNhan1.setVisibility(View.VISIBLE);
            } else {
                btnXacNhan1.setVisibility(View.GONE);
            }
            txtID.setText(String.valueOf(od.getMaHoaDon()));
            txtUID.setText(String.format("Tên khách hàng: %s", String.valueOf(od.getTenKhachHang())));
            txtDate.setText(String.format("Ngày mua: %s", String.valueOf(od.getNgayMua())));
            txtAddress.setText(String.format("Địa chỉ: %s", String.valueOf(od.getDiaChi())));
            txtPhone.setText(String.format("SĐT: %s", String.valueOf(od.getSDT())));
            txtPayment.setText(String.format("Hình thức thanh toán: %s", String.valueOf(od.getHinhThucThanhToan())));
            DecimalFormat decimalFormat = new DecimalFormat("#,###" + "₫");
            String tong = decimalFormat.format(od.getTongTien());
            txtTotal.setText("Tổng tiền: " + tong);
//            txtTotal.setText(String.format("Tổng tiền: %s đ", String.valueOf(od.getTongTien())));
            txtStatus.setText(String.format("Trạng thái: %s", String.valueOf(od.getTrangThai())));
        }
    }
    public void addEvents(){
        btnXacNhan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(getApplicationContext());
                db.comfirmStatus(Integer.parseInt(txtID.getText().toString()));
                btnXacNhan1.setVisibility(View.GONE);
            }
        });
        btnPO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(), ViewProductsOrder.class);
                if(od != null)
                    it.putExtra("Order", (Serializable) od);
                it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(it);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}