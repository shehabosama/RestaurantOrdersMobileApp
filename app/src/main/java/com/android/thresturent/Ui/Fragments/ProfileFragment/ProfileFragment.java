package com.android.thresturent.Ui.Fragments.ProfileFragment;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.thresturent.R;
import com.android.thresturent.common.SqlHelper.myDbAdapter;
import com.android.thresturent.common.base.BaseFragment;
import com.android.thresturent.common.helper.Message;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends BaseFragment implements ProfileContract.Model.onFinishedListener,ProfileContract.View {


    public ProfileFragment() {
        // Required empty public constructor
    }
    private TextView textViewName,textViewEmail,textViewAddress,report,support;
    private myDbAdapter myDbAdapter;
    private ProgressDialog progressDialog;
    private PresenterProfile presenterProfile;

    public static ProfileFragment newInstance(){
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeViews(view);
        setListeners();
        return view ;
    }

    @Override
    protected void initializeViews(View v) {
        textViewEmail = v.findViewById(R.id.email);
        textViewName = v.findViewById(R.id.username);
        textViewAddress = v.findViewById(R.id.address);
        report = v.findViewById(R.id.get_help_text);
        support = v.findViewById(R.id.support);
        myDbAdapter = new myDbAdapter(getActivity());
        textViewAddress.setText(myDbAdapter.getEmployeeName("address"));
        textViewEmail.setText(myDbAdapter.getEmployeeName("email"));
        textViewName.setText(myDbAdapter.getEmployeeName("name"));
        progressDialog = new ProgressDialog(getActivity());
        presenterProfile = new PresenterProfile(this,this);
    }

    @Override
    protected void setListeners() {
        report.setOnClickListener(reportListner);
        support.setOnClickListener(reportListner);
    }

    private View.OnClickListener reportListner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Custome_dialogs_reports custome_dialogs_reports = new Custome_dialogs_reports();
            custome_dialogs_reports.showDialog(getActivity(),presenterProfile);
        }
    };


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
