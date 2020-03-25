package com.android.thresturent.Ui.Fragments.NotificationFragment;

import com.android.thresturent.common.model.AllItemRsponse;
import com.android.thresturent.common.model.MyOrder;
import com.android.thresturent.common.model.MyOrderResponse;

public interface NotificationContract {
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

        void performGetAllRequestItem(String user_id);
        void updateItemStatus(String userId ,String orderId,String requestId,String confirmation,String token);
        void deleteOrderItem(String userId, String orderId, String requestId, String isDeleted);
        void deleteItemEver(String request_id);
    }
}
