package com.example.project_app_laptop.Admin.View;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.CustomerAdapter;
import com.example.project_app_laptop.Admin.Model.Customer;
import com.example.project_app_laptop.R;

import java.util.ArrayList;

public class CustomerActivity extends AppCompatActivity {
    private ListView lvDSKH;
    private ImageView btnBack;
    private ArrayList<Customer> customerList;
    private CustomerAdapter adapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        lvDSKH = findViewById(R.id.lvDSKH);
        btnBack = findViewById(R.id.btnBack);
        dbHelper = new DBHelper(this);

        customerList = dbHelper.getAllCustomers();
        adapter = new CustomerAdapter(this, customerList);

        lvDSKH.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}