package com.android.thresturent.Ui.Fragments.ControlPanelFragment;

import android.content.Context;
import android.net.Uri;

import com.android.thresturent.common.model.AllItemRsponse;
import com.android.thresturent.common.model.UserListResponse;

public interface ControlContract {
    interface Model {
        interface onFinishedListener {
            void onFinished(String str);
            void onFailuer(Throwable t);
            void loadUserList(UserListResponse userListResponse);
        }
    }
    interface View{
        void showProgress();
        void hideProgress();



    }
    interface Presenter{

        void performGetAllUser();
        void performDeleteUser(String delete,int id);
        void performPermissionUser(String permission,int id,String value);
        void performBlockUser(int id,int value);
    }
}
