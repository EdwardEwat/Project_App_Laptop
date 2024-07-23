package com.example.project_app_laptop.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.project_app_laptop.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project_app_laptop.Admin.Model.Brand;
import com.example.project_app_laptop.Admin.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class BrandAdapter extends ArrayAdapter<Brand> {
    private Context context;
    private int resource;
    private ArrayList<Brand> brands;

    public BrandAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Brand> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.brands = objects;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
        }
        TextView tvtenhang = convertView.findViewById(R.id.tvTenHang);
        ImageView imgLogo = convertView.findViewById(R.id.imgLogoHang);

        Brand brand = brands.get(position);
        tvtenhang.setText(brand.getTenhang());
        int resourceId = context.getResources().getIdentifier(brand.getLogo(), "drawable", context.getPackageName());
        if (resourceId != 0) {
            imgLogo.setImageResource(resourceId);
        } else {
            imgLogo.setImageResource(R.drawable.logodell);
        }

        return convertView;
    }
}
