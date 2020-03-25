package com.android.thresturent.Ui.Fragments.GetAllOrderDeleted;

import com.android.thresturent.common.model.MyOrderResponse;

public interface GetAllDeletedRequestContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String str);
            void onFailuer(Throwable t);
            void loadRequestItemList(MyOrderResponse myOrderResponse);

        }
    }
    interface View{
        void showProgress();
        void hideProgress();
    }
    interface Presenter{

        void performGetAllRequestItem();
        void performDeleteForEverItem(String requestId);


    }
}
