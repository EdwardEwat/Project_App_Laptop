package com.example.project_app_laptop.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.project_app_laptop.Admin.Model.Brand;
import com.example.project_app_laptop.Admin.Model.Product;
import com.example.project_app_laptop.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {
    private Context context;
    private int resource;
    private ArrayList<Product> products;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Product> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.products = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
        }

        ImageView imgHinhSP = convertView.findViewById(R.id.imgHinhSP);
        TextView tvTenSP = convertView.findViewById(R.id.tvTenSP);
        TextView tvGiaBan = convertView.findViewById(R.id.tvGiaBan);
        TextView tvMoTa = convertView.findViewById(R.id.tvMoTa);

        Product product = products.get(position);

        int resourceId = context.getResources().getIdentifier(product.getHinhanh(), "drawable", context.getPackageName());
        if (resourceId != 0) {
            imgHinhSP.setImageResource(resourceId);
        } else {
            imgHinhSP.setImageResource(R.drawable.logodell);
        }

        tvTenSP.setText(product.getTensp());

        DecimalFormat decimalFormat = new DecimalFormat("#,###" + "₫");
        String giaban = decimalFormat.format(product.getGiaban());
        tvGiaBan.setText(giaban);

        String mota = product.getMota();
        if (mota.length() > 30) {
            mota = mota.substring(0, 30) + "...";
        }
        tvMoTa.setText(mota);

        return convertView;
    }
}
