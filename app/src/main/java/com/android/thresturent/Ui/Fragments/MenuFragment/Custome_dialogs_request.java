package com.android.thresturent.Ui.Fragments.MenuFragment;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.android.thresturent.R;
import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.helper.Message;

public class Custome_dialogs_request {

    Button btnDelete;
    EditText editText;

    Activity activity;


    PresenterManuItem presenter;
    public void showDialog(final Activity activity , final PresenterManuItem presenter , final String itemId){
        this.presenter = presenter;
        this.activity = activity;

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       //  dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);
        dialog.setContentView(R.layout.custome_dialoge_request_order);
        dialog.show();

        btnDelete = dialog.findViewById(R.id.orderNow);
        editText  = dialog.findViewById(R.id.editTextLocation);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editText.getText().toString())){
                    Message.message(activity,"please write your Location");
                }else if(TextUtils.isEmpty(itemId)){
                    Message.message(activity,"Something Went Wrong");
                }else {
                    presenter.performOrderRequest(itemId,
                            AppPreferences.getString(Constants.AppPreferences.USER_KEY,activity,""),
                            AppPreferences.getString(Constants.AppPreferences.USER_TOKEN,activity,""),editText.getText().toString(),"1");
                    dialog.dismiss();
                }

            }
        });


    }

}
