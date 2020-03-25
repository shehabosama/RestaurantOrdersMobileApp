package com.android.thresturent.Ui.Fragments.MenuFragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.thresturent.R;
import com.android.thresturent.common.base.BaseFragment;
import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.helper.Message;
import com.android.thresturent.common.model.AllItemRsponse;
import com.android.thresturent.common.model.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends BaseFragment implements MenuItemContract.Model.onFinishedListener,MenuItemContract.View , MenuItemAdapter.MenuInterAction {


    PresenterManuItem presenter;
    private List<MenuItem> menuItemList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout srlList;
    private String userType;
    public MenuFragment() {
        // Required empty public constructor
    }


    public static MenuFragment newInstance() {

        return new MenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_manu_fragmnet, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }


    @Override
    public void onFinished(String str) {
        Message.message(getActivity(),str);
    }

    @Override
    public void onFailuer(Throwable t) {


    }

    @Override
    public void loadAllItemList(AllItemRsponse allItemRsponse) {

        menuItemList.addAll(allItemRsponse.getMenuItems());
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(getActivity(),menuItemList,this);
        recyclerView.setAdapter(menuItemAdapter);
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
    protected void initializeViews(View v) {
        menuItemList = new ArrayList<>();
        recyclerView = v.findViewById(R.id.recycler_department);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        presenter = new PresenterManuItem(getActivity(),this,this);
         userType = AppPreferences.getString(Constants.AppPreferences.USER_TYPE,getActivity(),"t");

           presenter.performGetAllMenuItem();



    }

    @Override
    protected void setListeners() {
        srlList.setOnRefreshListener(srlListRefreshListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            menuItemList.clear();
            presenter.performGetAllMenuItem();
        }
    };

    @Override
    public void onClickRequestOrder(MenuItem menuItem) {

        if(userType.equals("table")){
            presenter.performOrderRequest(String.valueOf(menuItem.getId()),
                    AppPreferences.getString(Constants.AppPreferences.USER_KEY,getActivity(),""),
                    AppPreferences.getString(Constants.AppPreferences.USER_TOKEN,getActivity(),""),"","2");

        }else{

            Custome_dialogs_request custome_dialogs_request = new Custome_dialogs_request();
            custome_dialogs_request.showDialog(getActivity(),presenter,String.valueOf(menuItem.getId()));
        }



    }

    @Override
    public void onClickItem(final MenuItem menuItem) {
        final CharSequence option[]=new CharSequence[]{"Rate the meal",AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,getActivity(),false)?"Delete Order":""};

        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        builder.setTitle("Menu");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch(which){
                    case 0:
                        Custome_dialogs_rate custome_dialogs_rate = new Custome_dialogs_rate();
                        custome_dialogs_rate.showDialog(getActivity(),presenter,String.valueOf(menuItem.getId()),AppPreferences.getString(Constants.AppPreferences.USER_KEY,getActivity(),""));
                        break;

                    case 1:
                        presenter.performDeleteMenuItem(String.valueOf(menuItem.getId()),menuItem.getItem_image());
                        break;
                }

            }
        });
        builder.show();
    }


}
