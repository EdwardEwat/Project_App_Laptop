package com.example.project_app_laptop.Admin.View;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.R;

public class Register extends AppCompatActivity {
    Button btnSignUp;
    EditText edtUser, edtPass, edtconPass;
    TextView btnLogin;
    ImageView btnPassIcon, btnconPassIcon;
    boolean passwordShow = false;
    DBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registeradmin);

        addControl();
        addEvent();
    }

    void addControl() {
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        edtconPass = findViewById(R.id.edtconPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnPassIcon = findViewById(R.id.btnPassIcon);
        btnconPassIcon = findViewById(R.id.btnconPassIcon);

        helper = new DBHelper(this);
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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pass, conpass;
                user = edtUser.getText().toString();
                pass = edtPass.getText().toString();
                conpass = edtconPass.getText().toString();
                if(user.equals("") || pass.equals("") || conpass.equals("")) {
                    Toast.makeText(Register.this, "Thông tin không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    if(pass.equals(conpass)) {
                        if(helper.checkUsername(user)) {
                            Toast.makeText(Register.this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        boolean registeredSuccess = helper.insertDataRegister(user, pass);
                        if(registeredSuccess) {
                            startActivity(new Intent(Register.this, Login.class));
                            Toast.makeText(Register.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(Register.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }
}