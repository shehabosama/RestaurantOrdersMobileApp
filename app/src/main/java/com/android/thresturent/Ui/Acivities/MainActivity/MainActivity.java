package com.android.thresturent.Ui.Acivities.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.thresturent.Ui.Acivities.MainRegistration.MainRegistration;
import com.android.thresturent.Ui.Fragments.AboutUSFragment.AboutUSFragment;
import com.android.thresturent.Ui.Fragments.AddAdsItemFragment.AddAdsItemFragment;
import com.android.thresturent.Ui.Fragments.AddMenuItemFragment.AddMenuItemFragment;
import com.android.thresturent.Ui.Fragments.BookTableFragment.BookTableFragemnt;
import com.android.thresturent.Ui.Fragments.ControlPanelFragment.ControlPanelFragment;
import com.android.thresturent.FCM.FCMRegisterationService;
import com.android.thresturent.Ui.Fragments.GetAllBookTableFragment.GetAllBookTableFragment;
import com.android.thresturent.Ui.Fragments.GetAllOrderDeleted.GetAllOrderDeletedFragment;
import com.android.thresturent.Ui.Fragments.HomeFragment.HomeFragment;
import com.android.thresturent.Ui.Fragments.MenuFragment.MenuFragment;
import com.android.thresturent.Ui.Fragments.NotificationFragment.NotificationFragment;
import com.android.thresturent.Ui.Fragments.ProfileFragment.ProfileFragment;
import com.android.thresturent.R;
import com.android.thresturent.Ui.Fragments.SettingsFragment.SettingsFragment;
import com.android.thresturent.Ui.Fragments.UsersBookTablesFragment.UsersBookTables;
import com.android.thresturent.common.SqlHelper.myDbAdapter;
import com.android.thresturent.common.base.BaseActivity;
import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.helper.Message;
import com.android.thresturent.common.model.LoginResponse;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;

public class MainActivity extends BaseActivity implements MainContract.Model.onFinishedListener,MainContract.View {
    private Toolbar toolbar;
    private TextView Navusername;
    private FrameLayout relativeLayout;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private myDbAdapter helper;
    private boolean isAdmin;
    private boolean isBlocked;
    private PresenterMainActivity presenter;
    public static void startActivity(Context context){
        context.startActivity(new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.rate_us:
                Message.message(MainActivity.this,"Under development");
                break;
        }
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void UserMenuSelector(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch(item.getItemId()){
            case R.id.action_add_menu_item:
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_ADD_MENU_ITEM)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_ADD_MENU_ITEM))));
                }else{
                    replaceFragment(R.id.container_body, AddMenuItemFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_ADD_MENU_ITEM));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_add_ads_item:
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_ADD_ADS_ITEM)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_ADD_ADS_ITEM))));
                }else{
                    replaceFragment(R.id.container_body, AddAdsItemFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_ADD_ADS_ITEM));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_menu_item:
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_MENU_ITEM)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_MENU_ITEM))));
                }else{
                    replaceFragment(R.id.container_body, MenuFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_MENU_ITEM));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_home:
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_HOME)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_HOME))));
                }else{
                    replaceFragment(R.id.container_body, HomeFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_HOME));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_profile:
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_PROFILE)) != null) {
                    Log.e("FRAGMENT", "UserMenuSelector: SECOND open fragment");
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_PROFILE))));
            }else{
                    replaceFragment(R.id.container_body, ProfileFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_PROFILE));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_control:
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_CONTROL)) != null) {
                     replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_CONTROL))));
                }else{
                    replaceFragment(R.id.container_body,  ControlPanelFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_CONTROL));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_settings:
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_SETTONGS)) != null) {
                     replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_SETTONGS))));

                }else{
                    replaceFragment(R.id.container_body,  SettingsFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_SETTONGS));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_about_us:
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_ABOUTUS)) != null) {
                     replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_ABOUTUS))));
                }else{

                    replaceFragment(R.id.container_body,  AboutUSFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_ABOUTUS));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_notification:
                if (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_ABOUTUS)) != null) {
                    replaceFragment(R.id.container_body, (fragmentManager.findFragmentByTag(String.valueOf(Constants.NavigationConstants.ACTION_ABOUTUS))));
                }else{

                    replaceFragment(R.id.container_body,  NotificationFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_ABOUTUS));
                }
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_logout:
                String data = helper.getEmployeeName("name");
                helper.delete(data);
                MainRegistration.startActivity(MainActivity.this);
                break;
            case R.id.action_book_table:
                replaceFragment(R.id.container_body,  BookTableFragemnt.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_BOOK_TABLE));
                mDrawerLayout.closeDrawers();
                break;

            case R.id.action_get_book_table:
                replaceFragment(R.id.container_body,  GetAllBookTableFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_GET_BOOK_TABLE));
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_get_user_book_table:
                replaceFragment(R.id.container_body,  UsersBookTables.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_USERS_BOOK_TABLE));
                mDrawerLayout.closeDrawers();
                break;
            case R.id.action_all_sales_done:
                replaceFragment(R.id.container_body,  GetAllOrderDeletedFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_DELETE_ORDER));
                mDrawerLayout.closeDrawers();
                break;
        }

    }

    @Override
    protected void initializeViews() {
        FirebaseApp.initializeApp(getApplicationContext());
        startService(new Intent(this, FCMRegisterationService.class));
        helper = new myDbAdapter(this);
        isAdmin = AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,MainActivity.this,false);
        presenter = new PresenterMainActivity(this,this);
        toolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(R.string.home);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer);
        actionBarDrawerToggle= new ActionBarDrawerToggle(MainActivity.this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
                {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                }else
                {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView=(NavigationView)findViewById(R.id.nav);
        View nav_view=navigationView.inflateHeaderView(R.layout.header);
        Navusername=(TextView)nav_view.findViewById(R.id.nav_user_full_name);
        Navusername.setText(helper.getEmployeeName("name"));
        if(AppPreferences.getBoolean(Constants.AppPreferences.IS_ADMIN,MainActivity.this,false))
        {
            controlMenuView("1",R.id.action_control,"",true);
            controlMenuView("1",R.id.action_add_menu_item,"",true);
            controlMenuView("1",R.id.action_add_ads_item,"",true);
            controlMenuView("2",R.id.action_notification,"Order Notification",true);
            controlMenuView("1",R.id.action_get_book_table,"",true);
            controlMenuView("1",R.id.action_get_user_book_table,"",false);
            controlMenuView("1",R.id.action_all_sales_done,"",true);
        }else{
            controlMenuView("2",R.id.action_notification,"My Orders",true);
            controlMenuView("1",R.id.action_add_menu_item,"",false);
            controlMenuView("1",R.id.action_add_ads_item,"",false);
            controlMenuView("1",R.id.action_control,"",false);
            controlMenuView("1",R.id.action_get_book_table,"",false);
            controlMenuView("1",R.id.action_get_user_book_table,"",true);
            controlMenuView("1",R.id.action_all_sales_done,"",false);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);
                return false;
            }
        });

        presenter.performCheckIsBlocked(AppPreferences.getString(Constants.AppPreferences.USER_KEY,MainActivity.this,""));
        replaceFragment(R.id.container_body, HomeFragment.newInstance(), String.valueOf(Constants.NavigationConstants.ACTION_HOME));
    }

    @Override
    protected void setListeners() {

    }

    private void controlMenuView(String type,int id,String value,boolean visibility)
    {

        Menu nav_Menu = navigationView.getMenu();
        if (type.equals("1")){
            nav_Menu.findItem(id).setVisible(visibility);

        }else{
            nav_Menu.findItem(id).setTitle(value);

        }
    }

    @Override
    public void onFinished(LoginResponse loginResponse) {
        if (loginResponse.user.is_bloked.equals("1")){
            String data = helper.getEmployeeName("name");
            helper.delete(data);
            MainRegistration.startActivity(MainActivity.this);
        }
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void showProgress() {


    }

    @Override
    public void hideProgress() {

    }
}
