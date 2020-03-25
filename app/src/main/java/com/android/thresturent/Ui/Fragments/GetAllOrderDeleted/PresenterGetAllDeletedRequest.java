package com.android.thresturent.Ui.Fragments.GetAllOrderDeleted;

import android.text.TextUtils;

import com.android.thresturent.common.model.MainResponse;
import com.android.thresturent.common.model.MyOrderResponse;
import com.android.thresturent.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterGetAllDeletedRequest implements GetAllDeletedRequestContract.Presenter {
    GetAllDeletedRequestContract.Model.onFinishedListener mModel;
    GetAllDeletedRequestContract.View mView;

    public PresenterGetAllDeletedRequest(GetAllDeletedRequestContract.Model.onFinishedListener mModel, GetAllDeletedRequestContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performGetAllRequestItem() {
        mView.showProgress();


            WebService.getInstance(false).getApi().getAllDeletedOrders().enqueue(new Callback<MyOrderResponse>() {
                @Override
                public void onResponse(Call<MyOrderResponse> call, Response<MyOrderResponse> response) {
                    mModel.loadRequestItemList(response.body());
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MyOrderResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });

    }

    @Override
    public void performDeleteForEverItem(String requestId) {
        if(TextUtils.isEmpty(requestId)){
            mModel.onFinished("SomeThing went Wrong..");
        }else{
            WebService.getInstance(true).getApi().deleteItem(Integer.parseInt(requestId)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {

                }
            });
        }
    }


}
