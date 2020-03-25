package com.android.thresturent.Ui.Fragments.ProfileFragment;

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

public class Custome_dialogs_reports {
    Activity activity;
    PresenterProfile presenter;
    EditText editTextReport;
    Button btnSendReport;
    public void showDialog(final Activity activity , final PresenterProfile presenter ){
        this.presenter = presenter;
        this.activity = activity;

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       //  dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);

        dialog.setContentView(R.layout.custome_dialoge_reports);
        dialog.show();
        editTextReport = dialog.findViewById(R.id.editTextReportDescription);
        btnSendReport = dialog.findViewById(R.id.btn_sen_report);
        btnSendReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(editTextReport.getText().toString())){
                    Message.message(activity,"Please Write the report");
                }else{
                    presenter.performSendReport(AppPreferences.getString(Constants.AppPreferences.USER_KEY,activity,"0"),editTextReport.getText().toString());
                }

            }
        });

    }

}
