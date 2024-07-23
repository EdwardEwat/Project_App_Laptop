package com.example.project_app_laptop.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.project_app_laptop.R;

import com.example.project_app_laptop.Admin.Model.Customer;

import java.util.List;

public class CustomerAdapter extends BaseAdapter {
    private Context context;
    private List<Customer> customerList;
    private LayoutInflater inflater;

    public CustomerAdapter(Context context, List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return customerList.size();
    }

    @Override
    public Object getItem(int position) {
        return customerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return customerList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.layout_item_customer, parent, false);
        }

        TextView tvSTT = view.findViewById(R.id.tvSTT);
        TextView tvTenKH = view.findViewById(R.id.tvTenKH);
        TextView tvTenTK = view.findViewById(R.id.tvTenTK);

        Customer customer = customerList.get(position);

        tvSTT.setText(String.valueOf(position + 1));
        tvTenKH.setText(customer.getName());
        tvTenTK.setText(customer.getUsername());

        return view;
    }
}