package com.android.thresturent.Ui.Fragments.BookTableFragment;

public interface BookContract {

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
        void performBookTable(String userId,String date ,String time ,String token,String personCount);
    }

}
