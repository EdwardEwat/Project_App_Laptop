package com.example.project_app_laptop.User.Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.project_app_laptop.User.Controller.CTGHHandler;
import com.example.project_app_laptop.User.Controller.GioHangHandler;
import com.example.project_app_laptop.User.Fragments.frmAccount_main;
import com.example.project_app_laptop.User.Fragments.frmHome_main;
import com.example.project_app_laptop.User.Fragments.frmBill_main;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.R;

public class MainActivity extends AppCompatActivity {
    AHBottomNavigation nav_bottom;
    CTGHHandler ctghHandler;
    GioHangHandler gioHangHandler;
    Account account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIt();
        addcontrols();
        addevents();
    }
    void addcontrols()
    {
        nav_bottom = findViewById(R.id.nav_bottom_main);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_1, R.drawable.baseline_home_24, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_2, R.drawable.baseline_shopping_cart_24, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_3, R.drawable.baseline_playlist_add_check_circle_24, R.color.white);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.tab_4, R.drawable.baseline_account_circle_24, R.color.white);

        nav_bottom.addItem(item1);
        nav_bottom.addItem(item2);
        nav_bottom.addItem(item3);
        nav_bottom.addItem(item4);

        nav_bottom.setColored(false);

        ctghHandler = new CTGHHandler(MainActivity.this, "LaptopShop.db", null, 1);
        gioHangHandler = new GioHangHandler(MainActivity.this, "LaptopShop.db", null, 1);

        if (account != null)
        {
            frmHome_main fragment = new frmHome_main();
            Bundle bundle = new Bundle();
            bundle.putSerializable("Account", account);
            fragment.setArguments(bundle);
            replaceFragment(fragment);
        }
        else
        {
            replaceFragment(new frmHome_main());
        }
    }
    void getIt()
    {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            account = (Account) bundle.get("Account");
        }
    }
    void addevents()
    {
        nav_bottom.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (account != null)
                {
                    if (position == 0)
                    {
                        frmHome_main fragment = new frmHome_main();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Account", account);
                        fragment.setArguments(bundle);
                        replaceFragment(fragment);
                        return true;
                    }
                    if (position == 1)
                    {
                        Intent intent = new Intent(MainActivity.this, CartActivity.class);
                        intent.putExtra("Account", account);
                        startActivity(intent);
                        return true;
                    }
                    if (position == 2)
                    {
                        frmBill_main fragment = new frmBill_main();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Account", account);
                        fragment.setArguments(bundle);
                        replaceFragment(fragment);
                        return true;
                    }
                    if (position == 3)
                    {
                        frmAccount_main fragment = new frmAccount_main();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Account", account);
                        fragment.setArguments(bundle);
                        replaceFragment(fragment);
                        return true;
                    }
                }
                else
                {
                    if (position == 0)
                    {
                        replaceFragment(new frmHome_main());
                        return true;
                    }
                    if (position == 1)
                    {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    if (position == 2)
                    {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        return true;
                    }
                    if (position == 3)
                    {
                        replaceFragment(new frmAccount_main());
                        return true;
                    }
                }
                return false;
            }
        });
    }
    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frm_layout_main, fragment).commit();
    }
}