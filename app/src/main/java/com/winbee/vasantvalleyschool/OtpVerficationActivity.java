package com.winbee.vasantvalleyschool;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.winbee.vasantvalleyschool.Models.OtpVerify;
import com.winbee.vasantvalleyschool.RetrofitApiCall.ApiClient;
import com.winbee.vasantvalleyschool.Utils.ProgressBarUtil;
import com.winbee.vasantvalleyschool.WebApi.ClientApi;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpVerficationActivity extends AppCompatActivity {
    Button otpVerify;
    TextView mobile;
    EditText otp;
    private ProgressBarUtil progressBarUtil;
    TextView name,email,password;
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private FirebaseFirestore db=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verfication);
        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");
        String Email = bundle.getString("email");
        String Name = bundle.getString("name");
        String Password = bundle.getString("password");
        otp = findViewById(R.id.link_otp);
        mobile = (TextView) findViewById(R.id.text_mobile3);
        name =(TextView) findViewById(R.id.name);
        email = (TextView)findViewById(R.id.email);
        password = (TextView)findViewById(R.id.password);
        mobile.setText(message);
        name.setText(Name);
        email.setText(Email);
        password.setText(Password);
        progressBarUtil = new ProgressBarUtil(this);
        otpVerify = findViewById(R.id.buttonOtp);
         mobile = (TextView) findViewById(R.id.text_mobile3);
        mobile.setText(message);
        otpVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userValidation();
            }
        });
    }

    private void userValidation() {
        final String otp1 = otp.getText().toString();
        final String mobile1=mobile.getText().toString();

        if (TextUtils.isEmpty(otp1)) {
            otp.setError("Please enter your mobile no");
            otp.requestFocus();
            return;
        }

        OtpVerify otpVerify = new OtpVerify();
        otpVerify.setUsername(mobile1);
        otpVerify.setOtp(otp1);

        callOtpVerifySignInApi(otpVerify);
    }
    private void callOtpVerifySignInApi(final OtpVerify otpVerify){

        progressBarUtil.showProgress();
        ClientApi mService = ApiClient.getClient().create(ClientApi.class);
        Call<OtpVerify> call = mService.getOtpVerify(3,otpVerify.getUsername(),otpVerify.getOtp());
        call.enqueue(new Callback<OtpVerify>() {
            @Override
            public void onResponse(Call<OtpVerify> call, Response<OtpVerify> response) {
                int statusCode  = response.code();
                if(statusCode==200 && response.body().getSuccess()== true) {
//                    progressBarUtil.hideProgress();
//                    startActivity(new Intent(OtpVerficationActivity.this, Login.class));
//                    finish();
                    FireBaseValidation();
                }else {
                    progressBarUtil.hideProgress();
                    Toast.makeText(OtpVerficationActivity.this, "Invalid UserName Password ", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<OtpVerify> call, Throwable t) {
                Toast.makeText(OtpVerficationActivity.this,"Failed"+t.getMessage(),Toast.LENGTH_LONG).show();
                System.out.println(t.getLocalizedMessage());
            }
        });
    }
    public void FireBaseValidation(){
        final String Email=email.getText().toString();
        final String Name =name.getText().toString();
        final String Password=password.getText().toString();
        final String FireBaseMobile=mobile.getText().toString();

        if (!Email.isEmpty()&& !Password.isEmpty() ){
            auth.createUserWithEmailAndPassword(Email,Password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                String  userId = Objects.requireNonNull(auth.getCurrentUser()).getUid();

                                Map<String,String> userInfo =new HashMap<>();
                                userInfo.put("Email",Email);
                                userInfo.put("Name",Name);
                                userInfo.put("Password",Password);
                                userInfo.put("FireBaseMobile",FireBaseMobile);
                                userInfo.put("userId",userId);
                                //sab sahi he boss


                                db.collection("Users")
                                        .document(userId)
                                        .set(userInfo)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    progressBarUtil.hideProgress();
                                                    startActivity(new Intent(OtpVerficationActivity.this, Login.class));
                                                    finish();
//                                                    Toast.makeText(OtpVerficationActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
//                                                    finish();
//                                                    Intent intent = new Intent(OtpVerficationActivity.this, GroupChatActivity.class);
//                                                    startActivity(intent);
                                                    Log.d("TAG", "onComplete: Login successfully and data uploaded");

                                                } else {
                                                    // Toast.makeText(OtpVerficationActivity.this, "not successful", Toast.LENGTH_SHORT).show();
                                                    Log.d("TAG", "onComplete: Login failed data failed");
                                                }
                                            }
                                        });



                            }

                        }

                    });//done

        }else{
            Toast.makeText(this, "Fields can't be empty!!", Toast.LENGTH_SHORT).show();
        }


    }

}
