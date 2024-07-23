package com.example.project_app_laptop.User.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_app_laptop.User.Adapters.ProductAdapter;
import com.example.project_app_laptop.User.Controller.ProductHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.Product;
import com.example.project_app_laptop.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class DetailCatActivity extends AppCompatActivity {
    TextView tvTenHang, tvCheck;
    SearchView searchPro;
    RecyclerView recyProCat;
    ArrayList<Product> lst;
    ProductAdapter proAdapter;
    ImageView imgBack;
    Toolbar toolbar;
    String tenhang;
    Account account;
    ProductHandler productHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cat);
        getIt();
        addcontrols();
        addevents();
    }
    void addcontrols()
    {
        tvTenHang = findViewById(R.id.tvTenHang);
        searchPro = findViewById(R.id.searchPro);
        recyProCat = findViewById(R.id.recyProCat);
        imgBack = findViewById(R.id.imgBack);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (account != null)
        {
            proAdapter = new ProductAdapter(lst, DetailCatActivity.this, account);
        }
        else
        {
            proAdapter = new ProductAdapter(lst, DetailCatActivity.this, account);
        }

        recyProCat.setLayoutManager(new GridLayoutManager(this, 2));
        recyProCat.setItemAnimator(new DefaultItemAnimator());

        recyProCat.setAdapter(proAdapter);
        tvTenHang.setText(tvTenHang.getText() + tenhang);
        productHandler = new ProductHandler(DetailCatActivity.this, "LaptopShop.db", null, 1);
    }
    void addevents()
    {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchPro.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (account != null)
                {
                    ArrayList<Product> filter = new ArrayList<>();
                    for (Product pro : lst)
                    {
                        if (pro.getTensp().toLowerCase().contains(newText.toLowerCase()))
                        {
                            filter.add(pro);
                        }
                    }

                    proAdapter = new ProductAdapter(filter, DetailCatActivity.this, account);
                    recyProCat.setAdapter(proAdapter);
                    return false;
                }
                else
                {
                    ArrayList<Product> filter = new ArrayList<>();
                    for (Product pro : lst)
                    {
                        if (pro.getTensp().toLowerCase().contains(newText.toLowerCase()))
                        {
                            filter.add(pro);
                        }
                    }

                    proAdapter = new ProductAdapter(filter, DetailCatActivity.this, null);
                    recyProCat.setAdapter(proAdapter);
                    return false;
                }
            }
        });
    }
    void getIt()
    {
        tvCheck = findViewById(R.id.tvCheck);
        Intent intent = getIntent();
        lst = (ArrayList<Product>) intent.getSerializableExtra("lstProCat");
        if (lst != null && !lst.isEmpty()) {
            tvCheck.setVisibility(View.GONE);
        } else {
            tvCheck.setVisibility(View.VISIBLE);
            tvCheck.setText("Không có sản phẩm nào");
        }

        tenhang = intent.getStringExtra("tenhang");
        account = (Account) intent.getSerializableExtra("Account");
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater =getMenuInflater();
        menuInflater.inflate(R.menu.sort_filter, menu);

        if (menu instanceof MenuBuilder)
        {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        ArrayList<Product> lstChange = new ArrayList<>(lst);
        if (account != null)
        {
            if (id == R.id.iDefault)
            {
                item.setChecked(false);
                proAdapter = new ProductAdapter(lst, DetailCatActivity.this, account);
                recyProCat.setAdapter(proAdapter);
            }
            if (id == R.id.ibanChay)
            {
                HashMap<Integer, Integer> soLuongDaBanMap = new HashMap<>();
                for (Product product : lstChange) {
                    int soLuongDaBan = productHandler.getSoLuongDaBanProduct(product.getMasp());
                    soLuongDaBanMap.put(product.getMasp(), soLuongDaBan);
                }
                Collections.sort(lstChange, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        int soLuongDaBan1 = soLuongDaBanMap.get(p1.getMasp());
                        int soLuongDaBan2 = soLuongDaBanMap.get(p2.getMasp());
                        return Integer.compare(soLuongDaBan2, soLuongDaBan1);
                    }
                });
                item.setChecked(false);
                proAdapter = new ProductAdapter(lstChange, DetailCatActivity.this, account);
                recyProCat.setAdapter(proAdapter);
            }
            else if (id == R.id.idesc)
            {
                Collections.sort(lstChange, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return Long.compare(p2.getGiaban(), p1.getGiaban());
                    }
                });
                item.setChecked(false);
                proAdapter = new ProductAdapter(lstChange, DetailCatActivity.this, account);
                recyProCat.setAdapter(proAdapter);
            }
            else if (id == R.id.iasc)
            {
                Collections.sort(lstChange, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return Long.compare(p1.getGiaban(), p2.getGiaban());
                    }
                });
                item.setChecked(false);
                proAdapter = new ProductAdapter(lstChange, DetailCatActivity.this, account);
                recyProCat.setAdapter(proAdapter);
            }
        }
        else
        {
            if (id == R.id.iDefault)
            {
                item.setChecked(false);
                proAdapter = new ProductAdapter(lst, DetailCatActivity.this, null);
                recyProCat.setAdapter(proAdapter);
            }
            if (id == R.id.ibanChay)
            {
                HashMap<Integer, Integer> soLuongDaBanMap = new HashMap<>();
                for (Product product : lstChange) {
                    int soLuongDaBan = productHandler.getSoLuongDaBanProduct(product.getMasp());
                    soLuongDaBanMap.put(product.getMasp(), soLuongDaBan);
                }
                Collections.sort(lstChange, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        int soLuongDaBan1 = soLuongDaBanMap.get(p1.getMasp());
                        int soLuongDaBan2 = soLuongDaBanMap.get(p2.getMasp());
                        return Integer.compare(soLuongDaBan2, soLuongDaBan1);
                    }
                });
                item.setChecked(false);
                proAdapter = new ProductAdapter(lstChange, DetailCatActivity.this, null);
                recyProCat.setAdapter(proAdapter);
            }
            else if (id == R.id.idesc)
            {
                Collections.sort(lstChange, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return Long.compare(p2.getGiaban(), p1.getGiaban());
                    }
                });
                item.setChecked(false);
                proAdapter = new ProductAdapter(lstChange, DetailCatActivity.this, null);
                recyProCat.setAdapter(proAdapter);
            }
            else if (id == R.id.iasc)
            {
                Collections.sort(lstChange, new Comparator<Product>() {
                    @Override
                    public int compare(Product p1, Product p2) {
                        return Long.compare(p1.getGiaban(), p2.getGiaban());
                    }
                });
                item.setChecked(false);
                proAdapter = new ProductAdapter(lstChange, DetailCatActivity.this, null);
                recyProCat.setAdapter(proAdapter);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}