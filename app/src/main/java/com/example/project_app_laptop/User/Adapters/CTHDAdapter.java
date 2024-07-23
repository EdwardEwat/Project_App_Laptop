package com.example.project_app_laptop.User.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_app_laptop.User.Activity.DetailActivity;
import com.example.project_app_laptop.User.Controller.ProductHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.ChiTiet;
import com.example.project_app_laptop.User.Models.Product;
import com.example.project_app_laptop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CTHDAdapter extends RecyclerView.Adapter<CTHDAdapter.MyViewHolder>{
    ArrayList<ChiTiet> lstCT;
    Context context;
    Account account;
    ProductHandler productHandler;
    public CTHDAdapter(ArrayList<ChiTiet> lstCT, Context context, Account account) {
        this.lstCT = lstCT;
        this.context = context;
        this.account = account;
        productHandler = new ProductHandler(context, "LaptopShop.db", null, 1);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycthd, parent, false);
        return new MyViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChiTiet ct = lstCT.get(position);

        int resourceid = holder.itemView.getContext().getResources().getIdentifier(ct.getHinhanh(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imgProduct.setImageResource(resourceid);

        holder.tvTenSP.setText(ct.getTensp());
        holder.tvSoLuong.setText("Số lượng: " + String.valueOf(ct.getSoluong()));

        DecimalFormat formatter = new DecimalFormat("#,###");
        String thanhtienFormatted = formatter.format(ct.getThanhtien());
        thanhtienFormatted = thanhtienFormatted.replace(",", ".");
        holder.tvThanhTien.setText("Thành tiền: " + thanhtienFormatted + "đ");

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailProduct(productHandler.getSP(ct.getMasp()));
            }
        });
    }
    private void DetailProduct(Product pro)
    {
        Intent intent = new Intent(context, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Product", pro);
        bundle.putSerializable("Account", account);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return lstCT.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ConstraintLayout constraintLayout;
        private ImageView imgProduct;
        private TextView tvTenSP, tvSoLuong, tvThanhTien;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.constraintLayout = itemView.findViewById(R.id.linearconstraint);
            this.imgProduct = itemView.findViewById(R.id.imgProduct);
            this.tvTenSP = itemView.findViewById(R.id.tvTenSP);
            this.tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            this.tvThanhTien = itemView.findViewById(R.id.tvThanhTien);
        }
    }
}
