package com.android.thresturent.Ui.Fragments.BookTableFragment;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.thresturent.R;
import com.android.thresturent.common.base.BaseFragment;
import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.helper.Message;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookTableFragemnt extends BaseFragment implements BookContract.Model.onFinishedListener,BookContract.View{


    public BookTableFragemnt() {
        // Required empty public constructor
    }

    private EditText editTextPersonCount,textDate,textTime;
    private ImageView imageTime, imageDate;
    private PresenterBookTable presenter;
    private TextView btnUpload;
    private ProgressDialog progressDialog;
    private Calendar c;
    public static BookTableFragemnt newInstance() {
        return new BookTableFragemnt();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_table_fragemnt, container, false);

        initializeViews(view);
        setListeners();
        return view;
    }

    @Override
    protected void initializeViews(View v) {
        presenter = new PresenterBookTable(this,this);
        editTextPersonCount = v.findViewById(R.id.txt_person_count);
        imageDate = v.findViewById(R.id.image_date);
        imageTime = v.findViewById(R.id.image_time);
        textDate = v.findViewById(R.id.txt_date);
        textTime = v.findViewById(R.id.txt_time);
        btnUpload = v.findViewById(R.id.update);
        progressDialog = new ProgressDialog(getActivity());
        textTime.setEnabled(false);
        textDate.setEnabled(false);
         c = Calendar.getInstance();


    }

    @Override
    protected void setListeners() {
        imageDate.setOnClickListener(imageDateListener);
        imageTime.setOnClickListener(imageTimeListener);
        btnUpload.setOnClickListener(btnUploadListener);
    }

    private View.OnClickListener btnUploadListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.performBookTable(AppPreferences.getString(Constants.AppPreferences.USER_KEY, getActivity(),"0"),
                    textDate.getText().toString(),
                    textTime.getText().toString(),
                    AppPreferences.getString(Constants.AppPreferences.USER_TOKEN,getActivity(),""),
                    editTextPersonCount.getText().toString());
        }
    };
    private View.OnClickListener imageDateListener = new View.OnClickListener() {

        public void setReturnDate(int year, int month, int day) {
            datePicked(year, month, day);
        }

        @Override
        public void onClick(View v) {
            Dialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                    .get(Calendar.DAY_OF_MONTH));
            datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    setReturnDate(((DatePickerDialog) dialog).getDatePicker().getYear(),
                            ((DatePickerDialog) dialog).getDatePicker().getMonth(), ((DatePickerDialog) dialog)
                                    .getDatePicker().getDayOfMonth());
                }
            });

            datePickerDialog.show();
        }
    };
    @SuppressLint("SetTextI18n")
    public void datePicked(int year, int month, int day) {
        textDate.setText(String.valueOf(year) + "/" +
                String.valueOf(month) + "/" + String.valueOf(day));
    }
    private View.OnClickListener imageTimeListener = new View.OnClickListener() {
        public void setResaurantTime(int hour ,int minute){
            timePicked(hour,minute);
        }
        @Override
        public void onClick(View v) {
            TimePickerDialog dialog= new TimePickerDialog(new ContextThemeWrapper(getActivity(), R.style.AppTheme), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    setResaurantTime(hourOfDay,minute);
                }
            }, c.get(Calendar.HOUR), c.get(Calendar.MINUTE), DateFormat.is24HourFormat(getActivity()));
            dialog.show();
        }
    };

    @SuppressLint("SetTextI18n")
    public void timePicked(int hour, int minute) {
        textTime.setText(String.valueOf(hour) + ":" +
                String.valueOf(minute));
    }

    @Override
    public void onFinished(String result) {
        Message.message(getActivity(),result);
    }

    @Override
    public void onFailuer(Throwable t) {

    }

    @Override
    public void showProgress() {
        progressDialog.setTitle("wait minuet..");//title which will show  on the dialog box
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();// turn on the dialog box
    }

    @Override
    public void hideProgress() {

        progressDialog.dismiss();
    }
}