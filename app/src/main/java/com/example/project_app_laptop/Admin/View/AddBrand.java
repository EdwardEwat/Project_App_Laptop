package com.example.project_app_laptop.Admin.View;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddBrand extends AppCompatActivity {
    private ImageView btnBack, imgLogo_add;
    private EditText edtTenHang;
    private Button btnThemHang;
    private DBHelper dbHelper;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private String selectedImagePath; // Đường dẫn của hình ảnh được chọn
    private String currentPhotoPath; // Đường dẫn của hình ảnh được chụp từ camera
    private String defaultImageName = "applelogo"; // Tên của hình ảnh mặc định trong thư mục drawable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_brand);

        dbHelper = new DBHelper(this);
        addControl();
        addEvent();
        checkPermissions();
    }

    public void addControl() {
        edtTenHang = findViewById(R.id.edtTenHang);
        btnBack = findViewById(R.id.btnBack);
        btnThemHang = findViewById(R.id.btnThemHang);
        imgLogo_add = findViewById(R.id.imgLogo_add); // Thêm điều khiển cho ImageView
    }

    public void addEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgLogo_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage(); // Gọi phương thức chọn ảnh
            }
        });

        btnThemHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenHang = edtTenHang.getText().toString().trim();

                if (tenHang.isEmpty()) {
                    Toast.makeText(AddBrand.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Nếu không có hình ảnh được chọn, sử dụng hình ảnh mặc định
                String imageName = (selectedImagePath == null) ? defaultImageName : "brand_image_" + System.currentTimeMillis();

                if (selectedImagePath != null) {
                    copyImageToInternalStorage(selectedImagePath, imageName);
                }

                // Thêm hãng vào cơ sở dữ liệu
                addBrandToDatabase(tenHang, imageName);
            }
        });
    }

    private void chooseImage() {
        // Tạo một Intent mới có hành động là lựa chọn hình ảnh từ album hoặc camera
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        // Thêm các lựa chọn để có thể chụp hình từ camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent chooserIntent = Intent.createChooser(intent, "Chọn hình ảnh");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{cameraIntent});

        // Khởi chạy Intent để chọn hình ảnh và chụp từ camera
        startActivityForResult(chooserIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Hiển thị hình ảnh đã chọn
                Glide.with(this).load(selectedImageUri).into(imgLogo_add);
                // Lưu đường dẫn của hình ảnh đã chọn
                selectedImagePath = getRealPathFromURI(selectedImageUri);
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            if (imageBitmap != null) {
                // Hiển thị hình ảnh đã chụp từ camera
                imgLogo_add.setImageBitmap(imageBitmap);
                // Lưu đường dẫn của hình ảnh đã chụp từ camera
                selectedImagePath = saveBitmapToInternalStorage(imageBitmap);
            }
        }
    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        return null;
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(sourceFile);
            outputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private void copyImageToInternalStorage(String selectedImagePath, String imageName) {
        if (selectedImagePath == null) {
            return;
        }

        try {
            File internalDir = getDir("images", Context.MODE_PRIVATE);
            if (!internalDir.exists()) {
                internalDir.mkdirs();
            }

            File sourceFile = new File(selectedImagePath);
            File destination = new File(internalDir, imageName);

            copyFile(sourceFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String saveBitmapToInternalStorage(Bitmap bitmap) {
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File file = wrapper.getDir("images", Context.MODE_PRIVATE);
        file = new File(file, "captured_image_" + System.currentTimeMillis() + ".jpg");

        try {
            OutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    private void addBrandToDatabase(String tenHang, String imageName) {
        // Thêm hãng vào cơ sở dữ liệu
        boolean isInserted = dbHelper.insertDataHang(tenHang, imageName);

        if (isInserted) {
            Toast.makeText(AddBrand.this, "Thêm hãng thành công!", Toast.LENGTH_SHORT).show();
            // Gửi kết quả trở lại BrandActivity và kết thúc AddBrandActivity
            setResult(RESULT_OK);
            finish();
        } else {
            Toast.makeText(AddBrand.this, "Thêm hãng thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            }, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
