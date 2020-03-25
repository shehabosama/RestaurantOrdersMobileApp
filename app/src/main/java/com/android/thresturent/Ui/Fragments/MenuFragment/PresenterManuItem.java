package com.android.thresturent.Ui.Fragments.MenuFragment;
import android.content.Context;
import android.text.TextUtils;

import com.android.thresturent.common.model.MainResponse;
import com.android.thresturent.common.model.MenuItem;

import com.android.thresturent.common.model.AllItemRsponse;
import com.android.thresturent.common.network.WebService;
import com.google.firebase.FirebaseApp;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterManuItem implements MenuItemContract.Presenter {
    MenuItemContract.View view;
    MenuItemContract.Model.onFinishedListener model;

    public PresenterManuItem(Context context, MenuItemContract.View view, MenuItemContract.Model.onFinishedListener model) {
        this.view = view;
        this.model = model;
        FirebaseApp.initializeApp(context);
    }

    @Override
    public void performGetAllMenuItem() {

        view.showProgress();

        WebService.getInstance(false).getApi().getAllMenuItem().enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                AllItemRsponse allItemRsponse = new AllItemRsponse();
                allItemRsponse.setMenuItems(response.body());
                model.loadAllItemList(allItemRsponse);
               // model.onFinished(response.body().toString());
                view.hideProgress();

            }
            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                model.onFailuer(t);
                view.hideProgress();
            }
        });
    }

    @Override
    public void performDeleteMenuItem(String id, String itemImage) {

        view.showProgress();
        if(TextUtils.isEmpty(id)||TextUtils.isEmpty(itemImage)){
            model.onFinished("Something Went Wrong...");
            view.hideProgress();
        }else{
            WebService.getInstance(false).getApi().deleteOrderItem(Integer.parseInt(id),itemImage).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    model.onFinished(response.body().message);
                    view.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    view.hideProgress();
                }
            });
        }

    }

    @Override
    public void performOrderRequest(String id, String userId, String notificationToken,String location,String user_type) {
        view.showProgress();
        WebService.getInstance(false).getApi().addOrderRequest(Integer.parseInt(id),Integer.parseInt(userId),notificationToken,location,user_type).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                model.onFinished(response.body().message);
                view.hideProgress();
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {
                view.hideProgress();
            }
        });
    }

    @Override
    public void performRateOrder(String id,String rate) {
        view.showProgress();

        WebService.getInstance(false).getApi().rateItem(rate,Integer.parseInt(id)).enqueue(new Callback<MainResponse>() {
            @Override
            public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                model.onFinished(response.body().message);
                view.hideProgress();
            }

            @Override
            public void onFailure(Call<MainResponse> call, Throwable t) {

            }
        });

    }


}
