package com.android.thresturent.Ui.Fragments.BookTableFragment;

import android.text.TextUtils;

import com.android.thresturent.common.model.MainResponse;
import com.android.thresturent.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterBookTable implements BookContract.Presenter {
    private BookContract.View mView;
    private BookContract.Model.onFinishedListener mModel;

    public PresenterBookTable(BookContract.Model.onFinishedListener mModel, BookContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performBookTable(String userId, String date, String time, String token, String personCount) {
        mView.showProgress();
        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(token)){
            mModel.onFinished("Something Went Wrong...");
            mView.hideProgress();
        }else if(TextUtils.isEmpty(date)){
            mModel.onFinished("Please Select the Date..");
            mView.hideProgress();
        }else if(TextUtils.isEmpty(time)){
            mModel.onFinished("Please Select The Time..");
            mView.hideProgress();
        }else if(TextUtils.isEmpty(personCount)){
            mModel.onFinished("Please Write The Persons Count");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().addBookTable(Integer.parseInt(userId),date,time,personCount,token).enqueue(new Callback<MainResponse>() {
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