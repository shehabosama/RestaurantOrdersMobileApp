package com.android.thresturent.Ui.Fragments.AboutUSFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.thresturent.R;

public class AboutUSFragment extends Fragment {

    public AboutUSFragment() {
        // Required empty public constructor
    }
    public static AboutUSFragment newInstance (){
        return new AboutUSFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_u, container, false);
    }

}
