package com.android.thresturent.Ui.Fragments.AddMenuItemFragment;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.thresturent.R;
import com.android.thresturent.common.base.BaseFragment;
import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.helper.Message;
import com.android.thresturent.common.model.UserListResponse;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMenuItemFragment extends BaseFragment implements AddMenuItemContract.Model.onFinishedListener,AddMenuItemContract.View{

    private static final int CAMERA_REQUEST_CODE = 131;
    private static final int PICK_FROM_GALLERY = 141;
    private Button uploadBtn;
    private ImageView selectImage;
    private EditText itemName,itemDescription,itemPrice;
    private Context context;
    private Uri uri;
    PresenterAddMenuItem presenter;
    ProgressDialog progressDialog;
    String userType;
    public AddMenuItemFragment() {
        // Required empty public constructor
    }


    public static AddMenuItemFragment newInstance(){
        return new AddMenuItemFragment();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_menu_item_fragement, container, false);
        initializeViews(view);
        setListeners();
        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                uri = data.getData();
                selectImage.setImageURI(uri);

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    //do something like displaying a message that he didn`t allow the app to access gallery and you wont be able to let him select from gallery
                }
                break;


        }
    }

    @Override
    protected void initializeViews(View v) {
        context = getActivity();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
        }

        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        }
         userType = AppPreferences.getString(Constants.AppPreferences.USER_TYPE,context,"t");

        uploadBtn = v.findViewById(R.id.btn_upload_item);
        selectImage = v.findViewById(R.id.image_select);
        itemName = v.findViewById(R.id.edit_text_item_name);
        itemDescription = v.findViewById(R.id.edit_text_item_description);
        itemPrice = v.findViewById(R.id.edit_text_item_price);
        presenter = new PresenterAddMenuItem(this,this);
        progressDialog = new ProgressDialog(getActivity());
    }

    @Override
    protected void setListeners() {
        uploadBtn.setOnClickListener(uploadBtnListener);
        selectImage.setOnClickListener(selectImageListener);
    }
    private View.OnClickListener selectImageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
        }
    };
    private View.OnClickListener uploadBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


                presenter.performAddMenuItem(context,uri,itemName.getText().toString(),itemDescription.getText().toString(),itemPrice.getText().toString());


        }
    };

    @Override
    public void showProgress() {
        progressDialog.setTitle("Please Wait While Uploading the Menu Item");
        progressDialog.setMessage("login now...");//message which will show  on the dialog box
        progressDialog.setCancelable(false);// not allow the user to cancel the dialog box even done the process
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void validation(String str) {
        Message.message(context,str);
    }

    @Override
    public void onFinished(String str) {
        Log.e(TAG, "onFinished: "+str );
    }

    @Override
    public void onFailuer(Throwable t) {
        Log.e(TAG, "onFinished: "+t.getLocalizedMessage() );
    }

    @Override
    public void loadDepartmentList(UserListResponse userListResponse) {

    }
}
