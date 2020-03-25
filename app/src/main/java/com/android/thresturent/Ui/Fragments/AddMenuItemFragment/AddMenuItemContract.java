package com.android.thresturent.Ui.Fragments.AddMenuItemFragment;

import android.content.Context;
import android.net.Uri;

import com.android.thresturent.common.model.UserListResponse;

public interface AddMenuItemContract {

    interface Model {
        interface onFinishedListener {
            void onFinished(String str);
            void onFailuer(Throwable t);
            void loadDepartmentList(UserListResponse userListResponse);
        }
    }
    interface View{
        void showProgress();
        void hideProgress();
        void validation(String str);
    }
    interface Presenter{


        void performAddMenuItem(Context context, Uri uri,String userName, String itemDescription,  String itemPrice);
    }
}
