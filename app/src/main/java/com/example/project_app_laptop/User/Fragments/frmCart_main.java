package com.example.project_app_laptop.User.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project_app_laptop.User.Activity.CartActivity;
import com.example.project_app_laptop.User.Controller.CTGHHandler;
import com.example.project_app_laptop.User.Controller.CTHDHandler;
import com.example.project_app_laptop.User.Controller.GioHangHandler;
import com.example.project_app_laptop.User.Controller.HoaDonHandler;
import com.example.project_app_laptop.User.Controller.ProductHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.ChiTiet;
import com.example.project_app_laptop.User.Models.Product;
import com.example.project_app_laptop.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class frmCart_main extends Fragment{
    CheckBox chkSelect;
    TextView tvDelete, tvTongSP;
    Button btnDatHang;
    CartActivity cartActivity;
    ArrayList<ChiTiet> lstCT;
    CTGHHandler ctghHandler;
    GioHangHandler gioHangHandler;
    ProductHandler productHandler;
    HoaDonHandler hoaDonHandler;
    CTHDHandler cthdHandler;
    Account account;
    int countCheck = -1;

    public frmCart_main() {

    }

    public static frmCart_main newInstance() {
        frmCart_main fragment = new frmCart_main();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frm_cart_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ctghHandler = new CTGHHandler(view.getContext(), "LaptopShop.db", null, 1);
        gioHangHandler = new GioHangHandler(view.getContext(), "LaptopShop.db", null, 1);
        productHandler = new ProductHandler(view.getContext(), "LaptopShop.db", null, 1);
        hoaDonHandler = new HoaDonHandler(view.getContext(), "LaptopShop.db", null, 1);
        cthdHandler = new CTHDHandler(view.getContext(), "LaptopShop.db", null, 1);

        chkSelect = view.findViewById(R.id.chkSelect);
        tvDelete = view.findViewById(R.id.tvDelete);
        tvTongSP = view.findViewById(R.id.tvTongSP);
        btnDatHang = view.findViewById(R.id.btnDatHang);

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("Account")) {
            account = (Account) bundle.getSerializable("Account");

        }

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                if (lstCT != null)
                {
                    ArrayList<ChiTiet> temp = ctghHandler.loadCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()));
                    for (ChiTiet ct : lstCT)
                    {
                        for (ChiTiet t : temp)
                        {
                            if (t.getHinhanh().equals(ct.getHinhanh()))
                            {
                                t.setChecked(ct.isChecked());
                            }
                        }
                    }

                    for (ChiTiet t : temp)
                    {
                        if (t.isChecked() == true)
                        {
                            count++;
                        }
                    }
                    if (count != 0)
                    {
                        for (ChiTiet ct : temp)
                        {
                            if (ct.isChecked() == true)
                            {
                                Product pro = productHandler.getSP(ct.getMasp());
                                if (pro.getSoluong() < ct.getSoluong())
                                {
                                    Toast.makeText(cartActivity, "Kiểm tra lại số lượng của sản phẩm " + ct.getTensp() + " đã vượt quá số lượng hiện có", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        }
                        OpenPop();
                    }
                    else
                    {
                        OpenPopChuaChonSP("Bạn vẫn chưa chọn sản phẩm nào để mua.");
                    }
                }
                else
                {
                    int dem = 0;
                    ArrayList<ChiTiet> lstct;
                    lstct = cartActivity.getLstCT();
                    ArrayList<ChiTiet> temp = ctghHandler.loadCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()));
                    for (ChiTiet ct : lstct)
                    {
                        for (ChiTiet t : temp)
                        {
                            if (t.getHinhanh().equals(ct.getHinhanh()))
                            {
                                t.setChecked(ct.isChecked());
                            }
                        }
                    }

                    for (ChiTiet t : temp)
                    {
                        if (t.isChecked() == true)
                        {
                            dem++;
                        }
                    }
                    if (dem != 0)
                    {
                        for (ChiTiet ct : temp)
                        {
                            if (ct.isChecked() == true)
                            {
                                Product pro = productHandler.getSP(ct.getMasp());
                                if (pro.getSoluong() < ct.getSoluong())
                                {
                                    OpenPopChuaChonSP("Kiểm tra lại số lượng của sản phẩm " + ct.getTensp() + " đã vượt quá số lượng hiện có");
                                    return;
                                }
                            }
                        }
                        OpenPop();
                    }
                    else
                    {
                        OpenPopChuaChonSP("Bạn vẫn chưa chọn sản phẩm nào để mua.");
                    }
                }
            }
        });

        chkSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (lstCT != null)
                {
                    if (countCheck == -1)
                    {
                        cartActivity.updateAllCheckStatus(isChecked);
                        if (isChecked == true)
                        {
                            for (ChiTiet ct : lstCT)
                            {
                                ct.setChecked(isChecked);
                            }
                            tvTongSP.setText("Tổng (" + lstCT.size() + " Sản phẩm)");
                        }
                        else
                        {
                            for (ChiTiet ct : lstCT)
                            {
                                ct.setChecked(isChecked);
                            }
                            tvTongSP.setText("Tổng (" + 0 + " Sản phẩm)");
                        }
                    }
                    else
                    {
                        ArrayList<ChiTiet> lstct;
                        if (isChecked == true)
                        {
                            cartActivity.updateAllCheckStatus(isChecked);
                            lstct = cartActivity.getLstCT();
                            tvTongSP.setText("Tổng (" + lstct.size() + " Sản phẩm)");
                        }
                        else
                        {
                            cartActivity.updateAllCheckStatus(isChecked);
                            tvTongSP.setText("Tổng (" + 0 + " Sản phẩm)");
                        }
                    }
                }
                else
                {
                    ArrayList<ChiTiet> lstct;
                    if (isChecked == true)
                    {
                        cartActivity.updateAllCheckStatus(isChecked);
                        lstct = cartActivity.getLstCT();
                        tvTongSP.setText("Tổng (" + lstct.size() + " Sản phẩm)");
                    }
                    else
                    {
                        cartActivity.updateAllCheckStatus(isChecked);
                        tvTongSP.setText("Tổng (" + 0 + " Sản phẩm)");
                    }
                }
            }
        });

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ChiTiet> temp = cartActivity.getLstCT();
                for (ChiTiet ct : temp)
                {
                    if (ct.isChecked() == true)
                    {
                        ctghHandler.deleteMotCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()), ct.getMasp());
                    }
                }
                cartActivity.UpdateNotify();
                chkSelect.setChecked(false);
                tvTongSP.setText("Tổng (" + 0 + " Sản phẩm)");
            }
        });
    }
    void OpenPop ()
    {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_dathang);

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

        TextView btnHuyPop, btnDatHangPop;
        EditText edtTenKH, edtSDT, edtDiaChi;

        btnHuyPop = dialog.findViewById(R.id.btnHuyPop);
        btnDatHangPop = dialog.findViewById(R.id.btnDatHangPop);
        edtTenKH = dialog.findViewById(R.id.edtTenKH);

        edtSDT = dialog.findViewById(R.id.edtSDT);
        edtSDT.setInputType(InputType.TYPE_CLASS_NUMBER);
        edtSDT.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});

        edtDiaChi = dialog.findViewById(R.id.edtDiaChi);

        edtTenKH.setText(account.getTenkh());
        edtTenKH.setEnabled(false);
        btnHuyPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnDatHangPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((edtSDT.getText().toString().length() == 10) && !edtDiaChi.getText().toString().isEmpty())
                {
                    ArrayList<ChiTiet> temp = cartActivity.getLstCT();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String currentDateandTime = sdf.format(new Date());

                    int tongthanhtien = 0;
                    for (ChiTiet ct : temp)
                    {
                        if (ct.isChecked() == true)
                        {
                            tongthanhtien += ct.getThanhtien();
                        }
                    }
                    long mahoadon = hoaDonHandler.insertHoaDon(account.getMataikhoan(), currentDateandTime, edtDiaChi.getText().toString().trim(), edtSDT.getText().toString(), cartActivity.getHinhThucThanhToan(), tongthanhtien + 100000);

                    for (ChiTiet ct : temp)
                    {
                        if (ct.isChecked() == true)
                        {
                            cthdHandler.insertCTHD(mahoadon, ct);

                            Product pro = productHandler.getSP(ct.getMasp());
                            productHandler.updateSL(ct.getMasp(), pro.getSoluong() - ct.getSoluong());
                        }
                    }
                    for (ChiTiet ct : temp)
                    {
                        if (ct.isChecked() == true)
                        {
                            ctghHandler.deleteMotCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()), ct.getMasp());
                        }
                    }
                    cartActivity.UpdateNotify();
                    chkSelect.setChecked(false);
                    dialog.dismiss();
                    OpenpopSuccess();
                }
                else
                {
                    OpenPopChuaChonSP("Vui lòng điền đầy đủ thông tin");
                }
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    void OpenPopChuaChonSP(String thongbao)
    {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_thongbaochuachonsp);

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

        TextView tvThongBao, btnOK;

        tvThongBao = dialog.findViewById(R.id.tvThongBao);
        tvThongBao.setText(thongbao);

        btnOK = dialog.findViewById(R.id.btnOK);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    void OpenpopSuccess()
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.success_dialog, null);

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(view);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);

        TextView tvThongBao;
        tvThongBao = dialog.findViewById(R.id.tvThongBao);
        tvThongBao.setText("Đặt hàng thành công!");

        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        }, 2000);
    }
    public void setCartActivity(CartActivity activity) {
        this.cartActivity = activity;
    }
    public void getlstchitiet(ArrayList<ChiTiet> lst)
    {
        lstCT = lst;
        countCheck = 0;
        for (ChiTiet ct: lstCT)
        {
            if (ct.isChecked())
            {
                countCheck++;
            }
        }
        if (countCheck != 0)
        {
            tvTongSP.setText("Tổng (" + countCheck + " Sản phẩm)");
        }
        if (countCheck == 0)
        {
            countCheck = -1;
            chkSelect.setChecked(false);
        }
        if (countCheck == lstCT.size())
        {
            countCheck = -1;
            chkSelect.setChecked(true);
        }
    }
}