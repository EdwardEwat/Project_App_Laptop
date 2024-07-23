package com.example.project_app_laptop.Admin.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.Admin.BrandAdapter;
import com.example.project_app_laptop.Admin.Model.Brand;
import com.example.project_app_laptop.Admin.ProductAdapter;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.Model.Product;
import com.example.project_app_laptop.R;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {
    private ImageView btnBack, btnAddSP;
    private TextView tvSearch;
    private ListView lvDSSP;
    private ProductAdapter productAdapter;
    private ArrayList<Product> productList;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        addControl();

        dbHelper = new DBHelper(this);
        productList = dbHelper.getDataSanPham();

        productAdapter = new ProductAdapter(this, R.layout.layout_item_product, productList);
        lvDSSP.setAdapter(productAdapter);

        addEvent();
    }

    void addControl() {
        tvSearch = findViewById(R.id.tvSearch);
        btnBack = findViewById(R.id.btnBack);
        btnAddSP = findViewById(R.id.btnAddSP);
        lvDSSP = findViewById(R.id.lvDSSP);
    }

    void addEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchProduct(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnAddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductActivity.this, AddProduct.class));
            }
        });

        lvDSSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = productList.get(position);

                Intent intent = new Intent(ProductActivity.this, ProductDetail.class);

                intent.putExtra("MASANPHAM", selectedProduct.getMasp());

                startActivity(intent);
            }
        });
    }

    private void searchProduct(String query) {
        String key = query.trim();
        if (!key.isEmpty()) {
            ArrayList<Product> filtered = filterProductByName(key);
            if (filtered.isEmpty()) {
                Toast.makeText(this, "Không Tìm Thấy Sản Phẩm!", Toast.LENGTH_SHORT).show();
            }
            productAdapter = new ProductAdapter(ProductActivity.this, R.layout.layout_item_product, filtered);
        } else {
            productAdapter = new ProductAdapter(ProductActivity.this, R.layout.layout_item_product, productList);
        }
        lvDSSP.setAdapter(productAdapter);
    }

    private ArrayList<Product> filterProductByName(String key)
    {
        ArrayList<Product> filteredProduct = new ArrayList<>();
        for(Product product : productList)
        {
            if(product.getTensp().toLowerCase().contains(key.toLowerCase()))
            {
                filteredProduct.add(product);
            }
        }
        return filteredProduct;
    }
}
