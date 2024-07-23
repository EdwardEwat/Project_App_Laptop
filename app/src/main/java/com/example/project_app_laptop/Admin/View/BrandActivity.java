package com.example.project_app_laptop.Admin.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_app_laptop.Admin.BrandAdapter;
import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.Model.Brand;
import com.example.project_app_laptop.Admin.Model.Product;
import com.example.project_app_laptop.Admin.ProductAdapter;
import com.example.project_app_laptop.R;

import java.util.ArrayList;
import java.util.List;

public class BrandActivity extends AppCompatActivity {
    private ImageView btnBack, btnAddHang;
    private TextView tvSearch;
    private ListView lvHangSP;
    private BrandAdapter adapter;
    private ArrayList<Brand> brands;
    private DBHelper db;

    private static final int ADD_BRAND_REQUEST_CODE = 1;
    private static final int EDIT_BRAND_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_brand);

        addControl();

        db = new DBHelper(this);
        brands = db.getDataHang();

        adapter = new BrandAdapter(this, R.layout.layout_item_hang, brands);
        lvHangSP.setAdapter(adapter);

        addEvent();
    }

    public void addControl() {
        tvSearch = findViewById(R.id.tvSearch);
        lvHangSP = findViewById(R.id.lvHangSP);
        btnAddHang = findViewById(R.id.btnAddBrand);
        btnBack = findViewById(R.id.btnBack);
    }

    public void addEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BrandActivity.this, AddBrand.class));
                startActivityForResult(new Intent(BrandActivity.this, AddBrand.class), ADD_BRAND_REQUEST_CODE);
            }
        });

        tvSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchBrand(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lvHangSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Brand brand = brands.get(position);

                // Lấy danh sách sản phẩm của Hãng
                List<Product> products = db.getProductsByBrandId(brand.getMahang());

                // Tạo Intent và truyền dữ liệu
                Intent intent = new Intent(BrandActivity.this, BrandDetail.class);
                intent.putExtra("PRODUCT_LIST", (ArrayList<Product>) products);
                startActivity(intent);

            }
        });

        lvHangSP.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showChosseDialog(position);
                return true;
            }
        });
    }

    private void showChosseDialog(int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(BrandActivity.this);
        builder.setTitle("Chọn hành động")
                .setItems(new String[]{"Sửa", "Xóa"}, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            // Sửa hãng
                            Intent intent = new Intent(BrandActivity.this, UpdateBrand.class);
                            intent.putExtra("BRAND_TO_UPDATE", brands.get(position).getMahang());
                            startActivityForResult(intent, EDIT_BRAND_REQUEST_CODE);
                        } else {
                            // Xóa hãng
                            confirmDelete(position);
                        }
                    }
                });
        builder.create().show();
    }

    //Xóa Hãng
    private void confirmDelete(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BrandActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        TextView tvDialogTitle = dialogView.findViewById(R.id.tvDialogTitle);
        TextView tvDialogMessage = dialogView.findViewById(R.id.tvDialogMessage);
        TextView btnYes = dialogView.findViewById(R.id.btnYes);
        TextView btnNo = dialogView.findViewById(R.id.btnNo);

        tvDialogTitle.setText("Xác nhận xóa hãng");
        tvDialogMessage.setText("Bạn có chắc chắn muốn xóa hãng này?");

        AlertDialog dialog = builder.create();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy ID của hãng từ danh sách
                int brandId = brands.get(position).getMahang();
                boolean isDeleted = db.deleteBrand(brandId);
                if (isDeleted) {
                    brands.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(BrandActivity.this, "Xóa hãng thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(BrandActivity.this, "Xóa hãng thất bại", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    // Thêm một phương thức mới để cập nhật danh sách hãng
    private void updateBrandList() {
        brands.clear(); // Xóa danh sách hiện tại
        brands.addAll(db.getDataHang()); // Lấy danh sách hãng mới từ cơ sở dữ liệu
        adapter.notifyDataSetChanged(); // Cập nhật adapter
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_BRAND_REQUEST_CODE || requestCode == EDIT_BRAND_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Thực hiện tải lại danh sách hãng ở đây
                updateBrandList();
            }
        }
    }

    private void searchBrand(String ten)
    {
        String key = tvSearch.getText().toString().trim();
        if(!key.isEmpty())
        {
            ArrayList<Brand> filtered = filterBrandByName(key);
            if(filtered.isEmpty())
            {
                //Thông báo không có sản phẩm phù hợp
                Toast.makeText(this, "Không Tìm Thấy Sản Phẩm!", Toast.LENGTH_SHORT).show();
            }
            else {
                //Cập nhập ListView với danh sách sẳn phẩm
                adapter = new BrandAdapter(BrandActivity.this,R.layout.layout_item_hang, filtered);
                lvHangSP.setAdapter(adapter);
            }
        }
        else {
            adapter = new BrandAdapter(BrandActivity.this,R.layout.layout_item_hang, brands);
            lvHangSP.setAdapter(adapter);
        }
    }

    private ArrayList<Brand> filterBrandByName(String key)
    {
        ArrayList<Brand> filteredBrand = new ArrayList<>();
        for(Brand brand : brands)
        {
            if(brand.getTenhang().toLowerCase().contains(key.toLowerCase()))
            {
                filteredBrand.add(brand);
            }
        }
        return filteredBrand;
    }
}