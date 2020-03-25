package com.android.thresturent.Ui.Fragments.NotificationFragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
public class NotificationFragment extends BaseFragment implements NotificationContract.View,NotificationContract.Model.onFinishedListener , MyOrderAdapter.MyOrderInterAction {

    private RecyclerView recyclerView;
    private List<MyOrder> menuItemList;
    private SwipeRefreshLayout srlList;
    private TextView textTotal;
    PresenterNotification presenter;
    private boolean isAdmin;
    private double sumofMyOrders;
    public NotificationFragment() {
        // Required empty public constructor
    }
    public static NotificationFragment newInstance(){
        return new NotificationFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_department);
        srlList = v.findViewById(R.id.srlList);
        textTotal = v.findViewById(R.id.total_price);

        presenter = new PresenterNotification(this,this);
        String userType = AppPreferences.getString(Constants.AppPreferences.USER_TYPE,getActivity(),"t");
        isAdmin = AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN, getActivity(),false);
        if(userType.equals("table")){
            presenter.performGetAllRequestItem(AppPreferences.getString(Constants.AppPreferences.USER_KEY,getActivity(),""));
        }else{
            if(isAdmin){
                presenter.performGetAllRequestItem("");

            }else{
                presenter.performGetAllRequestItem(AppPreferences.getString(Constants.AppPreferences.USER_KEY,getActivity(),""));

            }
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }

    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListRefreshListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            menuItemList.clear();
            if(isAdmin){
                presenter.performGetAllRequestItem("");

            }else{
                presenter.performGetAllRequestItem(AppPreferences.getString(Constants.AppPreferences.USER_KEY,getActivity(),""));

            }
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
        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(getActivity(),menuItemList,this,isAdmin);
        recyclerView.setAdapter(myOrderAdapter);

    }
    public void calculateBills(List<MyOrder> myOrders){
        for(int i=0;i<myOrders.size();i++){
            sumofMyOrders += Double.parseDouble(myOrders.get(i).getPrice());

        }
        textTotal.setText("Total Bills are :"+ String.format("%.2f", (double)sumofMyOrders));
        AppPreferences.setString(Constants.AppPreferences.TOTAL_OF_BILL,String.format("%.2f", (double)sumofMyOrders),getActivity());

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
        presenter.updateItemStatus(myOrder.getUser_id(),
                myOrder.getId(),myOrder.getRequest_id(),
                "1",AppPreferences.getString(Constants.AppPreferences.USER_TOKEN,
                        getActivity(),""));
        refreshingRecycler();
    }

    public void refreshingRecycler(){
        srlList.post(new Runnable() {
            @Override public void run() {
                srlList.setRefreshing(true);
                // directly call onRefresh() method
                srlListRefreshListener.onRefresh();
            }
        });
        srlList.setRefreshing(false);
    }

    @Override
    public void onClickReject(MyOrder myOrder) {
        presenter.updateItemStatus(myOrder.getUser_id(),
                myOrder.getId(),
                myOrder.getRequest_id(),
                "2",AppPreferences.getString(Constants.AppPreferences.USER_TOKEN,getActivity(),
                        ""));
        refreshingRecycler();
    }

    @Override
    public void onClickDelete(MyOrder myOrder) {
        Custome_dialogs_Delete custome_dialogs_delete = new Custome_dialogs_Delete();
        custome_dialogs_delete.showDialog(getActivity(),presenter,myOrder.getId(),myOrder.getUser_id(),"1",myOrder.getRequest_id());
    }

    @Override
    public void onClickDeleteEver(MyOrder myOrder) {
        presenter.deleteItemEver(myOrder.getRequest_id());
    }
}
