package com.android.thresturent.Ui.Fragments.MenuFragment;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.thresturent.R;
import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Message;

public class Custome_dialogs_rate {


    RatingBar ratingBar;
    Button btnRate;
    Activity activity;
    String rate;
    TextView textRateDone;
    ImageView rateDone;

    PresenterManuItem presenter;
    public void showDialog(final Activity activity , final PresenterManuItem presenter , final String itemId, final String user_id){
        this.presenter = presenter;
        this.activity = activity;

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       //  dialog.getWindow().setBackgroundDrawableResource(R.drawable.empty);
        // dialog.setCancelable(false);

        dialog.setContentView(R.layout.custome_dialoge_rate);
        dialog.show();

        ratingBar = dialog.findViewById(R.id.rating);
        btnRate = dialog.findViewById(R.id.btn_rate);
        textRateDone = dialog.findViewById(R.id.text_rate_done);
        rateDone = dialog.findViewById(R.id.rate_done);
        if(AppPreferences.getBoolean(user_id+itemId,activity,false)){
            ratingBar.setVisibility(View.GONE);
            btnRate.setVisibility(View.GONE);
            rateDone.setVisibility(View.VISIBLE);
            textRateDone.setVisibility(View.VISIBLE);
        }else{
            ratingBar.setVisibility(View.VISIBLE);
            btnRate.setVisibility(View.VISIBLE);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
               if(rating == 1.0){
                   rate = "very_bad";
               }else if(rating == 1.5){
                   rate = "bad";
               }else if(rating == 2.0){
                   rate = "acceptable";
               }else if(rating == 2.5){
                   rate = "good";
               }else if(rating == 3.0){
                   rate = "very_good";
               }else if(rating == 3.5){
                   rate = "excellent";
               }else if(rating == 4.0){
                   rate = "very_excellent";
               }else if(rating == 4.5){
                   rate = "awesome";
               }else if(rating == 5.0){
                   rate = "very_awesome";
               }
            }
        });

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(rate)){
                    Message.message(activity,"make sure that you rated");
                }else{


                    presenter.performRateOrder(itemId,rate);
                    AppPreferences.setBoolean(user_id+itemId,true,activity);
                    ratingBar.setVisibility(View.GONE);
                    btnRate.setVisibility(View.GONE);
                    rateDone.setVisibility(View.VISIBLE);
                    textRateDone.setVisibility(View.VISIBLE);
                }

            }
        });

    }

}
