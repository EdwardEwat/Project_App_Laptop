package com.example.project_app_laptop.User.Controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.project_app_laptop.User.Models.Categories;

import java.util.ArrayList;

public class CatHandler extends SQLiteOpenHelper {
    private String DB_NAME;
    private int DB_VERSION;
    private static final String TABLE_NAME = "hang";
    private static String path;

    public CatHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
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
    public ArrayList<Categories> loadCat()
    {
        SQLiteDatabase db;
        ArrayList<Categories> lstCat = new ArrayList<>();
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql = "SELECT * FROM HANG";
        Cursor cs = db.rawQuery(sql, null);
        cs.moveToFirst();
        while (cs.isAfterLast() == false)
        {
            Categories cat = new Categories(cs.getInt(0), cs.getString(1), cs.getString(2));
            lstCat.add(cat);
            cs.moveToNext();
        }
        lstCat.add(new Categories(0, "ALL", "cat5"));
        cs.close();
        db.close();
        return lstCat;
    }
}
