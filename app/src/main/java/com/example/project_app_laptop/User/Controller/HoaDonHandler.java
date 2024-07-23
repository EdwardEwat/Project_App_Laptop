package com.example.project_app_laptop.User.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.project_app_laptop.User.Models.ChiTiet;
import com.example.project_app_laptop.User.Models.HoaDon;
import com.example.project_app_laptop.User.Models.Product;

import java.util.ArrayList;

public class HoaDonHandler extends SQLiteOpenHelper {
    private String DB_NAME;
    private int DB_VERSION;
    private static String path;
    ProductHandler productHandler;

    public HoaDonHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.DB_NAME = name;
        this.DB_VERSION = version;
        path = context.getFilesDir() + "/db/" + DB_NAME;
        productHandler = new ProductHandler(context, "LaptopShop.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<HoaDon> loadHoaDon(ArrayList<Integer> lstmahd)
    {
        SQLiteDatabase db;
        ArrayList<HoaDon> lsthd = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        for (int mahd : lstmahd)
        {
            String sql = "SELECT * FROM HOADON WHERE MAHOADON = " + mahd;
            Cursor cs = db.rawQuery(sql, null);
            cs.moveToFirst();
            while (cs.isAfterLast() == false)
            {
                HoaDon hd = new HoaDon(cs.getInt(0), cs.getInt(1), cs.getInt(6), cs.getString(2), cs.getString(3), cs.getString(4), cs.getString(5), cs.getString(7));
                lsthd.add(hd);
                cs.moveToNext();
            }
            cs.close();
        }
        db.close();
        return lsthd;
    }
    public long insertHoaDon(int matk, String ngaymua, String diachi, String sdt, String hinhthucthanhtoan, int tongtien)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        ContentValues values = new ContentValues();
        values.put("MaTaiKhoan", matk);
        values.put("NgayMua", ngaymua);
        values.put("DiaChi", diachi);
        values.put("SDT", sdt);
        values.put("HinhThucThanhToan", hinhthucthanhtoan);
        values.put("TongTien", tongtien);
        values.put("TrangThai", "Chờ xác nhận");

        long newRowId = db.insertWithOnConflict("HOADON", null, values, SQLiteDatabase.CONFLICT_REPLACE);

        db.close();

        return newRowId;
    }
    public ArrayList<Integer> getAllMaHD(int matk)
    {
        SQLiteDatabase db;
        ArrayList<Integer> lstmahd = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT MAHOADON FROM HOADON WHERE MATAIKHOAN = " + matk;
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (cs.isAfterLast() == false)
        {
            lstmahd.add(cs.getInt(0));
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstmahd;
    }
    public void deleteHoaDon(int mahd)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        db.delete("CHITIETHOADON", "MAHOADON = ?", new String[]{String.valueOf(mahd)});
        db.delete("HOADON", "MAHOADON = ?", new String[]{String.valueOf(mahd)});
        db.close();
    }
    public void deleteAllHoaDon(ArrayList<Integer> lstHD)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        for (Integer mahd : lstHD)
        {
            db.delete("CHITIETHOADON", "MAHOADON = ?", new String[]{String.valueOf(mahd)});
            db.delete("HOADON", "MAHOADON = ?", new String[]{String.valueOf(mahd)});
        }
        db.close();
    }
    public void UpdateLaiSoLuongSPKhiHuy(ArrayList<ChiTiet> lstct)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        for (ChiTiet ct : lstct)
        {
            Product pro = productHandler.getSP(ct.getMasp());
            ContentValues values = new ContentValues();
            values.put("SoLuong", ct.getSoluong() + pro.getSoluong());

            db.update("SANPHAM", values, "masanpham = ?", new String[]{String.valueOf(ct.getMasp())});
        }
    }
}
