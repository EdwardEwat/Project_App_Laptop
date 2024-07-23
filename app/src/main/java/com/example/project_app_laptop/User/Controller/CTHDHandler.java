package com.example.project_app_laptop.User.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.project_app_laptop.User.Models.ChiTiet;

import java.util.ArrayList;

public class CTHDHandler extends SQLiteOpenHelper {
    private String DB_NAME;
    private  int DB_VERSION;
    private static String path;

    public CTHDHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.DB_NAME = name;
        this.DB_VERSION = version;
        path = context.getFilesDir() + "/db/" + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertCTHD(long mahd, ChiTiet ctgh)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        ContentValues values = new ContentValues();
        values.put("MaHoaDon", mahd);
        values.put("MaSanPham", ctgh.getMasp());
        values.put("SoLuong", ctgh.getSoluong());
        values.put("ThanhTien", ctgh.getThanhtien());

        db.insert("CHITIETHOADON", null, values);
        db.close();
    }
    public ArrayList<ChiTiet> getCTHD(int mahd, ArrayList<Integer> lstMaSP)
    {
        ArrayList<ChiTiet> lstcthd = new ArrayList<>();
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        for (Integer masp : lstMaSP)
        {
            String sql = "SELECT SANPHAM.MASANPHAM, SANPHAM.TENSANPHAM, SANPHAM.HINHANH, CHITIETHOADON.SOLUONG, CHITIETHOADON.THANHTIEN FROM CHITIETHOADON, SANPHAM WHERE CHITIETHOADON.MASANPHAM = SANPHAM.MASANPHAM AND CHITIETHOADON.MAHOADON = " + mahd + " AND CHITIETHOADON.MASANPHAM = " + masp;
            Cursor cs = db.rawQuery(sql, null);
            cs.moveToFirst();
            ChiTiet ct = new ChiTiet(cs.getInt(0), cs.getInt(3), 0, cs.getInt(4), cs.getString(1), cs.getString(2), false);
            lstcthd.add(ct);
            cs.close();
        }
        db.close();
        return lstcthd;
    }
    public ArrayList<Integer> getAllMaSP(int mahoadon)
    {
        ArrayList<Integer> lstMaSP = new ArrayList<>();
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT MASANPHAM FROM CHITIETHOADON WHERE MAHOADON = " + mahoadon;
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (cs.isAfterLast() == false)
        {
            lstMaSP.add(cs.getInt(0));
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstMaSP;
    }
}
