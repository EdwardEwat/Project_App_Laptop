package com.example.project_app_laptop.User.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_app_laptop.User.Activity.ChiTietHoaDonActivity;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.HoaDon;
import com.example.project_app_laptop.R;

import java.util.ArrayList;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder>{
    ArrayList<HoaDon> lstHoaDon;
    Context context;
    Account account;

    public BillAdapter(ArrayList<HoaDon> lstHoaDon, Context context, Account account) {
        this.lstHoaDon = lstHoaDon;
        this.context = context;
        this.account = account;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recybill, parent, false);
        return new MyViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HoaDon hd = lstHoaDon.get(position);

        holder.tvMaHoaDon.setText(String.valueOf(hd.getMahd()));
        holder.tvNgayMua.setText(hd.getNgaymua());
        if (hd.getTrangthai().equals("Chờ xác nhận"))
        {
            holder.tvTrangThai.setTextColor(Color.WHITE);
        }
        else
        {
            holder.tvTrangThai.setTextColor(Color.RED);
        }
        holder.tvTrangThai.setText(hd.getTrangthai());

        holder.linearLayoutbill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietHoaDonActivity.class);
                intent.putExtra("HoaDon", hd);
                intent.putExtra("Account", account);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstHoaDon.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private LinearLayout linearLayoutbill;
        private TextView tvMaHoaDon, tvNgayMua, tvTrangThai;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.linearLayoutbill = itemView.findViewById(R.id.linearlayoutbill);
            this.tvMaHoaDon = itemView.findViewById(R.id.tvMaHoaDon);
            this.tvNgayMua = itemView.findViewById(R.id.tvNgayMua);
            this.tvTrangThai = itemView.findViewById(R.id.tvTrangThai);
        }
    }
}
