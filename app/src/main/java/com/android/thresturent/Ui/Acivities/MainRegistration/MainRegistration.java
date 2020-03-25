package com.android.thresturent.Ui.Acivities.MainRegistration;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.thresturent.Ui.Acivities.MainActivity.MainActivity;
import com.android.thresturent.R;
import com.android.thresturent.common.SqlHelper.myDbAdapter;
import com.android.thresturent.common.base.BaseActivity;
import com.android.thresturent.Ui.Acivities.Login.LoginActivity;

public class MainRegistration extends BaseActivity  {
    private Button user_btn , table_btn , admin_btn;
    private myDbAdapter helper;
    public static void startActivity(Context context){
        context.startActivity(new Intent(context,MainRegistration.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_regstration);
        initializeViews();
        setListeners();
    }
    private View.OnClickListener admin_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginActivity.startActivity(MainRegistration.this,"admin");
        }
    };

    private View.OnClickListener user_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginActivity.startActivity(MainRegistration.this,"user");
        }
    };
    private View.OnClickListener table_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            LoginActivity.startActivity(MainRegistration.this,"table");
        }
    };



    @Override
    protected void initializeViews() {
        table_btn = findViewById(R.id.table);
        user_btn = findViewById(R.id.user);
        admin_btn = findViewById(R.id.admin);
        helper = new myDbAdapter(this);
        String data = helper.getData();
        if (data.length()>0){
            MainActivity.startActivity(MainRegistration.this);
        }
    }

    @Override
    protected void setListeners() {
        user_btn.setOnClickListener(user_listener);
        admin_btn.setOnClickListener(admin_listener);
        table_btn.setOnClickListener(table_listener);
    }
}
