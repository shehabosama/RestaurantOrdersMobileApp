package com.android.thresturent.Ui.Fragments.NotificationFragment;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.thresturent.R;

public class Custome_dialogs_Delete{

    Button btnDelete,btnCancel;

    Activity activity;


    PresenterNotification presenter;
    public void showDialog(final Activity activity , final PresenterNotification presenter , final String itemId, final String userId, final String isDeleted, final String requestId){
        this.presenter = presenter;
        this.activity = activity;

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       //  dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custome_dialoge);
        dialog.show();

        btnDelete = dialog.findViewById(R.id.delete);
        btnCancel = dialog.findViewById(R.id.dismiss);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.deleteOrderItem(userId,itemId,requestId,isDeleted);
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                }
        });

    }

}
