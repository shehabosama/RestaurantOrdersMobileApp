package com.android.thresturent.Ui.Fragments.GetAllOrderDeleted;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.thresturent.R;
import com.android.thresturent.common.base.BaseFragment;
import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.helper.Message;
import com.android.thresturent.common.model.MyOrder;
import com.android.thresturent.common.model.MyOrderResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GetAllOrderDeletedFragment extends BaseFragment implements GetAllDeletedRequestContract.Model.onFinishedListener,GetAllDeletedRequestContract.View ,MyOrderAdapter.MyOrderInterAction {

    private RecyclerView recyclerView;
    private List<MyOrder> menuItemList;
    private SwipeRefreshLayout srlList;
    private PresenterGetAllDeletedRequest presenter;
    private double sumofMyOrders;
    private TextView textTotal,textTotalBills,textDifference;
    public GetAllOrderDeletedFragment() {
        // Required empty public constructor
    }

    public static GetAllOrderDeletedFragment newInstance() {
        return new  GetAllOrderDeletedFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get_all_order_deleted, container, false);
        initializeViews(view);
        setListeners();
        return view ;
    }

    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_department);
        srlList = v.findViewById(R.id.srlList);
        textTotal = v.findViewById(R.id.total_price);
        textTotalBills = v.findViewById(R.id.bill_price);
        textDifference = v.findViewById(R.id.difference_price);
        presenter = new PresenterGetAllDeletedRequest(this,this);
        presenter.performGetAllRequestItem();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListListener);
    }

    SwipeRefreshLayout.OnRefreshListener srlListListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.performGetAllRequestItem();
        }
    };
    @Override
    public void onFinished(String str) {
        Message.message(getActivity(),str);
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void loadRequestItemList(MyOrderResponse myOrderResponse) {
        sumofMyOrders = 0;
        menuItemList = new ArrayList<>();
        menuItemList.addAll(myOrderResponse.getItem());
        calculateBills(menuItemList);
        boolean isAdmin = AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN, getActivity(),false);
        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(getActivity(),menuItemList,this,isAdmin);
        recyclerView.setAdapter(myOrderAdapter);
    }
    public void calculateBills(List<MyOrder> myOrders){
        for(int i=0;i<myOrders.size();i++){
            sumofMyOrders += Double.parseDouble(myOrders.get(i).getPrice());

        }
        textTotal.setText("Total Sales are :"+ String.format("%.2f", (double)sumofMyOrders));
        if(TextUtils.isEmpty(AppPreferences.getString(Constants.AppPreferences.TOTAL_OF_BILL,getActivity(),""))){
            Message.message(getActivity(),"Please go to the Notification first to see the orders");
        }else{
            textTotalBills.setText("Total bills are :"+AppPreferences.getString(Constants.AppPreferences.TOTAL_OF_BILL,getActivity(),""));
            double dif = Double.parseDouble(AppPreferences.getString(Constants.AppPreferences.TOTAL_OF_BILL,getActivity(),"")) -  sumofMyOrders;
            textDifference.setText("Total Difference between bills and sales are : " +String.format("%.2f", (double)dif));
        }



    }
    @Override
    public void showProgress() {
        srlList.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        srlList.setRefreshing(false);
    }

    @Override
    public void onClickAccept(MyOrder myOrder) {

    }

    @Override
    public void onClickReject(MyOrder myOrder) {

    }

    @Override
    public void onClickDelete(MyOrder myOrder) {
        presenter.performDeleteForEverItem(myOrder.getRequest_id());
    }
}
