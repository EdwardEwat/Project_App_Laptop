package com.example.project_app_laptop.Admin.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.DetailBrandAdapter;
import com.example.project_app_laptop.Admin.Model.Product;
import com.example.project_app_laptop.R;
import com.example.project_app_laptop.Admin.View.ProductDetail;

import java.util.ArrayList;
import java.util.List;

public class BrandDetail extends AppCompatActivity {

    private ListView lvDSSPBrand;
    private DetailBrandAdapter detailBrandAdapter;
    private List<Product> productList;
    private ImageView btnBackBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_detail);

        //Nhận danh sách sản phẩm từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("PRODUCT_LIST")) {
            productList = (List<Product>) intent.getSerializableExtra("PRODUCT_LIST");
        } else {
            productList = new ArrayList<>(); // Khởi tạo danh sách rỗng nếu không có dữ liệu
        }

        // Thiết lập các thành phần giao diện
        lvDSSPBrand = findViewById(R.id.lvDSSPBrand);
        btnBackBrand = findViewById(R.id.btnBackBrand);

        // Thiết lập adapter cho ListView
        detailBrandAdapter = new DetailBrandAdapter(this, productList);
        lvDSSPBrand.setAdapter(detailBrandAdapter);

        //Show DetailProduct khi onCLick vào ListView
        lvDSSPBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product selectedProduct = productList.get(position); // Lấy sản phẩm được chọn từ danh sách
                int productId = selectedProduct.getMasp(); // Lấy mã sản phẩm của sản phẩm được chọn

                Intent intent = new Intent(getApplicationContext(), ProductDetail.class);
                intent.putExtra("MASANPHAM", productId); // Truyền mã sản phẩm qua Intent
                startActivity(intent);

                Toast.makeText(BrandDetail.this, "Open Detail Product", Toast.LENGTH_SHORT).show();
            }
        });

        btnBackBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void displayProductDetail(int productId) {
        DBHelper dbHelper = new DBHelper(this);
        Product product = dbHelper.getProductById(productId);
        if (product != null) {
            // Hiển thị thông tin chi tiết của sản phẩm
            // Ví dụ: Hiển thị tên sản phẩm, giá bán, mô tả, hình ảnh, vv.
        } else {
            // Xử lý trường hợp không tìm thấy sản phẩm
            Toast.makeText(this, "Không tìm thấy thông tin sản phẩm", Toast.LENGTH_SHORT).show();
        }
    }

}
