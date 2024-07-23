package com.example.project_app_laptop.User.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.project_app_laptop.User.Models.Product;

import java.util.ArrayList;

public class ProductHandler extends SQLiteOpenHelper {
    private String DB_NAME;
    private int DB_VERSION;
    private static final String TABLE_NAME = "sanpham";
    private static String path;
    public ProductHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
    public ArrayList<Product> loadPro()
    {
        SQLiteDatabase db;
        ArrayList<Product> lstPro = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM SANPHAM";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (cs.isAfterLast() == false)
        {
            Product pro = new Product(cs.getInt(0), cs.getInt(14), cs.getInt(13), cs.getString(1), cs.getString(2), cs.getString(3), cs.getString(5), cs.getString(6), cs.getString(7), cs.getString(8), cs.getString(9), cs.getString(10), cs.getString(11), cs.getString(12), cs.getInt(4));
            lstPro.add(pro);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstPro;
    }
    public ArrayList<Product> loadProByCat(int mahang)
    {
        SQLiteDatabase db;
        ArrayList<Product> lstProCat = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM SANPHAM WHERE MAHANG = " + mahang;
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (cs.isAfterLast() == false)
        {
            Product pro = new Product(cs.getInt(0), cs.getInt(14), cs.getInt(13), cs.getString(1), cs.getString(2), cs.getString(3), cs.getString(5), cs.getString(6), cs.getString(7), cs.getString(8), cs.getString(9), cs.getString(10), cs.getString(11), cs.getString(12), cs.getInt(4));
            lstProCat.add(pro);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstProCat;
    }
    public int getSoLuongDaBanProduct(int masanpham)
    {
        int soluongdaban = 0;
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT SUM(chitiethoadon.soluong) AS SoLuongDaBan " +
                        "FROM sanpham INNER JOIN chitiethoadon ON sanpham.MaSanPham = chitiethoadon.masanpham " +
                        "WHERE sanpham.MaSanPham = " + masanpham;
        Cursor cs = db.rawQuery(sql, null);
        if (cs != null && cs.moveToFirst())
        {
            soluongdaban = cs.getInt(0);
        }
        cs.close();
        db.close();
        return soluongdaban;
    }
    public ArrayList<Product> loadTop10ProductNoiBat()
    {
        SQLiteDatabase db;
        ArrayList<Product> lstPro = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM sanpham ORDER BY GiaBan DESC LIMIT 10";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (cs.isAfterLast() == false)
        {
            Product pro = new Product(cs.getInt(0), cs.getInt(14), cs.getInt(13), cs.getString(1), cs.getString(2), cs.getString(3), cs.getString(5), cs.getString(6), cs.getString(7), cs.getString(8), cs.getString(9), cs.getString(10), cs.getString(11), cs.getString(12), cs.getInt(4));
            lstPro.add(pro);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstPro;
    }
    public Product getSP(int masp)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM SANPHAM WHERE MASANPHAM = " + masp;
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        Product pro = new Product(cs.getInt(0), cs.getInt(14), cs.getInt(13), cs.getString(1), cs.getString(2), cs.getString(3), cs.getString(5), cs.getString(6), cs.getString(7), cs.getString(8), cs.getString(9), cs.getString(10), cs.getString(11), cs.getString(12), cs.getInt(4));
        return pro;
    }
    public void updateSL(int masp, int soluong)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        ContentValues values = new ContentValues();
        values.put("SoLuong", soluong);

        db.update("SANPHAM", values, "masanpham = ?", new String[]{String.valueOf(masp)});
    }
}
