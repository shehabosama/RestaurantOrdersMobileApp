package com.android.thresturent.Ui.Fragments.HomeFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.thresturent.Ui.Fragments.MenuFragment.MenuFragment;
import com.android.thresturent.R;
import com.android.thresturent.common.base.BaseFragment;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.model.Advertising;
import com.android.thresturent.common.model.LoginResponse;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeContract.View,HomeContract.Model.onFinishedListener , AdvertisingAdapter.AdvertisingInterAction {


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private Button btnOrderNow;
    private RecyclerView recyclerView;
    private PresenterHome presenter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        btnOrderNow = v.findViewById(R.id.btn_order_now);
        recyclerView = v.findViewById(R.id.recycler_advertising);
        swipeRefreshLayout = v.findViewById(R.id.swipe);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        presenter = new PresenterHome(this,this);
        presenter.performGetAds();
        swipeRefreshLayout.setEnabled(false);
    }

    @Override
    protected void setListeners() {
        btnOrderNow.setOnClickListener(btnOrderNowListener);
    }

    private View.OnClickListener btnOrderNowListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            replaceFragment(R.id.container_body, MenuFragment.newInstance(),String.valueOf(Constants.NavigationConstants.ACTION_MENU_ITEM));
        }
    };

    @Override
    public void onFinished(LoginResponse loginResponse) {

    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void loadAds(List<Advertising> advertisings) {

        AdvertisingAdapter adapter = new AdvertisingAdapter(getActivity(),advertisings,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress() {

        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClick() {
        replaceFragment(R.id.container_body,MenuFragment.newInstance(),String.valueOf(Constants.NavigationConstants.ACTION_MENU_ITEM));
    }
}
