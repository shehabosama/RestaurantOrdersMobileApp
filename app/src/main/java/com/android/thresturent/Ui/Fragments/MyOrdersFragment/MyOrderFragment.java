package com.android.thresturent.Ui.Fragments.MyOrdersFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.thresturent.R;
import com.android.thresturent.common.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderFragment extends BaseFragment {


    public MyOrderFragment() {
        // Required empty public constructor
    }


    public static MyOrderFragment newInstance (){
        return new MyOrderFragment();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);

        return view;
    }

    @Override
    protected void initializeViews(View v) {

    }

    @Override
    protected void setListeners() {

    }
}
