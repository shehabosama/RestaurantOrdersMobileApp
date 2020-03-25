package com.android.thresturent.Ui.Acivities.MainActivity;

import com.android.thresturent.Ui.Acivities.Register.RegisterContract;
import com.android.thresturent.common.model.LoginResponse;

public interface MainContract
{
    interface Model {
        interface onFinishedListener {
            void onFinished(LoginResponse loginResponse);
            void onFailuer(Throwable t);
        }
        void Login(RegisterContract.Model.onFinishedListener onFinishedListener, String email, String password);
    }
    interface View {
        void showProgress();

        void hideProgress();
    }
    interface Presenter{
        void performCheckIsBlocked(String userId);
    }
}
