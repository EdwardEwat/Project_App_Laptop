package com.example.project_app_laptop.User.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project_app_laptop.User.Adapters.CatAdapter;
import com.example.project_app_laptop.User.Adapters.ProductAdapter;
import com.example.project_app_laptop.User.Controller.CatHandler;
import com.example.project_app_laptop.User.Controller.ProductHandler;
import com.example.project_app_laptop.User.Models.Account;
import com.example.project_app_laptop.User.Models.Categories;
import com.example.project_app_laptop.User.Models.Product;
import com.example.project_app_laptop.R;

import java.util.ArrayList;

public class frmHome_main extends Fragment {
    RecyclerView recyCat, recyTopSale;
    ArrayList<Categories> lstCat;
    CatAdapter myCatadapter;
    ArrayList<Product> lstPro;
    ProductAdapter myTopSaleadapter;
    ProductHandler productHandler;
    CatHandler catHandler;
    Account account;
    TextView tvTenKH;
    public frmHome_main() {

    }
    public static frmHome_main newInstance() {
        frmHome_main fragment = new frmHome_main();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_frm_home_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTenKH = view.findViewById(R.id.tvTenKH);

        recyCat = view.findViewById(R.id.recyCat);
        catHandler = new CatHandler(view.getContext(), "LaptopShop.db", null, 1);
        lstCat = catHandler.loadCat();

        recyTopSale = view.findViewById(R.id.recyTopSale);
        productHandler = new ProductHandler(view.getContext(), "LaptopShop.db", null, 1);
        lstPro = productHandler.loadTop10ProductNoiBat();

        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey("Account")) {
            account = (Account) bundle.getSerializable("Account");
            tvTenKH.setText(account.getTenkh());
            tvTenKH.setVisibility(View.VISIBLE);

            myCatadapter = new CatAdapter(lstCat, getActivity(), account);

            myTopSaleadapter = new ProductAdapter(lstPro, getActivity(), account);
        }
        else
        {
            tvTenKH.setText("");
            tvTenKH.setVisibility(View.GONE);

            myCatadapter = new CatAdapter(lstCat, getActivity(), null);

            myTopSaleadapter = new ProductAdapter(lstPro, getActivity(), null);
        }

        recyCat.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyCat.setItemAnimator(new DefaultItemAnimator());

        recyCat.setAdapter(myCatadapter);

        recyTopSale.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyTopSale.setItemAnimator(new DefaultItemAnimator());

        recyTopSale.setAdapter(myTopSaleadapter);
    }
}