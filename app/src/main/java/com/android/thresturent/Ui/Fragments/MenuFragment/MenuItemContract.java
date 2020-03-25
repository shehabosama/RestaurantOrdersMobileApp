package com.android.thresturent.Ui.Fragments.MenuFragment;

import android.content.Context;
import android.net.Uri;

import com.android.thresturent.common.model.AllItemRsponse;
import com.android.thresturent.common.model.UserListResponse;

public interface MenuItemContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String str);
            void onFailuer(Throwable t);
            void loadAllItemList(AllItemRsponse allItemRsponse);

        }
    }
    interface View{
        void showProgress();
        void hideProgress();
    }
    interface Presenter{

      void performGetAllMenuItem();
      void performDeleteMenuItem(String id,String itemImage);
      void performOrderRequest(String id,String userId,String notificationToken,String location,String user_type);
      void performRateOrder(String id,String rate);
    }
}
