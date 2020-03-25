package com.android.thresturent.Ui.Fragments.NotificationFragment;

import android.text.TextUtils;

import com.android.thresturent.common.model.MainResponse;
import com.android.thresturent.common.model.MyOrderResponse;
import com.android.thresturent.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterNotification implements NotificationContract.Presenter {
    NotificationContract.Model.onFinishedListener mModel;
    NotificationContract.View mView;

    public PresenterNotification(NotificationContract.Model.onFinishedListener mModel, NotificationContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void performGetAllRequestItem(String user_id) {
        mView.showProgress();

        if(TextUtils.isEmpty(user_id)){
            WebService.getInstance(false).getApi().getOrderRequest().enqueue(new Callback<MyOrderResponse>() {
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
        }else{
            WebService.getInstance(false).getApi().getOrderRequest(Integer.parseInt(user_id)).enqueue(new Callback<MyOrderResponse>() {
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
    }

    @Override
    public void updateItemStatus(String userId, String orderId, String requestId, String confirmation,String token) {
        mView.showProgress();
        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(orderId)||TextUtils.isEmpty(requestId)||TextUtils.isEmpty(confirmation)){
            mModel.onFinished("Something went Wrong..");
        }else{
            WebService.getInstance(false).getApi().updateRequestStatus(Integer.parseInt(userId),
                    Integer.parseInt(orderId),
                    Integer.parseInt(requestId),
                    Integer.parseInt(confirmation), token).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mView.hideProgress();
                    mModel.onFinished(t.getLocalizedMessage());
                }
            });
        }
    }

    @Override
    public void deleteOrderItem(String userId, String orderId, String requestId, String isDeleted) {

        mView.showProgress();
        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(orderId)||TextUtils.isEmpty(requestId)||TextUtils.isEmpty(isDeleted)){
            mModel.onFinished("Something went Wrong..");
        }else{
            WebService.getInstance(false).getApi().updateRequestIsDeleted(Integer.parseInt(userId),
                    Integer.parseInt(orderId),
                    Integer.parseInt(requestId),Integer.parseInt(isDeleted)).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mView.hideProgress();
                    mModel.onFinished(t.getLocalizedMessage());
                }
            });
        }
    }

    @Override
    public void deleteItemEver(String request_id) {
        if(TextUtils.isEmpty(request_id)){
            mModel.onFinished("SomeThing went Wrong..");
        }else{
            WebService.getInstance(true).getApi().deleteItem(Integer.parseInt(request_id)).enqueue(new Callback<MainResponse>() {
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
