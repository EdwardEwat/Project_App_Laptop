package com.example.project_app_laptop.Admin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.project_app_laptop.Admin.Controller.DBHelper;
import com.example.project_app_laptop.Admin.Model.Order;
import com.example.project_app_laptop.Admin.Model.ProductOrder;
import com.example.project_app_laptop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class ListProductsOrderAdapter extends ArrayAdapter<ProductOrder> {
    Context context;
    int IDLayout;
    ArrayList<ProductOrder> lstPro;


    public ListProductsOrderAdapter(@NonNull Context context, int IDLayout, ArrayList<ProductOrder> lstPro) {
        super(context, IDLayout, lstPro);
        this.context = context;
        this.IDLayout = IDLayout;
        this.lstPro = lstPro;
    }

    @SuppressLint("DefaultLocale")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProductOrder PO = lstPro.get(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(IDLayout, null, true);
        }
        ImageView imgV = (ImageView) convertView.findViewById(R.id.imgPO);
        int resourceid = getContext().getResources().getIdentifier(PO.getHinhAnh(), "drawable", getContext().getPackageName());
        imgV.setImageResource(resourceid);
        TextView txtNamePO = (TextView) convertView.findViewById(R.id.txtNamePO);
        txtNamePO.setText(PO.getTenSanPham());
        TextView txtSL = (TextView) convertView.findViewById(R.id.txtSLKho);
//        txtSL.setText(String.format("Số lượng trong kho: %s", PO.getSoLuong()));
        txtSL.setVisibility(View.GONE);
        TextView txtSLMua = (TextView) convertView.findViewById(R.id.txtSLMua);
        txtSLMua.setText(String.format("Đã mua: %s", String.valueOf(PO.getSoLuongChiTiet())));
        TextView txtGiaBanPO = (TextView) convertView.findViewById(R.id.txtGiaPO);
//        txtGiaBanPO.setText(String.format("Giá bán: %d đ", PO.getGiaBan()));

        DecimalFormat decimalFormat = new DecimalFormat("#,###" + "₫");
        String giaban = decimalFormat.format(PO.getGiaBan());
        txtGiaBanPO.setText("Tổng tiền: " + giaban);
        return convertView;
    }
}
