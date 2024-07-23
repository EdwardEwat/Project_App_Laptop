package com.example.project_app_laptop.Admin.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.project_app_laptop.Admin.Model.Brand;
import com.example.project_app_laptop.Admin.Model.Customer;
import com.example.project_app_laptop.Admin.Model.Order;
import com.example.project_app_laptop.Admin.Model.Product;
import com.example.project_app_laptop.Admin.Model.ProductOrder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    SQLiteDatabase database;
    ContentValues values;
    public static final String DBName = "LaptopShop.db";
    public static final String TABLE_PRODUCT = "sanpham";
    public static final String TABLE_ADMIN = "admin";
    public static final String TABLE_HANG = "hang";
    public static final String TABLE_KHACHHANG = "user";
    private static String path;
    private Context context;
    public DBHelper(@Nullable Context context) {
        super(context, DBName, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Kiểm tra tồn tại bảng ADMIN
        if (!tableExists(db, TABLE_ADMIN)) {
            db.execSQL("CREATE TABLE " + TABLE_ADMIN + " (" +
                    "MaTaiKhoan INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "TenTaiKhoan TEXT, " +
                    "MatKhau TEXT)");
        }
        // Kiểm tra tồn tại bảng SANPHAM
        if (!tableExists(db, TABLE_PRODUCT)) {
            db.execSQL("CREATE TABLE " + TABLE_PRODUCT + " (" +
                    "MaSanPham INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "TenSanPham TEXT, " +
                    "MoTa TEXT, " +
                    "HinhAnh TEXT, " +
                    "GiaBan INTEGER, " +
                    "CPU TEXT, " +
                    "Ram TEXT, " +
                    "OCung TEXT, " +
                    "ManHinh TEXT, " +
                    "VGA TEXT, " +
                    "HeDieuHanh TEXT, " +
                    "TrongLuong TEXT, " +
                    "Pin TEXT, " +
                    "SoLuong TEXT, " +
                    "MaHang INTEGER, " +
                    "FOREIGN KEY(MaHang) REFERENCES hang(MaHang)" +
                    ")");
        }

        if (!tableExists(db, TABLE_HANG)) {
            db.execSQL("CREATE TABLE " + TABLE_HANG + " (" +
                    "MaHang INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "TenHang TEXT, " +
                    "Logo TEXT" +")");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return SQLiteDatabase.openOrCreateDatabase(getDatabasePath(), null);
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return SQLiteDatabase.openDatabase(getDatabasePath(), null, SQLiteDatabase.OPEN_READONLY);
    }

    private String getDatabasePath() {
        File file = new File(context.getFilesDir(), "db/" + DBName);
        return file.getAbsolutePath();
    }

    public boolean insertDataRegister(String tentaikhoan, String matkhau) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenTaiKhoan", tentaikhoan);
        contentValues.put("MatKhau", matkhau);
        long result = myDB.insert(TABLE_ADMIN, null, contentValues);
        return result != -1;
    }

    // Kiểm tra tồn tại bảng
    private boolean tableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[] {"table", tableName});
        if (cursor != null) {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            cursor.close();
            return count > 0;
        }
        return false;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM " + TABLE_ADMIN + " WHERE TenTaiKhoan = ?", new String[]{username});
        if(cursor.getCount() > 0) {
            cursor.close();
            myDB.close();
            return true;
        } else {
            cursor.close();
            myDB.close();
            return false;
        }
    }

    public boolean checkUser(String username, String pass) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM " + TABLE_ADMIN + " WHERE TenTaiKhoan = ? AND MatKhau = ?", new String[]{username, pass});
        if(cursor.getCount() > 0) {
            cursor.close();
            myDB.close();
            return true;
        } else {
            cursor.close();
            myDB.close();
            return false;
        }
    }

    // Update mật khẩu
    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MatKhau", newPassword);
        int result = myDB.update(TABLE_ADMIN, contentValues, "TenTaiKhoan = ?", new String[]{username});
        myDB.close();
        return result > 0;
    }


    // Thao tác sản phẩm
    public boolean insertDataSanPham(String tenSanPham, String moTa, String hinhAnh, int giaBan, String cpu, String ram, String oCung, String manHinh, String vga, String heDieuHanh, String trongLuong, String pin, String soLuong, int maHang) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenSanPham", tenSanPham);
        contentValues.put("MoTa", moTa);
        contentValues.put("HinhAnh", hinhAnh);
        contentValues.put("GiaBan", giaBan);
        contentValues.put("CPU", cpu);
        contentValues.put("Ram", ram);
        contentValues.put("OCung", oCung);
        contentValues.put("ManHinh", manHinh);
        contentValues.put("VGA", vga);
        contentValues.put("HeDieuHanh", heDieuHanh);
        contentValues.put("TrongLuong", trongLuong);
        contentValues.put("Pin", pin);
        contentValues.put("SoLuong", soLuong);
        contentValues.put("MaHang", maHang);

        long result = myDB.insert(TABLE_PRODUCT, null, contentValues);
        return result != -1;
    }

    // Load sản phẩm
    public ArrayList<Product> getDataSanPham() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT, null);

        if (cursor.moveToFirst()) {
            do {
                int masp = cursor.getInt(cursor.getColumnIndex("MaSanPham"));
                String tensp = cursor.getString(cursor.getColumnIndex("TenSanPham"));
                String mota = cursor.getString(cursor.getColumnIndex("MoTa"));
                String hinhanh = cursor.getString(cursor.getColumnIndex("HinhAnh"));
                int giaban = cursor.getInt(cursor.getColumnIndex("GiaBan"));
                String cpu = cursor.getString(cursor.getColumnIndex("CPU"));
                String ram = cursor.getString(cursor.getColumnIndex("Ram"));
                String ocung = cursor.getString(cursor.getColumnIndex("OCung"));
                String manhinh = cursor.getString(cursor.getColumnIndex("ManHinh"));
                String vga = cursor.getString(cursor.getColumnIndex("VGA"));
                String hedieuhanh = cursor.getString(cursor.getColumnIndex("HeDieuHanh"));
                String trongluong = cursor.getString(cursor.getColumnIndex("TrongLuong"));
                String pin = cursor.getString(cursor.getColumnIndex("Pin"));
                String soluong = cursor.getString(cursor.getColumnIndex("SoLuong"));
                int mahang = cursor.getInt(cursor.getColumnIndex("MaHang"));

                Product product = new Product(masp, tensp, mota, hinhanh, giaban, cpu, ram, ocung, manhinh, vga, hedieuhanh, trongluong, pin, soluong, mahang);
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return products;
    }

    // Lấy sản phẩm theo ID
    public Product getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PRODUCT + " WHERE MaSanPham = ?", new String[]{String.valueOf(productId)});

        Product product = null;
        if (cursor.moveToFirst()) {
            int masp = cursor.getInt(cursor.getColumnIndex("MaSanPham"));
            String tensp = cursor.getString(cursor.getColumnIndex("TenSanPham"));
            String mota = cursor.getString(cursor.getColumnIndex("MoTa"));
            String hinhanh = cursor.getString(cursor.getColumnIndex("HinhAnh"));
            int giaban = cursor.getInt(cursor.getColumnIndex("GiaBan"));
            String cpu = cursor.getString(cursor.getColumnIndex("CPU"));
            String ram = cursor.getString(cursor.getColumnIndex("Ram"));
            String ocung = cursor.getString(cursor.getColumnIndex("OCung"));
            String manhinh = cursor.getString(cursor.getColumnIndex("ManHinh"));
            String vga = cursor.getString(cursor.getColumnIndex("VGA"));
            String hedieuhanh = cursor.getString(cursor.getColumnIndex("HeDieuHanh"));
            String trongluong = cursor.getString(cursor.getColumnIndex("TrongLuong"));
            String pin = cursor.getString(cursor.getColumnIndex("Pin"));
            String soluong = cursor.getString(cursor.getColumnIndex("SoLuong"));
            int mahang = cursor.getInt(cursor.getColumnIndex("MaHang"));

            product = new Product(masp, tensp, mota, hinhanh, giaban, cpu, ram, ocung, manhinh, vga, hedieuhanh, trongluong, pin, soluong, mahang);
        }
        cursor.close();
        db.close();

        return product;
    }

    // Cập nhật sản phẩm
    public boolean updateProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenSanPham", product.getTensp());
        values.put("GiaBan", product.getGiaban());
        values.put("CPU", product.getCpu());
        values.put("Ram", product.getRam());
        values.put("OCung", product.getOcung());
        values.put("ManHinh", product.getManhinh());
        values.put("VGA", product.getVga());
        values.put("HeDieuHanh", product.getHedieuhanh());
        values.put("TrongLuong", product.getTrongluong());
        values.put("Pin", product.getPin());
        values.put("SoLuong", product.getSoluong());
        values.put("MoTa", product.getMota());
        values.put("MaHang", product.getMahang());

        int result = db.update(TABLE_PRODUCT, values, "MaSanPham = ?", new String[]{String.valueOf(product.getMasp())});
        db.close();
        return result > 0;
    }

    // Xóa sản phẩm
    public boolean deleteProduct(int productId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_PRODUCT, "MaSanPham = ?", new String[]{String.valueOf(productId)});
        db.close();
        return result > 0;
    }

    //Load hãng
    public ArrayList<Brand> getDataHang() {
        ArrayList<Brand> brands = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HANG, null);

        if (cursor.moveToFirst()) {
            do {
                int mahang = cursor.getInt(cursor.getColumnIndex("MaHang"));
                String tenhang = cursor.getString(cursor.getColumnIndex("TenHang"));
                String logo = cursor.getString(cursor.getColumnIndex("Logo"));
                Brand brand = new Brand(mahang, tenhang, logo);
                brands.add(brand);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return brands;
    }

    // Lấy sản phẩm theo ID
    public Brand getBrandById(int brandId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_HANG + " WHERE MaHang = ?", new String[]{String.valueOf(brandId)});

        Brand brand = null;
        if (cursor.moveToFirst()) {
            int mahang = cursor.getInt(cursor.getColumnIndex("MaHang"));
            String tenhang = cursor.getString(cursor.getColumnIndex("TenHang"));
            String logo = cursor.getString(cursor.getColumnIndex("Logo"));

            brand = new Brand(mahang, tenhang, logo);
        }
        cursor.close();
        db.close();

        return brand;
    }

    // Thêm hãng
    public boolean insertDataHang(String tenHang, String logo) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("TenHang", tenHang);
        contentValues.put("Logo", logo);

        long result = myDB.insert(TABLE_HANG, null, contentValues);
        return result != -1;
    }

    // Xóa hãng
    public boolean deleteBrand(int brandID) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_HANG, "MaHang = ?", new String[]{String.valueOf(brandID)});
        db.close();
        return result > 0;
    }


    /////
    // Lấy sản phẩm theo ID
    public List<Product> getProductsByBrandId(int brandId) {
        List<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_PRODUCT +" WHERE MaHang = ?", new String[]{String.valueOf(brandId)});
        if (cursor.moveToFirst()) {
            do {
                int masp = cursor.getInt(cursor.getColumnIndex("MaSanPham"));
                String tensp = cursor.getString(cursor.getColumnIndex("TenSanPham"));
                String mota = cursor.getString(cursor.getColumnIndex("MoTa"));
                String hinhanh = cursor.getString(cursor.getColumnIndex("HinhAnh"));
                int giaban = cursor.getInt(cursor.getColumnIndex("GiaBan"));
                String cpu = cursor.getString(cursor.getColumnIndex("CPU"));
                String ram = cursor.getString(cursor.getColumnIndex("Ram"));
                String ocung = cursor.getString(cursor.getColumnIndex("OCung"));
                String manhinh = cursor.getString(cursor.getColumnIndex("ManHinh"));
                String vga = cursor.getString(cursor.getColumnIndex("VGA"));
                String hedieuhanh = cursor.getString(cursor.getColumnIndex("HeDieuHanh"));
                String trongluong = cursor.getString(cursor.getColumnIndex("TrongLuong"));
                String pin = cursor.getString(cursor.getColumnIndex("Pin"));
                String soluong = cursor.getString(cursor.getColumnIndex("SoLuong"));
                int mahang = cursor.getInt(cursor.getColumnIndex("MaHang"));
                products.add(new Product(masp, tensp, mota, hinhanh, giaban, cpu, ram, ocung, manhinh, vga, hedieuhanh, trongluong, pin, soluong, mahang));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    // Danh sách khách hàng
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_KHACHHANG, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("MaTaiKhoan"));
                String name = cursor.getString(cursor.getColumnIndex("TenKhachHang"));
                String username = cursor.getString(cursor.getColumnIndex("TenTaiKhoan"));
                customers.add(new Customer(id, name, username));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return customers;
    }

    // Order
    public ArrayList<Order> getOrders() {
        ArrayList<Order> lstOr = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();
        String sql = "SELECT * FROM hoadon JOIN user ON hoadon.MaTaiKhoan = user.MaTaiKhoan";
        Cursor cs = myDB.rawQuery(sql, null);
        if (cs != null) {
            while (cs.moveToNext()) {
                int maHoaDon = cs.getInt(cs.getColumnIndexOrThrow("MaHoaDon"));
//                int maTaiKhoan = cs.getInt(cs.getColumnIndexOrThrow("MaTaiKhoan"));
                String tenKhachHang = cs.getString(cs.getColumnIndexOrThrow("TenKhachHang"));
                String ngayMua = cs.getString(cs.getColumnIndexOrThrow("NgayMua"));
                String diaChi = cs.getString(cs.getColumnIndexOrThrow("DiaChi"));
                String SDT = cs.getString(cs.getColumnIndexOrThrow("SDT"));
                String hinhThucThanhToan = cs.getString(cs.getColumnIndexOrThrow("HinhThucThanhToan"));
                int tongTien = cs.getInt(cs.getColumnIndexOrThrow("TongTien"));
                String trangThai = cs.getString(cs.getColumnIndexOrThrow("TrangThai"));

                ArrayList<ProductOrder> lstProduct = new ArrayList<>();
                Cursor chiTietCursor = myDB.rawQuery("SELECT MaSanPham, SoLuong FROM chitiethoadon WHERE MaHoaDon = ?", new String[]{String.valueOf(maHoaDon)});
                if (chiTietCursor != null) {
                    while (chiTietCursor.moveToNext()) {
                        int maSanPham = chiTietCursor.getInt(chiTietCursor.getColumnIndexOrThrow("MaSanPham"));
                        int soLuongCT = chiTietCursor.getInt(chiTietCursor.getColumnIndexOrThrow("SoLuong"));
                        Cursor sanPhamCursor = myDB.rawQuery("SELECT TenSanPham, HinhAnh, SoLuong, GiaBan, MaHang FROM sanpham WHERE MaSanPham = ?", new String[]{String.valueOf(maSanPham)});
                        if (sanPhamCursor != null) {
                            if (sanPhamCursor.moveToFirst()) {
                                String tenSanPham = sanPhamCursor.getString(sanPhamCursor.getColumnIndexOrThrow("TenSanPham"));
                                String hinhAnh = sanPhamCursor.getString(sanPhamCursor.getColumnIndexOrThrow("HinhAnh"));
                                String soLuong = sanPhamCursor.getString(sanPhamCursor.getColumnIndexOrThrow("SoLuong"));
                                int giaBan = sanPhamCursor.getInt(sanPhamCursor.getColumnIndexOrThrow("GiaBan"));
                                int maHang = sanPhamCursor.getInt(sanPhamCursor.getColumnIndexOrThrow("MaHang"));
                                lstProduct.add(new ProductOrder(maSanPham, tenSanPham, hinhAnh, giaBan, soLuong, soLuongCT, maHang));
                            }
                            sanPhamCursor.close();
                        }
                    }
                    chiTietCursor.close();
                }

                lstOr.add(new Order(maHoaDon, tenKhachHang, ngayMua, diaChi, SDT, hinhThucThanhToan, tongTien, trangThai, lstProduct));
            }
            cs.close();
        }
        myDB.close();
        return lstOr;
    }

    public void comfirmStatus(int id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TrangThai", "Đã xác nhận");
        myDB.update("hoadon", values, "MaHoaDon = ?", new String[]{String.valueOf(id)});
//        Cursor cursor = myDB.rawQuery("SELECT MaSanPham, SoLuong FROM chitiethoadon WHERE MaHoaDon = ?", new String[]{String.valueOf(id)});
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    int maSanPhamIndex = cursor.getColumnIndex("MaSanPham");
//                    int soLuongIndex = cursor.getColumnIndex("SoLuong");
//                    Cursor cursorSP = myDB.rawQuery("SELECT SoLuong FROM sanpham WHERE MaSanPham = ?", new String[]{String.valueOf(maSanPhamIndex)});
//                    int SLKho = cursorSP.getColumnIndex("SoLuong");
//                    if (maSanPhamIndex != -1 && soLuongIndex != -1) {
//                        int maSanPham = cursor.getInt(maSanPhamIndex);
//                        int soLuong = cursor.getInt(soLuongIndex);
//                        int SoLuongKho = cursorSP.getInt(SLKho);
//                        ContentValues productValues = new ContentValues();
//                        productValues.put("SoLuong", SoLuongKho - soLuong);
//                        myDB.update("sanpham", productValues, "MaSanPham = ?", new String[]{String.valueOf(maSanPham)});
//                    }
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//        }

        myDB.close();
    }
    public void cancelOrder(int id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TrangThai", "Hủy đơn hàng");
        myDB.update("hoadon", values, "MaHoaDon = ?", new String[]{String.valueOf(id)});
        myDB.close();
    }
    public ArrayList<String> getBands(){
        ArrayList<String> Bands = new ArrayList<>();
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cs = myDB.rawQuery("SELECT TenHang FROM hang", null);
        if(cs != null){
            while(cs.moveToNext()){
                String tenHang = cs.getString(cs.getColumnIndexOrThrow("TenHang"));
                Bands.add(tenHang);
            }
        }
        return Bands;
    }

    // Cập nhật hãng
    public boolean updateBrand(int brandId, String tenHang, String logo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TenHang", tenHang);
        values.put("Logo", logo);
        int result = db.update(TABLE_HANG, values, "MaHang = ?", new String[]{String.valueOf(brandId)});
        db.close();
        return result > 0;
    }

    public void calculateAndUpdateTotals(int maHoaDon) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor chiTietCursor = myDB.rawQuery("SELECT MaSanPham, SoLuong FROM chitiethoadon WHERE MaHoaDon = ?", new String[]{String.valueOf(maHoaDon)});
        if (chiTietCursor != null && chiTietCursor.moveToFirst()) {
            int maSanPhamIndex = chiTietCursor.getColumnIndex("MaSanPham");
            int soLuongIndex = chiTietCursor.getColumnIndex("SoLuong");
            if (maSanPhamIndex != -1 && soLuongIndex != -1) {
                do {
                    int maSanPham = chiTietCursor.getInt(maSanPhamIndex);
                    int soLuong = chiTietCursor.getInt(soLuongIndex);
                    Cursor sanPhamCursor = myDB.rawQuery("SELECT GiaBan FROM sanpham WHERE MaSanPham = ?", new String[]{String.valueOf(maSanPham)});
                    if (sanPhamCursor != null && sanPhamCursor.moveToFirst()) {
                        int giaBan = sanPhamCursor.getInt(sanPhamCursor.getColumnIndexOrThrow("GiaBan"));
                        int thanhTien = soLuong * giaBan;
                        ContentValues chiTietValues = new ContentValues();
                        chiTietValues.put("ThanhTien", thanhTien);
                        myDB.update("chitiethoadon", chiTietValues, "MaSanPham = ?", new String[]{String.valueOf(maSanPham)});
                    }
                    if (sanPhamCursor != null) {
                        sanPhamCursor.close();
                    }
                } while (chiTietCursor.moveToNext());
            }
            chiTietCursor.close();
        }
        Cursor tongTienCursor = myDB.rawQuery("SELECT SUM(ThanhTien) AS TongTien FROM chitiethoadon WHERE MaHoaDon = ?", new String[]{String.valueOf(maHoaDon)});
        if (tongTienCursor != null && tongTienCursor.moveToFirst()) {
            int tongTien = tongTienCursor.getInt(tongTienCursor.getColumnIndexOrThrow("TongTien"));
            ContentValues hoaDonValues = new ContentValues();
            hoaDonValues.put("TongTien", tongTien);
            myDB.update("hoadon", hoaDonValues, "MaHoaDon = ?", new String[]{String.valueOf(maHoaDon)});
            tongTienCursor.close();
        }
        myDB.close();
    }
}
