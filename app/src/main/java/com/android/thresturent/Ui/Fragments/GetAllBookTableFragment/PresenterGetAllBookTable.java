package com.android.thresturent.Ui.Fragments.GetAllBookTableFragment;

import android.text.TextUtils;
import android.util.Log;

import com.android.thresturent.common.model.BookTableResponse;
import com.android.thresturent.common.model.MainResponse;
import com.android.thresturent.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class PresenterGetAllBookTable implements GetAllBookContract.Presenter {
    private GetAllBookContract.View mView;
    private GetAllBookContract.Model.onFinishedListener mModel;

    public PresenterGetAllBookTable(GetAllBookContract.Model.onFinishedListener mModel, GetAllBookContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performGetAllBookTable() {
        mView.showProgress();
        WebService.getInstance(true).getApi().getAllBookTable().enqueue(new Callback<BookTableResponse>() {
            @Override
            public void onResponse(Call<BookTableResponse> call, Response<BookTableResponse> response) {
                mModel.loadAllBookTable(response.body());
                mView.hideProgress();
                Log.e(TAG, "onResponse: here" );
            }

            @Override
            public void onFailure(Call<BookTableResponse> call, Throwable t) {
                mView.hideProgress();
                Log.e(TAG, "onFailure: here"+t.getLocalizedMessage() );
            }
        });
    }

    @Override
    public void performUpdateBookStatus(String userId, String tableId, String requestId, String confirmation,String token) {
        mView.showProgress();
        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(requestId)||TextUtils.isEmpty(confirmation)||TextUtils.isEmpty(token)){
            mModel.onFinished("Something Went Wrong...");
            mView.hideProgress();
        }else if(TextUtils.isEmpty(tableId)){
            mModel.onFinished("please Select Table first");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().updateUserBookTable(Integer.parseInt(requestId),Integer.parseInt(userId),Integer.parseInt(confirmation),Integer.parseInt(tableId),token).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    mView.hideProgress();
                }
            });
        }
    }
}