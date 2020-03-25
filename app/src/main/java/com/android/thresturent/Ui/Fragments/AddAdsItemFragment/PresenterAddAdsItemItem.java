package com.android.thresturent.Ui.Fragments.AddAdsItemFragment;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.thresturent.common.helper.FileUtil;
import com.android.thresturent.common.model.MainResponse;
import com.android.thresturent.common.network.WebService;

import java.io.File;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterAddAdsItemItem implements AddAdsItemContract.Presenter{
    AddAdsItemContract.View view;
    AddAdsItemContract.Model.onFinishedListener model;

    public PresenterAddAdsItemItem(AddAdsItemContract.View view, AddAdsItemContract.Model.onFinishedListener model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void performAddAdsItem(Context context, Uri uri,String userId,String itemDescription) {
        view.showProgress();
            if(uri == null){
                view.validation("Pleas Select the photo");
            }else{
                String cookie = "cookiehere";
                String stringValue = "stringValue";
                File file = new File( FileUtil.getPath(uri,context));
                final RequestBody requestFile =
                        RequestBody.create(
                                MediaType.parse("image/jpg"),
                                file
                        );
                String items = "[1,2,4]";
                Random rand = new Random();
                int value = rand.nextInt(50);
                String filename =String.valueOf(value)+file.getName();
                MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", filename, requestFile);

                RequestBody items1 = RequestBody.create(MediaType.parse("application/json"), items);
                RequestBody stringValue1 = RequestBody.create(MediaType.parse("text/plain"), stringValue);
                WebService.getInstance(true).getApi().addAdsItem(cookie, body, items1, stringValue1,Integer.parseInt(userId),itemDescription,filename).enqueue(new Callback<MainResponse>() {
                    @Override
                    public void onResponse(Call<MainResponse> call, Response<MainResponse> response) {
                        if (response.body().status == 2){
                            model.onFinished(response.body().message);
                            view.hideProgress();
                        } else {
                            model.onFinished(response.body().message);
                            view.hideProgress();
                        }
                    }

                    @Override
                    public void onFailure(Call<MainResponse> call, Throwable t) {
                        model.onFailuer(t);
                        view.hideProgress();
                        Log.e(TAG, "onResponse: "+t.getLocalizedMessage());
                    }
                });
            }



    }

}
