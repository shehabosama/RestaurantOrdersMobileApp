package com.android.thresturent.Ui.Fragments.ProfileFragment;

public interface ProfileContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void onFailuer(Throwable t);

        }
    }

    interface View {
        void showProgress();
        void hideProgress();
    }

    interface Presenter {
        void performSendReport(String userId,String reportDescription);
    }

}
