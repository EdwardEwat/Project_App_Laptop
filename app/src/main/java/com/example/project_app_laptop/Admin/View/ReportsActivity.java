package com.example.project_app_laptop.Admin.View;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_app_laptop.Admin.Fragment.Reports4BandFragment;
import com.example.project_app_laptop.Admin.Fragment.Reports4MonthFragment;
import com.example.project_app_laptop.Admin.Fragment.Reports4PaymentFragment;
import com.example.project_app_laptop.R;

import java.util.ArrayList;

public class ReportsActivity extends AppCompatActivity {
    Spinner spnReports;
    ImageView btnBack;
    FrameLayout frameLayout;
    ArrayList<String> data = new ArrayList<>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
        addControls();
        initDatas();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, data);
        spnReports.setAdapter(adapter);
        spnReports.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedString = data.get(position);
                if("Theo tháng".equals(selectedString))
                    loadFrame(new Reports4MonthFragment());
                else if ("Theo phương thức thanh toán".equals(selectedString))
                    loadFrame(new Reports4PaymentFragment());
                else if("Theo hãng".equals(selectedString))
                    loadFrame(new Reports4BandFragment());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void addControls(){
        spnReports = (Spinner) findViewById(R.id.spnReports);
        frameLayout = (FrameLayout) findViewById(R.id.frmRPs);
        btnBack = (ImageView) findViewById(R.id.btnBack);
    }
    public void initDatas(){
        data.add("Theo tháng");
        data.add("Theo phương thức thanh toán");
        data.add("Theo hãng");
    }
    public void loadFrame(Fragment fragment) {
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frmRPs, fragment);
        fragmentTransaction.commit();
    }
}
