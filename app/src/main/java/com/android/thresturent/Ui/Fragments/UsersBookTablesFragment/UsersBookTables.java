package com.android.thresturent.Ui.Fragments.UsersBookTablesFragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.thresturent.R;
import com.android.thresturent.common.base.BaseFragment;
import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.helper.Message;
import com.android.thresturent.common.model.BookTableResponse;
import com.android.thresturent.common.model.BookTables;

import java.util.ArrayList;
import java.util.List;


public class UsersBookTables extends BaseFragment implements GetAllBookContract.Model.onFinishedListener,GetAllBookContract.View, BookTablesAdapter.BookTableInterAction {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout srlList;
    private List<BookTables> bookTables;
    private PresenterGetAllBookTable presenter;

    public static UsersBookTables newInstance() {
        return new UsersBookTables();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_book_tables, container, false);
        initializeViews(view);
        setListeners();
        return view ;
    }


    @Override
    protected void initializeViews(View v) {
        recyclerView = v.findViewById(R.id.recycler_department);
        srlList = v.findViewById(R.id.srlList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        bookTables = new ArrayList<>();
        presenter = new PresenterGetAllBookTable(this,this);
        presenter.performGetAllBookTable(AppPreferences.getString(Constants.AppPreferences.USER_KEY,getActivity(),"0"));
    }

    @Override
    protected void setListeners() {

        srlList.setOnRefreshListener(srlListListener);
    }
    private SwipeRefreshLayout.OnRefreshListener srlListListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            presenter.performGetAllBookTable(AppPreferences.getString(Constants.AppPreferences.USER_KEY,getActivity(),"0"));
        }
    };

    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),result);
    }

    @Override
    public void loadAllBookTable(BookTableResponse response) {
        bookTables.clear();
        bookTables.addAll(response.getBookTables());
       BookTablesAdapter adapter = new BookTablesAdapter(getActivity(),bookTables,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void showProgress() {

        srlList.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        srlList.setRefreshing(false);
    }


}
