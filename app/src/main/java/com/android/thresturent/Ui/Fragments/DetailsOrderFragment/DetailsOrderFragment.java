package com.android.thresturent.Ui.Fragments.DetailsOrderFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.thresturent.R;

public class DetailsOrderFragment extends Fragment {

    public DetailsOrderFragment() {
        // Required empty public constructor
    }

    public static DetailsOrderFragment newInstance() {

        return new DetailsOrderFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details_order, container, false);
    }



}
