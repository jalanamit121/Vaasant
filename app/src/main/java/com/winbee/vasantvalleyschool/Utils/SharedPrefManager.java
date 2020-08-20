package com.winbee.vasantvalleyschool.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.winbee.vasantvalleyschool.Login;
import com.winbee.vasantvalleyschool.Models.RefCode;
import com.winbee.vasantvalleyschool.Models.RefUser;


public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "serverloginsharedpref";
    private static final String KEY_USERNAME = "Username";
    private static final String KEY_NAME = "Name";
    private static final String KEY_EMAIL = "Email";
    private static final String KEY_ROLE_ENCODE = "role_Encode";
    private static final String KEY_STATUS = "Status";
    private static final String KEY_USERID = "UserId";
    private static final String KEY_LOGIN_STATUS = "LoginStatus";
    private static final boolean KEY_MESSAGE_FAILURE = false;
    private static final boolean KEY_CURRENT_LOGIN_STATUS = false;
    private static final String KEY_ORG_CODE = "Org_Code";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REF_CODE = "ref_code";
    private static final String KEY_CRED = "Cred";
    private static final String KEY_WHATSAAP = "WhatsaapNo";
    private static final String KEY_ROLL = "registration_number";
    private static final String KEY_CLASS = "class_data";



    private static final String SHARED_PREF_NAME1 = "serverloginsharedpref1";
    private static final String KEY_USERNAME1 = "Username";
    private static final String KEY_NAME1 = "Name";
    private static final String KEY_EMAIL1 = "Email";
    private static final String KEY_ROLE_ENCODE1 = "role_Encode";
    private static final String KEY_USERID1 = "UserId";

    private static final String KEY_PASSWORD1 = "password";
    private static final String KEY_REF_CODE1 = "ref_code";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(RefCode refCode) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt(KEY_ID, user.g);
        editor.putString(KEY_USERNAME, refCode.getUsername());
        editor.putString(KEY_ROLE_ENCODE, refCode.getRole_Encode());
        editor.putString(KEY_USERID, refCode.getUserId());
        editor.putString(KEY_PASSWORD, refCode.getPassword());
        editor.putString(KEY_REF_CODE, refCode.getRef_code());
        editor.putString(KEY_NAME, refCode.getName());
        editor.putString(KEY_EMAIL, refCode.getEmail());
        editor.putString(KEY_ROLL, refCode.getRegistration_number());
        editor.putString(KEY_CLASS, refCode.getClass_data());
        editor.apply();
    }

//    public void freeUserLogin(FreeRefCode freeRefCode) {
//        SharedPreferences sharedPreferences1 = mCtx.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences1.edit();
////        editor.putInt(KEY_ID, user.g);
//        editor.putString(KEY_USERNAME1, freeRefCode.getUsername());
//        editor.putString(KEY_ROLE_ENCODE1, freeRefCode.getRole_Encode());
//        editor.putString(KEY_USERID1, freeRefCode.getUserId());
//        editor.putString(KEY_PASSWORD1, freeRefCode.getPassword());
//        editor.putString(KEY_REF_CODE1, freeRefCode.getRef_code());
//        editor.putString(KEY_NAME1, freeRefCode.getName());
//        editor.putString(KEY_EMAIL1, freeRefCode.getEmail());
//        editor.apply();
//    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public RefCode refCode() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new RefCode(
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_ROLE_ENCODE, null),
                sharedPreferences.getString(KEY_USERID, null),
                sharedPreferences.getString(KEY_REF_CODE, null),
                sharedPreferences.getString(KEY_ROLL, null),
                sharedPreferences.getString(KEY_CLASS, null)

        );

    }
        public RefUser refUser1() {
            SharedPreferences sharedPreferences1 = mCtx.getSharedPreferences(SHARED_PREF_NAME1, Context.MODE_PRIVATE);
            return new RefUser(
                    sharedPreferences1.getString(KEY_USERNAME1, null),
                    sharedPreferences1.getString(KEY_NAME1, null),
                    sharedPreferences1.getString(KEY_EMAIL1, null),
                    sharedPreferences1.getString(KEY_ROLE_ENCODE1, null),
                    sharedPreferences1.getString(KEY_USERID1, null)

            );
    }



    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Login.class));

    }
}
