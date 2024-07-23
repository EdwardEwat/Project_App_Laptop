package com.example.project_app_laptop.Admin.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.Model.Brand;
import com.example.project_app_laptop.Admin.Model.Product;
import com.example.project_app_laptop.R;

import java.util.ArrayList;

public class ProductDetail extends AppCompatActivity {
    private ImageView btnBack, imgHinhSP;
    private EditText edtTenSP, edtGiaBan, edtCPU, edtRAM, edtOCung, edtManHinh, edtVGA, edtHeDieuHanh, edtTrongLuong, edtPin, edtSoLuong, edtMota;
    private Button btnCapNhatSP, btnXoaSP;
    private int productId;
    private Spinner spinnerHang;
    private DBHelper dbHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_detail);

        dbHelper = new DBHelper(this);
        addControl();
        addEvent();
    }

    void addControl() {
        btnBack = findViewById(R.id.btnBack);
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
        btnCapNhatSP = findViewById(R.id.btnCapNhatSP);
        btnXoaSP = findViewById(R.id.btnXoaSP);
        imgHinhSP = findViewById(R.id.imgHinhSP);
        spinnerHang = findViewById(R.id.spinnerHang);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("MASANPHAM")) {
            productId = intent.getIntExtra("MASANPHAM", -1);
            if (productId != -1) {
                Product selectedProduct = dbHelper.getProductById(productId);

                if (selectedProduct != null) {
                    edtTenSP.setText(selectedProduct.getTensp());
                    edtGiaBan.setText(String.valueOf(selectedProduct.getGiaban()));
                    edtCPU.setText(selectedProduct.getCpu());
                    edtRAM.setText(selectedProduct.getRam());
                    edtOCung.setText(selectedProduct.getOcung());
                    edtManHinh.setText(selectedProduct.getManhinh());
                    edtVGA.setText(selectedProduct.getVga());
                    edtHeDieuHanh.setText(selectedProduct.getHedieuhanh());
                    edtTrongLuong.setText(selectedProduct.getTrongluong());
                    edtPin.setText(selectedProduct.getPin());
                    edtSoLuong.setText(selectedProduct.getSoluong());
                    edtMota.setText(selectedProduct.getMota());

                    int resourceId = getResources().getIdentifier(selectedProduct.getHinhanh(), "drawable", getPackageName());
                    if (resourceId != 0) {
                        imgHinhSP.setImageResource(resourceId);
                    } else {
                        imgHinhSP.setImageResource(R.drawable.logodell);
                    }

                    // Load brands into spinner
                    loadBrandsAndSetSelected(selectedProduct.getMahang());
                } else {
                    Toast.makeText(this, "Sản phẩm không có", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    void addEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnCapNhatSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });

        btnXoaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProduct();
            }
        });

    }


    // Xóa san phẩm
    private void deleteProduct() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetail.this);
        builder.setTitle("Xác nhận xóa sản phẩm");
        builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isDeleted = dbHelper.deleteProduct(productId);
                if (isDeleted) {
                    Toast.makeText(ProductDetail.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ProductDetail.this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    // Cập nhật sản phẩm
    private void updateProduct() {
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
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        int giaBan = Integer.parseInt(giaBanStr);

        // Lấy thông tin về hãng từ Spinner
        String selectedBrandName = spinnerHang.getSelectedItem().toString();

        // Tìm kiếm đối tượng Brand tương ứng
        int selectedBrandId = findBrandIdByName(selectedBrandName);

        Product product = new Product(productId, tenSP, mota, null, giaBan, cpu, ram, ocung, manhinh, vga, heDieuHanh, trongLuong, pin, soluong, selectedBrandId);

        boolean isUpdated = dbHelper.updateProduct(product);
        if (isUpdated) {
            Toast.makeText(this, "Cập nhật sản phẩm thành công!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cập nhật sản phẩm thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    // Tìm kiếm ID của hãng dựa trên tên
    private int findBrandIdByName(String name) {
        ArrayList<Brand> brands = dbHelper.getDataHang();
        for (Brand brand : brands) {
            if (brand.getTenhang().equals(name)) {
                return brand.getMahang();
            }
        }
        return -1; // Không tìm thấy
    }


    void loadBrandsAndSetSelected(int selectedBrandId) {
        ArrayList<Brand> brands = dbHelper.getDataHang();
        ArrayList<String> brandNames = new ArrayList<>();

        int selectedIndex = -1;

        for (int i = 0; i < brands.size(); i++) {
            Brand brand = brands.get(i);
            brandNames.add(brand.getTenhang());
            if (brand.getMahang() == selectedBrandId) {
                selectedIndex = i;
            }
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, brandNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHang.setAdapter(spinnerAdapter);

        if (selectedIndex != -1) {
            spinnerHang.setSelection(selectedIndex);
        }
    }
}
