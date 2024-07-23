package com.example.project_app_laptop.User.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.project_app_laptop.User.Models.Account;

public class AccountHandler extends SQLiteOpenHelper {
    private String DB_NAME;
    private int DB_VERSION;
    private static String path;

    public AccountHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
    public Account CheckLogin(String tentk, String matkhau)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM USER WHERE TENTAIKHOAN = '" + tentk + "' AND MATKHAU = '" + matkhau + "'";
        Cursor cs = db.rawQuery(sql, null);

        if (cs.getCount() > 0)
        {
            cs.moveToFirst();
            Account acc = new Account(cs.getInt(0), cs.getString(1), cs.getString(2), cs.getString(3));
            cs.close();
            db.close();
            return acc;
        }
        cs.close();
        db.close();
        return null;
    }
    public String getTenKH(int matk)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT TENKHACHHANG FROM USER WHERE MATAIKHOAN = " + matk;
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        return cs.getString(0);
    }
    public boolean KTTrungTenTK(String tentk)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM USER WHERE TENTAIKHOAN = '" + tentk + "'";
        Cursor cs = db.rawQuery(sql, null);
        if (cs.getCount() != 0)
        {
            cs.close();
            db.close();
            return false;
        }
        cs.close();
        db.close();
        return true;
    }
    public void insertAccount(String hotenkh, String tentk, String mk)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("TenKhachHang", hotenkh);
        values.put("TenTaiKhoan", tentk);
        values.put("MatKhau", mk);

        db.insert("USER", null, values);
        db.close();
    }
    public void updateHoTenKH(String tenkh, int matk)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("TenKhachHang", tenkh);

        db.update("USER", values, "MATAIKHOAN = ?", new String[]{String.valueOf(matk)});
        db.close();
    }
    public void updateMatKhau(String matkhau, int matk)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("MatKhau", matkhau);

        db.update("USER", values, "MATAIKHOAN = ?", new String[]{String.valueOf(matk)});
        db.close();
    }
    public  void deleteTaiKhoan(int matk)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        db.delete("USER", "MATAIKHOAN = ?", new String[]{String.valueOf(matk)});
        db.close();
    }
}
