package com.android.thresturent.Ui.Fragments.UsersBookTablesFragment;

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
        void performGetAllBookTable(String userId);
    }

}
