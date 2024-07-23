package com.example.project_app_laptop.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project_app_laptop.Admin.Model.Product;
import com.example.project_app_laptop.R;

import java.text.DecimalFormat;
import java.util.List;

public class DetailBrandAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> mProductList;

    public DetailBrandAdapter(Context context, List<Product> productList) {
        mContext = context;
        mProductList = productList;
    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_product, parent, false);
            holder = new ViewHolder();
            holder.imgProduct = convertView.findViewById(R.id.imgHinhSP);
            holder.txtProductName = convertView.findViewById(R.id.tvTenSP);
            holder.txtProductDescription = convertView.findViewById(R.id.tvMoTa);
            holder.txtProductPrice = convertView.findViewById(R.id.tvGiaBan);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the product at the current position
        Product product = mProductList.get(position);

        // Populate the views with product data
        holder.txtProductName.setText(product.getTensp());

        // Format the description
        String mota = product.getMota();
        if (mota.length() > 30) {
            mota = mota.substring(0, 30) + "...";
        }
        holder.txtProductDescription.setText(mota);

        // Format the price
        DecimalFormat decimalFormat = new DecimalFormat("#,###" + "₫");
        String giaban = decimalFormat.format(product.getGiaban());
        holder.txtProductPrice.setText(giaban);

        // Load image resource (if available, else set a default image)
        int imageResId = mContext.getResources().getIdentifier(product.getHinhanh(), "drawable", mContext.getPackageName());
        if (imageResId != 0) {
            holder.imgProduct.setImageResource(imageResId);
        } else {
            holder.imgProduct.setImageResource(R.drawable.hinhhhpvictus_7pa); // Default image if not found
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView imgProduct;
        TextView txtProductName;
        TextView txtProductDescription;
        TextView txtProductPrice;
    }
}
