package com.android.thresturent.Ui.Fragments.HomeFragment;

import com.android.thresturent.Ui.Acivities.Register.RegisterContract;
import com.android.thresturent.common.model.Advertising;
import com.android.thresturent.common.model.LoginResponse;

import java.util.List;

public interface HomeContract {
    interface Model{
    interface onFinishedListener {
        void onFinished(LoginResponse loginResponse);
        void onFailuer(Throwable t);
        void loadAds(List<Advertising> advertisings);
    }
    void Login(RegisterContract.Model.onFinishedListener onFinishedListener, String email, String password);
}
interface View {
    void showProgress();

    void hideProgress();
}
interface Presenter{
    void performGetAds();
}

}
