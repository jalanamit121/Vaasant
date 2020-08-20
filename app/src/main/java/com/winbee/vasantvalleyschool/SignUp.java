package com.winbee.vasantvalleyschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.winbee.vasantvalleyschool.Models.RefUser;
import com.winbee.vasantvalleyschool.RetrofitApiCall.ApiClient;
import com.winbee.vasantvalleyschool.Utils.ProgressBarUtil;
import com.winbee.vasantvalleyschool.Utils.SpinnerAdapter;
import com.winbee.vasantvalleyschool.WebApi.ClientApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignUp extends AppCompatActivity {

    EditText nameSignup,emailSignUp,phoneNumberSignUp,passwordSignUp,repasswordSignUp ,rollNumberSignup;
    TextView txt_referal;
    ProgressBar progressBarSignUp;
    private ProgressBarUtil progressBarUtil;
    Spinner batch;
    Spinner dropDownSignUp;
    Button signUpButtonsignup;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameSignup=findViewById(R.id.nameSignup);
        emailSignUp=findViewById(R.id.emailSignUp);
        phoneNumberSignUp=findViewById(R.id.phoneNumberSignUp);
        passwordSignUp=findViewById(R.id.passwordSignUp);
        repasswordSignUp=findViewById(R.id.repasswordSignUp);
        rollNumberSignup=findViewById(R.id.rollNumberSignup);
        signUpButtonsignup=findViewById(R.id.signUpButtonsignup);
        dropDownSignUp=findViewById(R.id.dropDownSignUp);//doneokk
      //  progressBarSignUp=findViewById(R.id.progressBarSignUp);
        txt_referal=findViewById(R.id.txt_referal);
        progressBarUtil   =  new ProgressBarUtil(this);

        String[] titleArray = getResources ( ).getStringArray ( R.array.batch );
        SpinnerAdapter adapter = new SpinnerAdapter( SignUp.this , titleArray );
        dropDownSignUp.setAdapter ( adapter );
        String valToSet = dropDownSignUp.getSelectedItem().toString();
        System.out.println("Suree: "+valToSet);


        signUpButtonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();
            }
        });

    }

    public void GotoLogInActivity(View view) {
        startActivity(new Intent(SignUp.this, Login.class));
    }

//    public void SignUp(View view) {
//
//
//        //startActivity(new Intent(SignUp.this, Home.class));
//    }

    private void userValidation() {
        final String username = nameSignup.getText().toString().trim();
        final String email = emailSignUp.getText().toString().trim();
        final String password = passwordSignUp.getText().toString().trim();
        final String re_password = repasswordSignUp.getText().toString().trim();
        final String referal_code = txt_referal.getText().toString().trim();
        final String phone = phoneNumberSignUp.getText().toString().trim();
        final String roll = rollNumberSignup.getText().toString().trim();
        final Object batchs=  dropDownSignUp.getSelectedItem();

        if (TextUtils.isEmpty(username)) {
            nameSignup.setError("Please enter username");
            nameSignup.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phone)) {
            emailSignUp.setError("Please enter your mobile number");
            emailSignUp.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordSignUp.setError("Enter a password");
            passwordSignUp.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(re_password)) {
            repasswordSignUp.setError("Enter a password");
            repasswordSignUp.requestFocus();
            return;
        }

        if (password.equals(re_password)) {

        }else{
            repasswordSignUp.setError("Password are not matching");
            repasswordSignUp.requestFocus();
            return;
        }
        RefUser refUser = new RefUser();
        refUser.setName(username);
        refUser.setPassword(password);
        refUser.setEmail(email);
        refUser.setMobile(phone);
        refUser.setRegistration(roll);
        refUser.setClass_data(String.valueOf(batchs));
        refUser.setRefcode(referal_code);


        CallSignupApi(refUser);
    }
    private void CallSignupApi(final RefUser refUser) {
        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<RefUser> call = mService.refUserSignIn(2, refUser.getName(),refUser.getEmail(),refUser.getMobile(),refUser.getRefcode(),refUser.getRegistration(),refUser.getClass_data(),refUser.getPassword());
        call.enqueue(new Callback<RefUser>() {
            @Override
            public void onResponse(Call<RefUser> call, Response<RefUser> response) {
                int statusCode = response.code();
                if (statusCode == 200 && response.body().getRegistration_id() != null) {

                    progressBarUtil.hideProgress();
                    Intent intent = new Intent(SignUp.this, OtpVerficationActivity.class);
                    intent.putExtra("message",refUser.getMobile());
                    intent.putExtra("email",refUser.getEmail());
                    intent.putExtra("name",refUser.getName());
                    intent.putExtra("password",refUser.getPassword());
                    startActivity(intent);
                } else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(SignUp.this, "User Already exist", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<RefUser> call, Throwable t) {
                progressBarUtil.hideProgress();
                Toast.makeText(SignUp.this,"Failed"+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
