package com.android.thresturent.Ui.Fragments.AddAdsItemFragment;

import android.content.Context;
import android.net.Uri;

import com.android.thresturent.common.model.UserListResponse;

public interface AddAdsItemContract {

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


        void performAddAdsItem(Context context, Uri uri,String userId, String itemDescription);
    }
}
