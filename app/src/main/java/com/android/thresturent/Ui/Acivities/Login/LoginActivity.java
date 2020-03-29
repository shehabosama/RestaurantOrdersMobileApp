package com.android.thresturent.Ui.Acivities.Login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.thresturent.Ui.Acivities.MainActivity.MainActivity;
import com.android.thresturent.R;
import com.android.thresturent.Ui.Acivities.Register.RegisterActivity;
import com.android.thresturent.common.SqlHelper.myDbAdapter;
import com.android.thresturent.common.base.BaseActivity;
import com.android.thresturent.common.helper.AppPreferences;
import com.android.thresturent.common.helper.Constants;
import com.android.thresturent.common.helper.Message;
import com.android.thresturent.common.model.User;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class LoginActivity extends BaseActivity implements LoginContract.View , LoginContract.Model.onFinishedListener{
    private static final int MY_PERMISSIONS_REQUEST_CAMERA =1000 ;
    private String user_type;
    private Button btnLogin;
    private EditText editTextEmail,editTextPassword;
    private TextView textViewRegister;
    private ProgressDialog progressDialog;
    private myDbAdapter helper;
    private PresenterLogin presenter;
    private ImageView imageView;
    public static void startActivity(Context context,String user_type){
        context.startActivity(new Intent(context,LoginActivity.class).putExtra(Constants.BundleKeys.USER_TYPE_KEY,user_type));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activy);
        initializeViews();
        setListeners();
    }
    @Override
    protected void initializeViews() {
        user_type = getIntent().getExtras().getString(Constants.BundleKeys.USER_TYPE_KEY).toString();
        btnLogin = findViewById(R.id.btn_login);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
        progressDialog = new ProgressDialog(this);
        helper = new myDbAdapter(this);
        presenter = new PresenterLogin(this,this,this);
        imageView = findViewById(R.id.scan_password);
        editTextPassword.setEnabled(true);
        editTextEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        if (user_type.equals("table")){
            imageView.setVisibility(View.VISIBLE);
            editTextPassword.setEnabled(false);
            editTextEmail.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this,
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);
            }
        } else {
        }


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Message.message(LoginActivity.this,"Permission granted");
                } else {
                    Message.message(LoginActivity.this,"permission denied,");
                }
                return;
            }
        }
    }
    private void initiateScan() {
        IntentIntegrator integrator = new IntentIntegrator(LoginActivity.this);
        integrator.setBeepEnabled(true)
                  .setPrompt("please scan qr code in the table")
                  .initiateScan();
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

    @Override
    public void loginValidations() {
        Message.message(LoginActivity.this,"pleas fill all the flied");
    }

    @Override
    public void loginSuccess() {
        Message.message(LoginActivity.this,"login successfully");
        hideProgress();
        startActivity(new Intent(getApplicationContext(), MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    @Override
    public void loginError() {
        hideProgress();
        Message.message(LoginActivity.this,"Login Failure");
    }

    @Override
    public void emailInvalid() {
        hideProgress();
        Message.message(LoginActivity.this,"Please Make Sure From Your Email");
    }

    @Override
    public void setUserInfo(User userInfo) {
        helper.insertData(userInfo.username,editTextPassword.getText().toString(),editTextEmail.getText().toString(),String.valueOf(userInfo.id),userInfo.location);
    }
    @Override
    protected void setListeners() {
        btnLogin.setOnClickListener(btnLoginListener);
           textViewRegister.setOnClickListener(textViewRegisterListener);
        imageView.setOnClickListener(imageViewListenert);
    }
    View.OnClickListener btnLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(user_type.equals("table")){
                Toast.makeText(LoginActivity.this, "test", Toast.LENGTH_SHORT).show();
                presenter.performTableLogin(editTextEmail.getText().toString(),editTextPassword.getText().toString());
                AppPreferences.setString(Constants.AppPreferences.USER_TYPE,"table", LoginActivity.this);

            }else {
                presenter.performLogin(editTextEmail.getText().toString(), editTextPassword.getText().toString());
            }
        }
    };
    View.OnClickListener textViewRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RegisterActivity.startActivity(LoginActivity.this);
        }
    };


    private View.OnClickListener imageViewListenert = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initiateScan();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            String re = scanResult.getContents();
            editTextPassword.setText(re);

        }

    }

    @Override
    public void onFinished(String str) {
        Message.message(LoginActivity.this,str);
    }

    @Override
    public void onFailuer(Throwable t) {
        Message.message(LoginActivity.this,t.getLocalizedMessage());
    }
}


