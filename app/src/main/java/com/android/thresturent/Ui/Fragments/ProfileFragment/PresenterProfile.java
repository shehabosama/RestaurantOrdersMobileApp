package com.android.thresturent.Ui.Fragments.ProfileFragment;

import android.text.TextUtils;
import android.util.Log;

import com.android.thresturent.common.model.MainResponse;
import com.android.thresturent.common.network.WebService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterProfile implements ProfileContract.Presenter {
    private ProfileContract.View mView;
    private ProfileContract.Model.onFinishedListener mModel;

    public PresenterProfile(ProfileContract.Model.onFinishedListener mModel, ProfileContract.View mView) {
        this.mView = mView;
        this.mModel = mModel;
    }


    @Override
    public void performSendReport(String userId, String reportDescription) {
        mView.showProgress();
        if(TextUtils.isEmpty(userId)||TextUtils.isEmpty(reportDescription)){
            mModel.onFinished("Something Went Wrong");
            mView.hideProgress();
        }else{
            WebService.getInstance(true).getApi().AddReports(Integer.parseInt(userId),reportDescription).enqueue(new Callback<MainResponse>() {
                @Override
                public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                    mModel.onFinished(response.body().message);
                    mView.hideProgress();
                }

                @Override
                public void onFailure(Call<MainResponse> call, Throwable t) {
                    Log.e(TAG, "onFailure: "+t.getMessage() );
                    mView.hideProgress();
                }
            });
        }
    }
}