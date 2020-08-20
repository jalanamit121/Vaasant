package com.winbee.vasantvalleyschool.Ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.winbee.vasantvalleyschool.R;
import com.winbee.vasantvalleyschool.Utils.SharedPrefManager;

public class MyProfileFragment extends Fragment {
    TextView studentName, studentRollNumber, studentPhoneNumber, studentClass;
    TextView mentorName, mentorPhoneNumber;
    ImageView mentorCall, mentorMessage;
    String Name,Mobile,Roll,Class;


    public MyProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        Name = SharedPrefManager.getInstance(getActivity()).refCode().getName();
        Mobile = SharedPrefManager.getInstance(getActivity()).refCode().getUsername();
        Roll = SharedPrefManager.getInstance(getActivity()).refCode().getRegistration_number();
        Class = SharedPrefManager.getInstance(getActivity()).refCode().getClass_data();
        studentName = view.findViewById(R.id.studentNameMyProfile);
        studentName.setText(Name);
        studentRollNumber = view.findViewById(R.id.rollNumberMyProfile);
        studentRollNumber.setText(Roll);
        studentPhoneNumber = view.findViewById(R.id.phoneNumberMyProfile);
        studentPhoneNumber.setText(Mobile);
        studentClass = view.findViewById(R.id.classMyProfile);
        studentClass.setText(Class);
        mentorName = view.findViewById(R.id.mentorNameMyProfile);
        mentorPhoneNumber = view.findViewById(R.id.mentorPhoneNumberNameMyProfile);
        mentorCall = view.findViewById(R.id.callImageMyprofile);
        mentorCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri u = Uri.parse("tel:" + "+91 8769564224");
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try
                {
                    startActivity(i);
                }
                catch (SecurityException s)
                {
                }
            }
        });
        mentorMessage = view.findViewById(R.id.messageImageMyProfile);
        mentorMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ "vasantvalleyschooljaipur@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "subject");
                email.putExtra(Intent.EXTRA_TEXT, "message");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }
        });





        return view;
    }
}