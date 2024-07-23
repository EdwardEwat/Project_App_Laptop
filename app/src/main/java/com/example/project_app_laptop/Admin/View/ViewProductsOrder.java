package com.example.project_app_laptop.Admin.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.Admin.Adapter.ListProductsOrderAdapter;
import com.example.project_app_laptop.Admin.Model.Order;
import com.example.project_app_laptop.R;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewProductsOrder extends AppCompatActivity {
    Order od;
    ImageView btnBack;
    ListProductsOrderAdapter lstPOAdapter;
    ListView lstPO;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_products_order);
        addControls();
        Intent it = getIntent();
        od = (Order) it.getSerializableExtra("Order");
        if(od != null){
            lstPOAdapter = new ListProductsOrderAdapter(getApplicationContext(), R.layout.list_view_products_order, od.getLstProduct());
            lstPO.setAdapter(lstPOAdapter);
        }
    }
    public void addControls(){
        btnBack = (ImageView) findViewById(R.id.btnBack);
        lstPO = (ListView) findViewById(R.id.lstPO);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
