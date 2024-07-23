package com.example.project_app_laptop.Admin.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.Admin.Adapter.OrdersAdapter;
import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.Model.Order;
import com.example.project_app_laptop.R;

import java.util.ArrayList;

public class OrdersActivity extends AppCompatActivity {
    DBHelper db;
    OrdersAdapter orAdapter;
    ArrayList<Order> lstOr = new ArrayList<>();
    ListView lstOrs;
    ImageView btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        addControls();
        db = new DBHelper(getApplicationContext());
        if(lstOr.isEmpty())
            lstOr = db.getOrders();
//        for (Order a: lstOr) {
//            db.calculateAndUpdateTotals(a.getMaHoaDon());
//        }
        orAdapter = new OrdersAdapter(getApplicationContext(),R.layout.list_view_orders, lstOr, db);
        lstOrs.setAdapter(orAdapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void addControls(){
        lstOrs = (ListView) findViewById(R.id.lstOrders);
        btnBack = findViewById(R.id.btnBack);
    }
}
