package com.example.project_app_laptop.User.Controller;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.project_app_laptop.User.Models.ChiTiet;
import com.example.project_app_laptop.User.Models.Product;
import com.example.project_app_laptop.R;

import java.util.ArrayList;

public class CTGHHandler extends SQLiteOpenHelper {
    private String DB_NAME;
    private int DB_VERSION;
    private static String path;
    Context context;

    public CTGHHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.DB_NAME = name;
        this.DB_VERSION = version;
        path = context.getFilesDir() + "/db/" + DB_NAME;
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<ChiTiet> loadCTGH(int magiohang)
    {
        SQLiteDatabase db;
        ArrayList<ChiTiet> lstCTGH = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT CHITIETGIOHANG.MASANPHAM, SANPHAM.TENSANPHAM, SANPHAM.HINHANH, SANPHAM.GIABAN, CHITIETGIOHANG.SOLUONG, CHITIETGIOHANG.THANHTIEN, SANPHAM.SOLUONG " +
                        "FROM CHITIETGIOHANG, SANPHAM " +
                        "WHERE CHITIETGIOHANG.MASANPHAM = SANPHAM.MASANPHAM AND CHITIETGIOHANG.MAGIOHANG = " + magiohang;
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (cs.isAfterLast() == false)
        {
            if (cs.getInt(6) == 0)
            {
                db.delete("CHITIETGIOHANG", "MAGIOHANG = ? and MASANPHAM = ?", new String[]{String.valueOf(magiohang), String.valueOf(cs.getInt(0))});
            }
            else
            {
                ChiTiet ct = new ChiTiet(cs.getInt(0), cs.getInt(4), cs.getInt(3), cs.getInt(5), cs.getString(1), cs.getString(2), false);
                lstCTGH.add(ct);
            }
           cs.moveToNext();
        }
        return lstCTGH;
    }
    public boolean insertGHByButtonMua(int magiohang, Product pro)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM CHITIETGIOHANG WHERE MAGIOHANG = " + magiohang + " AND MASANPHAM = " + pro.getMasp();
        Cursor cs = db.rawQuery(sql, null);

        if (cs.getCount() > 0)
        {
            cs.moveToFirst();
            int soluong = cs.getInt(2) + 1;
            if (soluong > pro.getSoluong())
            {
                OpenPopChuaChonSP();
                return false;
            }
            else
            {
                ContentValues values = new ContentValues();
                values.put("SoLuong", soluong);
                values.put("ThanhTien", soluong * pro.getGiaban());

                db.update("CHITIETGIOHANG", values, "magiohang = ? and masanpham = ?", new String[]{String.valueOf(magiohang), String.valueOf(pro.getMasp())});
            }
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put("MAGIOHANG", magiohang);
            values.put("MASANPHAM", pro.getMasp());
            values.put("SOLUONG", 1);
            values.put("THANHTIEN", pro.getGiaban());

            db.insert("CHITIETGIOHANG", null, values);
        }
        cs.close();
        db.close();
        return true;
    }
    public void deleteMotCTGH(int magiohang, int masp)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        db.delete("CHITIETGIOHANG", "MAGIOHANG = ? and MASANPHAM = ?", new String[]{String.valueOf(magiohang), String.valueOf(masp)});
        db.close();
    }
    public void updateSoLuongCTGH(int magiohang, int masp, int soluong, int giaban)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        ContentValues values = new ContentValues();
        values.put("SoLuong", soluong);
        values.put("ThanhTien", soluong * giaban);

        db.update("CHITIETGIOHANG", values, "magiohang = ? and masanpham = ?", new String[]{String.valueOf(magiohang), String.valueOf(masp)});
    }
    void OpenPopChuaChonSP()
    {
        Dialog dialog = new Dialog(context);
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
        tvThongBao.setText("Số lượng trong giỏ hàng đã đạt số lượng tối đa");

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
}
