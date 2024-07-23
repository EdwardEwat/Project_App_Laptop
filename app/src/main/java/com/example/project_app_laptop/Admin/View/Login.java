package com.example.project_app_laptop.Admin.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Login extends AppCompatActivity {
    Button btnLogin;
    EditText edtUser, edtPass;
    TextView btnForgotPass, btnSignUp;
    ImageView btnPassIcon;
    DBHelper helper;
    boolean passwordShow = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginadmin);

        addControl();
        addEvent();
    }

    void addControl() {
        edtUser = findViewById(R.id.edtUser);
        edtPass = findViewById(R.id.edtPass);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnPassIcon = findViewById(R.id.btnPassIcon);

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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pass;
                user = edtUser.getText().toString();
                pass = edtPass.getText().toString();
                if(user.equals("") || pass.equals("")) {
                    Toast.makeText(Login.this, "Tài khoản mật khẩu không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isLoggedId = helper.checkUser(edtUser.getText().toString(), edtPass.getText().toString());
                    if(isLoggedId) {
                        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username", edtUser.getText().toString());
                        editor.apply();
                        startActivity(new Intent(Login.this, Home.class));
                    } else {
                        Toast.makeText(Login.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}