package com.android.thresturent.Ui.Fragments.HomeFragment;

import com.android.thresturent.common.model.Advertising;
import com.android.thresturent.common.network.WebService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterHome implements HomeContract.Presenter {
    HomeContract.Model.onFinishedListener mModel;
    HomeContract.View mView;

    public PresenterHome(HomeContract.Model.onFinishedListener mModel, HomeContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performGetAds() {
        mView.showProgress();
        WebService.getInstance(false).getApi().getAds().enqueue(new Callback<List<Advertising>>() {
            @Override
            public void onResponse(Call<List<Advertising>> call, Response<List<Advertising>> response) {
                mModel.loadAds(response.body());
                mView.hideProgress();
            }

            @Override
            public void onFailure(Call<List<Advertising>> call, Throwable t) {
                mView.hideProgress();
            }
        });
    }
}
