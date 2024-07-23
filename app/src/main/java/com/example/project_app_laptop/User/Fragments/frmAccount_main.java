package com.example.project_app_laptop.User.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project_app_laptop.User.Activity.LoginActivity;
import com.example.project_app_laptop.User.Activity.MainActivity;
import com.example.project_app_laptop.User.Controller.AccountHandler;
import com.example.project_app_laptop.User.Controller.GioHangHandler;
import com.example.project_app_laptop.User.Controller.HoaDonHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.R;

import java.util.ArrayList;

public class frmAccount_main extends Fragment {
    LinearLayout linearlogin, lineardoihoten, lineardoimatkhau, linearxoataikhoan, linearlogout;
    TextView tvHoTenKH;
    Account account;
    AccountHandler accountHandler;
    GioHangHandler gioHangHandler;
    HoaDonHandler hoaDonHandler;
    public frmAccount_main() {

    }

    public static frmAccount_main newInstance() {
        frmAccount_main fragment = new frmAccount_main();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frm_account_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountHandler = new AccountHandler(view.getContext(), "LaptopShop.db", null, 1);
        gioHangHandler = new GioHangHandler(view.getContext(), "LaptopShop.db", null, 1);
        hoaDonHandler = new HoaDonHandler(view.getContext(), "LaptopShop.db", null, 1);

        linearlogin = view.findViewById(R.id.linear_login);
        lineardoihoten = view.findViewById(R.id.linear_doihoten);
        lineardoimatkhau = view.findViewById(R.id.linear_doimatkhau);
        linearxoataikhoan = view.findViewById(R.id.linear_xoataikhoan);
        linearlogout = view.findViewById(R.id.linear_logout);
        tvHoTenKH = view.findViewById(R.id.tvHoTenKH);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("Account")) {
            account = (Account) bundle.getSerializable("Account");
            tvHoTenKH.setText(account.getTenkh());
            tvHoTenKH.setVisibility(View.VISIBLE);
            linearlogin.setVisibility(View.GONE);
            lineardoihoten.setVisibility(View.VISIBLE);
            lineardoimatkhau.setVisibility(View.VISIBLE);
            linearxoataikhoan.setVisibility(View.VISIBLE);
            linearlogout.setVisibility(View.VISIBLE);
        }
        else
        {
            tvHoTenKH.setText("");
            tvHoTenKH.setVisibility(View.GONE);
            linearlogin.setVisibility(View.VISIBLE);
            lineardoihoten.setVisibility(View.GONE);
            lineardoimatkhau.setVisibility(View.GONE);
            linearxoataikhoan.setVisibility(View.GONE);
            linearlogout.setVisibility(View.GONE);
        }

        linearlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myit = new Intent(view.getContext(), LoginActivity.class);
                startActivity(myit);
            }
        });
        linearlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        lineardoihoten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPopDoiHoTen();
            }
        });
        lineardoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPopDoiMatKhau();
            }
        });
        linearxoataikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenPopXoaTK();
            }
        });
    }
    void OpenPopDoiHoTen()
    {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_doihoten);

        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowattri = window.getAttributes();
        windowattri.gravity = Gravity.CENTER;
        window.setAttributes(windowattri);

        TextView btnHuyPop, btnCapNhatPop, tvLoi;
        EditText edtTenKH;

        btnHuyPop = dialog.findViewById(R.id.btnHuyPop);
        btnCapNhatPop = dialog.findViewById(R.id.btnCapNhatPop);
        tvLoi = dialog.findViewById(R.id.tvLoi);
        edtTenKH = dialog.findViewById(R.id.edtTenKH);

        btnHuyPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnCapNhatPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtTenKH.getText().toString().isEmpty())
                {
                    accountHandler.updateHoTenKH(edtTenKH.getText().toString(), account.getMataikhoan());
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    tvLoi.setText("Vui lòng điền vào mục này.");
                    tvLoi.setVisibility(View.VISIBLE);
                }
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    void OpenPopDoiMatKhau()
    {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_doimatkhau);

        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowattri = window.getAttributes();
        windowattri.gravity = Gravity.CENTER;
        window.setAttributes(windowattri);

        TextView btnHuyPop, btnCapNhatPop, tvLoi;
        EditText edtMatKhau;

        btnHuyPop = dialog.findViewById(R.id.btnHuyPop);
        btnCapNhatPop = dialog.findViewById(R.id.btnCapNhatPop);
        tvLoi = dialog.findViewById(R.id.tvLoi);
        edtMatKhau = dialog.findViewById(R.id.edtMatKhau);

        btnHuyPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnCapNhatPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtMatKhau.getText().toString().isEmpty())
                {
                    accountHandler.updateMatKhau(edtMatKhau.getText().toString(), account.getMataikhoan());
                    Intent intent = new Intent(v.getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    tvLoi.setText("Vui lòng điền vào mục này.");
                    tvLoi.setVisibility(View.VISIBLE);
                }
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    void OpenPopXoaTK()
    {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_xoataikhoan);

        Window window = dialog.getWindow();
        if (window == null)
        {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowattri = window.getAttributes();
        windowattri.gravity = Gravity.CENTER;
        window.setAttributes(windowattri);

        TextView btnHuyPopTK, btnXoaPopTK;

        btnHuyPopTK = dialog.findViewById(R.id.btnHuyPopTK);
        btnXoaPopTK = dialog.findViewById(R.id.btnXoaPopTK);

        btnHuyPopTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnXoaPopTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int magiohang = gioHangHandler.getMaGioHang(account.getMataikhoan());
                gioHangHandler.deleteAllCTGH(magiohang);
                ArrayList<Integer> lstMaHD = hoaDonHandler.getAllMaHD(account.getMataikhoan());
                hoaDonHandler.deleteAllHoaDon(lstMaHD);
                accountHandler.deleteTaiKhoan(account.getMataikhoan());
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}