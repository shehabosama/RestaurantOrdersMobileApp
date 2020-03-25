package com.android.thresturent.Ui.Fragments.UsersBookTablesFragment;

import android.util.Log;

import com.android.thresturent.common.model.BookTableResponse;
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
    public void performGetAllBookTable(String userId) {
        mView.showProgress();
        WebService.getInstance(true).getApi().getAllBookTableUsers(Integer.parseInt(userId)).enqueue(new Callback<BookTableResponse>() {
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


}