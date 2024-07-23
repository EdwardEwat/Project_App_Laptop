package com.example.project_app_laptop.User.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class GioHangHandler extends SQLiteOpenHelper {
    private String DB_NAME;
    private int DB_VERSION;
    private static String path;

    public GioHangHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
    public int getMaGioHang(int matk)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT MaGioHang FROM GIOHANG WHERE MATAIKHOAN = " + matk;
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();

        return cs.getInt(0);
    }
    public void deleteAllCTGH(int magiohang)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

        db.delete("CHITIETGIOHANG", "MAGIOHANG = ?", new String[]{String.valueOf(magiohang)});
        db.delete("GIOHANG", "MAGIOHANG = ?", new String[]{String.valueOf(magiohang)});
        db.close();
    }
    public void insertGioHang(int matk)
    {
        SQLiteDatabase db;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues values = new ContentValues();
        values.put("MaTaiKhoan", matk);

        db.insert("GIOHANG", null, values);
        db.close();
    }
}
