package com.android.thresturent.Ui.Acivities.Login;

import com.android.thresturent.common.model.User;

public interface LoginContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String str);
            void onFailuer(Throwable t);
        }
        void Login(onFinishedListener onFinishedListener,String email,String password);


    }
    interface View{
        void showProgress();
        void hideProgress();
        void loginValidations();
        void loginSuccess();
        void loginError();
        void emailInvalid();
        void setUserInfo(User userInfo);

    }
    interface Presenter{
        void performLogin(String email,String password);
        void performTableLogin(String tableNo,String password);

    }
}
