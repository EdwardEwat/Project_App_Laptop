package com.example.project_app_laptop.Admin.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.R;

public class ChangePassword extends AppCompatActivity {
    EditText edtPass, edtconPass;
    ImageView btnPassIcon, btnconPassIcon, btnBack;
    Button btnChangePass;
    boolean passwordShow = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);

        addControl();
        addEvent();
    }

    void addControl() {
        btnPassIcon = findViewById(R.id.btnPassIcon);
        btnconPassIcon = findViewById(R.id.btnconPassIcon);
        btnBack = findViewById(R.id.btnBack);
        edtPass = findViewById(R.id.edtPass);
        edtconPass = findViewById(R.id.edtconPass);
        btnChangePass = findViewById(R.id.btnChangePass);
    }
    void addEvent() {
        btnPassIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordShow) {
                    passwordShow = false;
                    edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnPassIcon.setImageResource(R.drawable.password_hide);
                } else {
                    passwordShow = true;
                    edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnPassIcon.setImageResource(R.drawable.password_show);
                }
                edtPass.setSelection(edtPass.length());
            }
        });

        btnconPassIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordShow) {
                    passwordShow = false;
                    edtconPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnconPassIcon.setImageResource(R.drawable.password_hide);
                } else {
                    passwordShow = true;
                    edtconPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnconPassIcon.setImageResource(R.drawable.password_show);
                }
                edtPass.setSelection(edtPass.length());
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword() {
        String newPass = edtPass.getText().toString();
        String conPass = edtconPass.getText().toString();

        if (newPass.isEmpty() || conPass.isEmpty()) {
            Toast.makeText(ChangePassword.this, "Mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!newPass.equals(conPass)) {
            Toast.makeText(ChangePassword.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");

        DBHelper dbHelper = new DBHelper(ChangePassword.this);
        boolean isUpdated = dbHelper.updatePassword(username, newPass);

        if (isUpdated) {
            Toast.makeText(ChangePassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(ChangePassword.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
        }
    }
}