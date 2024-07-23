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

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.example.project_app_laptop.User.Activity.DetailActivity;
import com.example.project_app_laptop.User.Controller.ProductHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.Product;
import com.example.project_app_laptop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>{
    ArrayList<Product> lst;
    Context context;
    ProductHandler productHandler;
    Account account;
    AHBottomNavigation nav_bottom;
    public ProductAdapter(ArrayList<Product> lst, Context context, Account account) {
        this.lst = lst;
        this.context = context;
        this.account = account;
        productHandler = new ProductHandler(context, "LaptopShop.db", null, 1);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewholder = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_recyproduct, parent, false);
        return new MyViewHolder(viewholder);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product pro = lst.get(position);

        int resourceid = holder.itemView.getContext().getResources().getIdentifier(pro.getHinhanh(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imgSP.setImageResource(resourceid);

        holder.tvTenSP.setText(pro.getTensp().substring(0, 40) + "...");

        DecimalFormat formatter = new DecimalFormat("#,###");
        String giaBanFormatted = formatter.format(pro.getGiaban());
        giaBanFormatted = giaBanFormatted.replace(",", ".");
        holder.tvGiaBan.setText(giaBanFormatted + "đ");

        holder.tvDaBan.setText(holder.tvDaBan.getText() + String.valueOf(productHandler.getSoLuongDaBanProduct(pro.getMasp())));

        holder.layout_itempro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailProduct(pro);
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
        return lst.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {   private ConstraintLayout layout_itempro;
        private ImageView imgSP;
        private TextView tvTenSP, tvGiaBan, tvDaBan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.layout_itempro = itemView.findViewById(R.id.layout_itempro);
            this.imgSP = itemView.findViewById(R.id.imgSP);
            this.tvTenSP = itemView.findViewById(R.id.tvTenSP);
            this.tvGiaBan = itemView.findViewById(R.id.tvGiaBan);
            this.tvDaBan = itemView.findViewById(R.id.tvDaBan);
        }
    }
}
