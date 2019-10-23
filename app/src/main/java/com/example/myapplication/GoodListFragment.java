package com.example.myapplication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoodListFragment extends Fragment {

    LifePriceMainActivity.GoodsArrayAdapter goodsArrayAdapter;

    public GoodListFragment(LifePriceMainActivity.GoodsArrayAdapter goodsArrayAdapter) {
        // Required empty public constructor
        this.goodsArrayAdapter = goodsArrayAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_good_list, container, false);
        ListView listViewSuper = view.findViewById(R.id.list_view_goods);
        listViewSuper.setAdapter(goodsArrayAdapter);

        this.registerForContextMenu(listViewSuper);
        // Inflate the layout for this fragment
        return view;
    }

}
