package com.winbee.vasantvalleyschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.winbee.vasantvalleyschool.Models.RefCode;
import com.winbee.vasantvalleyschool.RetrofitApiCall.ApiClient;
import com.winbee.vasantvalleyschool.Ui.Home;
import com.winbee.vasantvalleyschool.Utils.AssignmentData;
import com.winbee.vasantvalleyschool.Utils.Constants;
import com.winbee.vasantvalleyschool.Utils.ProgressBarUtil;
import com.winbee.vasantvalleyschool.Utils.SharedPrefManager;
import com.winbee.vasantvalleyschool.WebApi.ClientApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText emailSignIn,passwordSignIn;
    ProgressBar progressBar;
    TextView txt_referal,txt_forgetpassword;
    private ProgressBarUtil progressBarUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Home.class));
            return;
        }

        emailSignIn=findViewById(R.id.emailSignIn);
        passwordSignIn=findViewById(R.id.passwordSignIn);
        progressBar=findViewById(R.id.progressBarSignIn);
        txt_referal=findViewById(R.id.txt_referal);
        txt_forgetpassword=findViewById(R.id.txt_forgetpassword);
        txt_forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        progressBarUtil = new ProgressBarUtil(this);

    }

    public void LogInClicked(View view) {
        //todo add login code of firebase

//        startActivity(new Intent(Login.this, Home.class));
        userValidation();

    }

    public void GotoSignUpActivity(View view) {

    startActivity(new Intent(Login.this, SignUp.class));
}

    private void userValidation() {
        final String mobile = emailSignIn.getText().toString();
        final String password = passwordSignIn.getText().toString();
        final String referaCode = txt_referal.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(mobile)) {
            emailSignIn.setError("Please enter your mobile no");
            emailSignIn.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordSignIn.setError("Please enter your password");
            passwordSignIn.requestFocus();
            return;
        }
        RefCode refCode = new RefCode();
        refCode.setUsername(mobile);
        refCode.setPassword(password);
        refCode.setRef_code(referaCode);
        callRefCodeSignInApi(refCode);



    }

    private void callRefCodeSignInApi(final RefCode refCode){

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RefCode> call = mService.refCodeSignIn(1,refCode.getUsername(),refCode.getPassword(),refCode.getRef_code());
        call.enqueue(new Callback<RefCode>() {
            @Override
            public void onResponse(Call<RefCode> call, Response<RefCode> response) {
                int statusCode  = response.code();
                if(statusCode==200 && response.body().getOrg_Code()!=null ) {
                    progressBarUtil.hideProgress();
                  //  AssignmentData.Whatsaap=response.body().getWhatsaapNo();
                    Constants.CurrentUser = response.body();
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(Constants.CurrentUser);
                    startActivity(new Intent(Login.this, Home.class));
                    AssignmentData.Email=response.body().getEmail();
                    AssignmentData.Password=response.body().getPassword();
                    finish();
                }else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(Login.this, "Invalid UserName Password ", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<RefCode> call, Throwable t) {
                Toast.makeText(Login.this,"Failed"+t.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }


}