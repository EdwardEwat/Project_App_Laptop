package com.example.project_app_laptop.User.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapterCTHD extends FragmentStatePagerAdapter {
    ArrayList<Fragment> lstFrm = new ArrayList<>();

    public ViewPagerAdapterCTHD(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return lstFrm.get(position);
    }

    @Override
    public int getCount() {
        return lstFrm.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        switch (position)
        {
            case 0:
            {
                title = "THÔNG TIN KHÁCH HÀNG";
                break;
            }
            case 1:
            {
                title = "THÔNG TIN ĐƠN HÀNG";
                break;
            }
            case 2:
            {
                title = "DANH SÁCH SẢN PHẨM";
                break;
            }
        }
        return title;
    }
    public void addFragment(Fragment frm)
    {
        lstFrm.add(frm);
        notifyDataSetChanged();
    }
}
