package com.example.project_app_laptop.Admin.View;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.Model.Brand;
import com.example.project_app_laptop.R;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {
    private ImageView btnBack, imgAvt;
    private EditText edtTenSP, edtGiaBan, edtCPU, edtRAM, edtOCung, edtManHinh, edtVGA, edtHeDieuHanh, edtTrongLuong, edtPin, edtSoLuong, edtMota;
    private Button btnThemSP;
    private Spinner spinnerHang;
    private DBHelper dbHelper;
    private ArrayList<Brand> brands;
    private ArrayAdapter<String> spinnerAdapter;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String selectedImagePath; // Đường dẫn của hình ảnh được chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_product);

        dbHelper = new DBHelper(this);
        addControl();
        addEvent();
        loadHang();
    }

    void addControl() {
        edtTenSP = findViewById(R.id.edtTenSP);
        edtGiaBan = findViewById(R.id.edtGiaBanSP);
        edtCPU = findViewById(R.id.edtCPU);
        edtOCung = findViewById(R.id.edtOCung);
        edtRAM = findViewById(R.id.edtRAM);
        edtManHinh = findViewById(R.id.edtManHinh);
        edtVGA = findViewById(R.id.edtVGA);
        edtHeDieuHanh = findViewById(R.id.edtHeDieuHanh);
        edtTrongLuong = findViewById(R.id.edtTrongLuong);
        edtPin = findViewById(R.id.edtPin);
        edtSoLuong = findViewById(R.id.edtSoLuongSP);
        edtMota = findViewById(R.id.edtMoTaSP);
        btnThemSP = findViewById(R.id.btnThemSP);
        btnBack = findViewById(R.id.btnBack);
        imgAvt = findViewById(R.id.imgAvt);
        spinnerHang = findViewById(R.id.spinnerHang);
    }

    void addEvent() {
        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenSP = edtTenSP.getText().toString().trim();
                String giaBanStr = edtGiaBan.getText().toString().trim();
                String cpu = edtCPU.getText().toString().trim();
                String ram = edtRAM.getText().toString().trim();
                String ocung = edtOCung.getText().toString().trim();
                String manhinh = edtManHinh.getText().toString().trim();
                String vga = edtVGA.getText().toString().trim();
                String heDieuHanh = edtHeDieuHanh.getText().toString().trim();
                String trongLuong = edtTrongLuong.getText().toString().trim();
                String pin = edtPin.getText().toString().trim();
                String soluong = edtSoLuong.getText().toString().trim();
                String mota = edtMota.getText().toString().trim();

                if (tenSP.isEmpty() || giaBanStr.isEmpty() || soluong.isEmpty()) {
                    Toast.makeText(AddProduct.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int giaBan = Integer.parseInt(giaBanStr);

                // Get the selected brand's ID from the spinner
                int selectedBrandIndex = spinnerHang.getSelectedItemPosition();
                int selectedBrandId = brands.get(selectedBrandIndex).getMahang();

                // Insert data into the database, including the image name
                boolean isInserted = dbHelper.insertDataSanPham(tenSP, mota, "hinhlaptopmicrosoft", giaBan, cpu, ram, ocung, manhinh, vga, heDieuHanh, trongLuong, pin, soluong, selectedBrandId);


                if (isInserted) {
                    Toast.makeText(AddProduct.this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddProduct.this, "Thêm sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
    }

    void loadHang() {
        brands = dbHelper.getDataHang();

        ArrayList<String> brandNames = new ArrayList<>();
        for (Brand brand : brands) {
            brandNames.add(brand.getTenhang());
        }

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, brandNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHang.setAdapter(spinnerAdapter);
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn hình ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            String imagePath = null;
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImageUri, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                cursor.close();
            }

            if (imagePath != null) {
                // Save the selected image to internal storage
                String imageName = "hinh" + System.currentTimeMillis();
                File destination = new File(getFilesDir(), imageName);
                try (InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                     OutputStream outputStream = new FileOutputStream(destination)) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    selectedImagePath = imageName;
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Display the selected image
                Glide.with(this).load(imagePath).into(imgAvt);
            } else {
                Toast.makeText(this, "Không thể lấy đường dẫn của hình ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
