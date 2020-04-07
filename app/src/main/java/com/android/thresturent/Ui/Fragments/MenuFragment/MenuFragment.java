package com.android.thresturent.Ui.Fragments.MenuFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class MenuFragment extends BaseFragment implements MenuItemContract.Model.onFinishedListener,MenuItemContract.View , MenuItemAdapter.MenuInterAction {


    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 121;
    PresenterManuItem presenter;
    private List<MenuItem> menuItemList;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout srlList;
    private String userType;
    private int count=0;
    private double price = 0.0;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    private double lat,lang;
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
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
                    AppPreferences.getString(Constants.AppPreferences.USER_TOKEN,getActivity(),""),"","2",String.valueOf(count),String.valueOf(price),String.valueOf(lat),String.valueOf(lang));

        }else{

            Custome_dialogs_request custome_dialogs_request = new Custome_dialogs_request();
            custome_dialogs_request.showDialog(getActivity(),presenter,String.valueOf(menuItem.getId()),count,price,lat,lang);
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

    @Override
    public void missOnCountOfOrder(String s) {
        Message.message(getActivity(),s);
    }

    @Override
    public void getCountNumber(int count,double price) {
        this.count = count;
        this.price = price;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // Permission denied.

                // Notify the user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the user for permission (device policy or "Never ask
                // again" prompts). Therefore, a user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.

            }
        }
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");


        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();

//                            location=String.format(Locale.ENGLISH, "%s%f",
//                                    "",
//                                    mLastLocation.getLatitude())+String.format(Locale.ENGLISH, "%s,%f",
//                                    "",
//                                    mLastLocation.getLongitude());


                            // Message.message(getActivity(),"hello from " +String.valueOf(mLastLocation.getLatitude())+String.valueOf(mLastLocation.getLongitude()));

                            lat = mLastLocation.getLatitude();
                            lang = mLastLocation.getLongitude();


                        } else {
                            Log.w(TAG, "getLastLocation:exception", task.getException());

                        }
                    }
                });
    }


}
