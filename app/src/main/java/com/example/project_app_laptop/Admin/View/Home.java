package com.example.project_app_laptop.Admin.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.project_app_laptop.Admin.Fragment.AccountFragment;
import com.example.project_app_laptop.Admin.Fragment.ManageFragment;
import com.example.project_app_laptop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {
    TextView tvAdmin;
    FrameLayout frameLayout;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        addControl();
        addEvent();
    }

    public void addControl() {
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String tenTaiKhoan = preferences.getString("username", "");

        tvAdmin = findViewById(R.id.tvAdmin);
        tvAdmin.setText(tenTaiKhoan);
        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottomNav);
    }

    public void addEvent() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                    if(id == R.id.mnQuanLy) {
                    loadFrame(new ManageFragment());
                    return true;
                }

                else if(id == R.id.mnHuongDan) {
                    Intent intent = new Intent(getApplication(), HuongDanActivity.class);
                    startActivity(intent);
                    return true;
                }

                else if(id == R.id.mnTaiKhoan) {
                    loadFrame(new AccountFragment());
                    return true;
                }
                else return false;
            }
        });
        loadFrame(new ManageFragment());
    }

    public void loadFrame(Fragment fragment) {
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }
}