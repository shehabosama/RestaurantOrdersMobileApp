package com.android.thresturent.Ui.Fragments.GetAllBookTableFragment;

import com.android.thresturent.common.model.BookTableResponse;

public interface GetAllBookContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String result);
            void loadAllBookTable(BookTableResponse response);
            void onFailuer(Throwable t);

        }
    }

    interface View {
        void showProgress();

        void hideProgress();
    }

    interface Presenter {
        void performGetAllBookTable();
        void performUpdateBookStatus(String userId,String tableId,String requestId,String confirmation,String token);
    }

}
