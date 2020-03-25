package com.android.thresturent.Ui.Acivities.Login;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.model.LoginResponse;
import com.android.thresturent.common.model.User;
import com.android.thresturent.common.model.table.Table;
import com.android.thresturent.common.network.WebService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class PresenterLogin implements LoginContract.Presenter {

    private LoginContract.View mLoginView;
    private Context context;
    private String token;
    private LoginContract.Model.onFinishedListener mModel;
    PresenterLogin(LoginContract.View mLoginView , Context context,LoginContract.Model.onFinishedListener mModel) {
        this.mLoginView = mLoginView;
        this.context = context;
        this.mModel =mModel;

    }


    @Override
    public void performLogin(final String email, final String password) {

        mLoginView.showProgress();
        if(TextUtils.isEmpty(email))
        {
            mLoginView.loginValidations();
        }else if (TextUtils.isEmpty(password))
        {
               mLoginView.loginValidations();
        }else if(!isEmailValid(email)){
            mLoginView.emailInvalid();
        }else
        {
            final User user = new User();
            user.email = email;
            user.password = password;

            WebService.getInstance(false).getApi().loginUser(user).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    assert response.body() != null;
                    if (response.body().status == 0) {
                        mLoginView.loginError();

                    } else if (response.body().status == 1) {
                        user.username = response.body().user.username;
                        user.id = Integer.parseInt(response.body().user.id);
                        user.isAdmin = response.body().user.is_admin.equals("1");
                        user.isBlocked = response.body().user.is_bloked.equals("1");
                        user.location = response.body().user.location;
                        Log.e(TAG, "onResponse: "+response.body().user.location );
                        if(user.isBlocked){
                            mModel.onFinished("Sorry For this but you Are blocked from Management");
                            mLoginView.hideProgress();
                        }else{
                            mLoginView.setUserInfo(user);
                            mLoginView.loginSuccess();
                            mLoginView.hideProgress();

                            AppPreferences.setString(Constants.AppPreferences.USER_TYPE,"user",context);
                            AppPreferences.setString(Constants.AppPreferences.USER_KEY,String.valueOf(user.id),context);
                            getUserToken();

                            if (user.isAdmin) {
                                AppPreferences.setBoolean(Constants.AppPreferences.IS_ADMIN,true,context);
                            } else {
                                AppPreferences.setBoolean(Constants.AppPreferences.IS_ADMIN, false,context);
                            }
                        }


                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                    mLoginView.loginError();
                    mLoginView.hideProgress();
                    mModel.onFailuer(t);
                }
            });
        }
    }

    @Override
    public void performTableLogin(String tableNo, String password) {

        mLoginView.showProgress();
        if(TextUtils.isEmpty(tableNo))
        {
            mLoginView.loginValidations();
            mLoginView.hideProgress();
        }else if (TextUtils.isEmpty(password))
        {
            mLoginView.loginValidations();
            mLoginView.hideProgress();
        }else {
            final Table table = new Table();
            table.table = "table";
            table.table_no = Integer.parseInt(tableNo);
            table.password = password;

            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {

                    if (task.isSuccessful())
                    {

                        token = task.getResult().getToken();
                        table.user_token = token;
                        AppPreferences.setString(Constants.AppPreferences.USER_TOKEN,token,context);


                        WebService.getInstance(false).getApi().loginTable(table).enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                Log.e(TAG, "performTableLogin: "+response.body().message );
                                if (response.body().status == 1) {
                                    mLoginView.loginSuccess();
                                    mLoginView.hideProgress();
                                    AppPreferences.setString(Constants.AppPreferences.USER_KEY,String.valueOf(table.table_no),context);
                                    AppPreferences.setBoolean(Constants.AppPreferences.IS_ADMIN,false,context);
                                }
                            }
                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {

                                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                                mLoginView.loginError();
                                mLoginView.hideProgress();
                                mModel.onFailuer(t);
                            }
                        });
                    }else{

                        mLoginView.hideProgress();
                    }
                }

            });

        }
    }

    private void getUserToken(){

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if (task.isSuccessful())
                {
                    token = task.getResult().getToken();
                    AppPreferences.setString(Constants.AppPreferences.USER_TOKEN,token,context);
                }
            }

        });

    }
    private boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);


        return matcher.matches();
    }






}
