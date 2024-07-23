package com.example.project_app_laptop.Admin.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.Model.Brand;
import com.example.project_app_laptop.R;
import com.squareup.picasso.Picasso;

public class UpdateBrand extends AppCompatActivity {
    private EditText edtTenHang_update;
    private ImageView imgLogo_update, btnBack;
    private Button btnHoanThanh;
    private DBHelper db;
    private Brand brandToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_brand);
        addControls();
        db = new DBHelper(this);

        Intent intent = getIntent();
        int brandIdToUpdate = intent.getIntExtra("BRAND_TO_UPDATE", -1);
        if (brandIdToUpdate == -1) {
            // Xử lý khi không có dữ liệu Brand cần sửa
            Toast.makeText(this, "Không có dữ liệu hãng cần sửa", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // Lấy thông tin của Brand từ cơ sở dữ liệu và hiển thị lên giao diện
            brandToUpdate = db.getBrandById(brandIdToUpdate);
            if (brandToUpdate != null) {
                edtTenHang_update.setText(brandToUpdate.getTenhang());
                // Lấy tên của hình ảnh từ cơ sở dữ liệu
                String logoName = brandToUpdate.getLogo();
                // Kiểm tra xem tên hình ảnh có tồn tại trong thư mục drawable không
                int logoResourceId = getResources().getIdentifier(logoName, "drawable", getPackageName());
                // Kiểm tra nếu hình ảnh tồn tại, thì sử dụng Picasso để hiển thị
                if (logoResourceId != 0) {
                    imgLogo_update.setImageResource(logoResourceId);
                } else {
                    // Xử lý khi không tìm thấy hình ảnh
                    Toast.makeText(this, "Không tìm thấy hình ảnh", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Xử lý khi không tìm thấy Brand cần sửa trong cơ sở dữ liệu
                Toast.makeText(this, "Không tìm thấy hãng cần sửa", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        addEvent();
    }

    void addControls() {
        edtTenHang_update = findViewById(R.id.edtTenHang_Update);
        imgLogo_update = findViewById(R.id.imgLogo_update);
        btnHoanThanh = findViewById(R.id.btnSuaHang);
        btnBack = findViewById(R.id.btnBack);
    }

    void addEvent() {
        btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin mới từ giao diện
                String tenHangMoi = edtTenHang_update.getText().toString().trim();

                // Cập nhật thông tin mới của Brand trong cơ sở dữ liệu
                if (!tenHangMoi.isEmpty()) {
                    brandToUpdate.setTenhang(tenHangMoi);
                    boolean isUpdated = db.updateBrand(brandToUpdate.getMahang(), tenHangMoi, brandToUpdate.getLogo());
                    if (isUpdated) {
                        // Xử lý khi cập nhật thành công
                        Toast.makeText(UpdateBrand.this, "Cập nhật thông tin hãng thành công", Toast.LENGTH_SHORT).show();
                        // Load lại danh sách sản phẩm
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        // Xử lý khi cập nhật thất bại
                        Toast.makeText(UpdateBrand.this, "Cập nhật thông tin hãng thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Xử lý khi tên hãng trống
                    Toast.makeText(UpdateBrand.this, "Vui lòng nhập tên hãng", Toast.LENGTH_SHORT).show();
                }
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
