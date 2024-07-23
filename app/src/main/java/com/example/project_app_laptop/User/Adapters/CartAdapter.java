package com.example.project_app_laptop.User.Adapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_app_laptop.User.Activity.CartActivity;
import com.example.project_app_laptop.User.Activity.DetailActivity;
import com.example.project_app_laptop.User.Controller.CTGHHandler;
import com.example.project_app_laptop.User.Controller.GioHangHandler;
import com.example.project_app_laptop.User.Controller.ProductHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.ChiTiet;
import com.example.project_app_laptop.User.Models.Product;
import com.example.project_app_laptop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{
    ArrayList<ChiTiet> lstChiTiet;
    CartActivity cartActivity;
    ProductHandler productHandler;
    CTGHHandler ctghHandler;
    GioHangHandler gioHangHandler;
    Account account;
    public interface OnItemClickListener {
        void sendData(ArrayList<ChiTiet> lstCT);
    }
    private OnItemClickListener mListener;

    public CartAdapter(ArrayList<ChiTiet> lstChiTiet, CartActivity cartActivity, Account account) {
        this.lstChiTiet = lstChiTiet;
        this.cartActivity = cartActivity;
        this.account = account;
        productHandler = new ProductHandler(cartActivity, "LaptopShop.db", null, 1);
        ctghHandler = new CTGHHandler(cartActivity, "LaptopShop.db", null, 1);
        gioHangHandler = new GioHangHandler(cartActivity, "LaptopShop.db", null, 1);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recycart, parent, false);
        return new MyViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChiTiet ct = lstChiTiet.get(position);

        mListener = (OnItemClickListener) holder.itemView.getContext();
        int resourceid = holder.itemView.getContext().getResources().getIdentifier(ct.getHinhanh(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imgProduct.setImageResource(resourceid);

        holder.tvTenSP.setText(ct.getTensp());
        holder.tvSoLuong.setText(String.valueOf(ct.getSoluong()));

        DecimalFormat formatter = new DecimalFormat("#,###");
        String giaBanFormatted = formatter.format(ct.getGiaban());
        giaBanFormatted = giaBanFormatted.replace(",", ".");
        holder.tvGiaBan.setText(holder.tvGiaBan.getText() + giaBanFormatted + "đ");

        String thanhtienFormatted = formatter.format(ct.getThanhtien());
        thanhtienFormatted= thanhtienFormatted.replace(",", ".");
        holder.tvThanhTien.setText(holder.tvThanhTien.getText() + thanhtienFormatted + "đ");

        if (ct.isChecked() == true)
        {
            holder.chkSelect.setChecked(true);
        }
        else
        {
            holder.chkSelect.setChecked(false);
        }

        holder.tvIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = lstChiTiet.get(holder.getAdapterPosition()).getSoluong();
                Product pro = productHandler.getSP(ct.getMasp());
                int slpro = pro.getSoluong();
                for (ChiTiet ct : lstChiTiet)
                {
                    ct.setChecked(false);
                }
                if (currentQuantity + 1 > slpro)
                {
                    Toast.makeText(cartActivity, "Sản phẩm có số lượng tối đa: " + slpro, Toast.LENGTH_LONG).show();
                    holder.tvSoLuong.setText(String.valueOf(1));
                    ctghHandler.updateSoLuongCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()), ct.getMasp(), 1, ct.getGiaban());
                }
                else
                {
                    ctghHandler.updateSoLuongCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()), ct.getMasp(), currentQuantity + 1, ct.getGiaban());
                }
                cartActivity.UpdateNotify();
                sendToCartActivity();
            }
        });
        holder.tvDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentQuantity = lstChiTiet.get(holder.getAdapterPosition()).getSoluong();
                Product pro = productHandler.getSP(ct.getMasp());
                int slpro = pro.getSoluong();
                for (ChiTiet ct : lstChiTiet)
                {
                    ct.setChecked(false);
                }
                if (currentQuantity - 1 > 0)
                {
                    if (currentQuantity - 1 > slpro)
                    {
                        Toast.makeText(cartActivity, "Sản phẩm có số lượng tối đa: " + slpro, Toast.LENGTH_LONG).show();
                        holder.tvSoLuong.setText(String.valueOf(1));
                        ctghHandler.updateSoLuongCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()), ct.getMasp(), 1, ct.getGiaban());
                    }
                    else
                    {
                        ctghHandler.updateSoLuongCTGH(gioHangHandler.getMaGioHang(account.getMataikhoan()), ct.getMasp(), currentQuantity - 1, ct.getGiaban());
                    }
                    cartActivity.UpdateNotify();
                    sendToCartActivity();
                }
            }
        });
        holder.chkSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mListener != null)
                {
                    if (holder.chkSelect.isChecked())
                    {
                        ct.setChecked(isChecked);
                        sendToCartActivity();
                    }
                    else
                    {
                        ct.setChecked(isChecked);
                        sendToCartActivity();
                    }
                }
            }
        });
        holder.constraintLayoutcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailProduct(productHandler.getSP(ct.getMasp()));
            }
        });
    }
    private void DetailProduct(Product pro)
    {
        Intent intent = new Intent(cartActivity, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Product", pro);
        bundle.putSerializable("Account", account);
        intent.putExtras(bundle);
        cartActivity.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return lstChiTiet.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        private ConstraintLayout constraintLayoutcart;
        private CheckBox chkSelect;
        private ImageView imgProduct;
        private TextView tvTenSP, tvGiaBan, tvThanhTien, tvSoLuong, tvIncrease, tvDecrease;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.constraintLayoutcart = itemView.findViewById(R.id.constraintlayoutcart);
            this.chkSelect = itemView.findViewById(R.id.chkSelect);
            this.imgProduct = itemView.findViewById(R.id.imgProduct);
            this.tvTenSP = itemView.findViewById(R.id.tvTenSP);
            this.tvGiaBan = itemView.findViewById(R.id.tvGiaBan);
            this.tvThanhTien = itemView.findViewById(R.id.tvThanhTien);
            this.tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            this.tvIncrease = itemView.findViewById(R.id.tvIncrease);
            this.tvDecrease = itemView.findViewById(R.id.tvDecrease);
        }
    }
    private void sendToCartActivity()
    {
        mListener.sendData(lstChiTiet);
    }
}
