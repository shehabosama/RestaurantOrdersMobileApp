package com.android.thresturent.Ui.Fragments.SettingsFragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.thresturent.R;
import com.android.thresturent.common.SqlHelper.myDbAdapter;
import com.android.thresturent.common.base.BaseFragment;
import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.helper.Message;


/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends BaseFragment implements SettingsContract.Model.onFinishedListener,SettingsContract.View{


    private EditText editTextName,editTextAddress,editTextPassword,editTextConfirmPass;
    private TextView btnUpdate;
    private PresenterSettings presenter;
    myDbAdapter myDbAdapter;

    ProgressDialog progressDialog;
    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initializeViews(view);
        setListeners();
        return view ;
    }

    @Override
    protected void initializeViews(View v) {
        editTextAddress = v.findViewById(R.id.txt_address);
        editTextName = v.findViewById(R.id.txt_name);
        editTextPassword =v.findViewById(R.id.txt_password);
        editTextConfirmPass = v.findViewById(R.id.txt_confirm_pass);
        btnUpdate = v.findViewById(R.id.update);
        myDbAdapter = new myDbAdapter(getActivity());
        presenter = new PresenterSettings(this,this,myDbAdapter);


        editTextName.setText(myDbAdapter.getEmployeeName("name"));
        editTextAddress.setText(myDbAdapter.getEmployeeName("address"));
        progressDialog = new ProgressDialog(getActivity());

    }

    @Override
    protected void setListeners() {

        btnUpdate.setOnClickListener(btnUpdateListener);
    }

    private View.OnClickListener btnUpdateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(TextUtils.isEmpty(editTextPassword.getText().toString())){
                presenter.upadteInformation(AppPreferences.getString(Constants.AppPreferences.USER_KEY,getActivity(),""),editTextName.getText().toString(),editTextAddress.getText().toString(),myDbAdapter.getEmployeeName("email"));

            }else{
                 if (!editTextPassword.getText().toString().equals(editTextConfirmPass.getText().toString())){
                    Message.message(getActivity(),"Make sure the password matches");
                }else{
                     presenter.updateInformationWithPass(AppPreferences.getString(Constants.AppPreferences.USER_KEY,getActivity(),""),editTextName.getText().toString(),editTextAddress.getText().toString(),editTextPassword.getText().toString(),myDbAdapter.getEmployeeName("email"));

                 }
            }

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
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Please Wait while Updating Your changes");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }
}
