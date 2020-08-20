package com.winbee.vasantvalleyschool.Ui;

import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.winbee.vasantvalleyschool.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class FeeFragment extends Fragment {
    Spinner dropDownClass;
    TextView addmissionFee, AnnualTitionFee, firstInstallment, secondInstallment, thirdInstallment;
    CardView cardViewFee;
    public FeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fee, container, false);

        dropDownClass = view.findViewById(R.id.dropDownClass);
        AnnualTitionFee = view.findViewById(R.id.AnnualTitionFee);
        firstInstallment = view.findViewById(R.id.firstInstallment);
        addmissionFee = view.findViewById(R.id.addmissionFee);
        secondInstallment = view.findViewById(R.id.secondInstallment);
        thirdInstallment = view.findViewById(R.id.thirdInstallment);
        cardViewFee = view.findViewById(R.id.cardViewFee);



        cardViewFee.setVisibility(View.GONE);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dropDownClass.setAutofillHints("Select Your Class");
        }


        addingItemToSpinner();

        return view;
    }

    private void addingItemToSpinner() {
        final List<String> categories = new ArrayList<>();

        final String nurToPrep = "NUR. / KG / PREP.";
        final String firstToSecond = "I-II";
        final String thirdToFifth = "III - V";
        final String sixthToEighth = "VI - VIII.";
        final String ninethToTenth = "IX - X";
        final String elevethToTwelleth = "XI - XII";


        categories.add(nurToPrep);
        categories.add(firstToSecond);
        categories.add(thirdToFifth);
        categories.add(sixthToEighth);
        categories.add(ninethToTenth);
        categories.add(elevethToTwelleth);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()), android.R.layout.simple_spinner_item, categories);
        dropDownClass.setAdapter(dataAdapter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        dropDownClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cardViewFee.setVisibility(View.VISIBLE);

                switch (categories.get(position)) {


                    case nurToPrep:

                        setFeeAmount(3500,11000,2750,4250,4000);

                        break;

                    case firstToSecond:

                        setFeeAmount(4000,13200,3300,5000,4900);

                        break;

                    case thirdToFifth:

                        setFeeAmount(4500,16500,4125,6375,6000);

                        break;

                    case sixthToEighth:

                        setFeeAmount(5000,19800,4950,7450,7400);

                        break;

                    case ninethToTenth:

                        setFeeAmount(5500,27500,6875,10325,10300);

                        break;

                    case elevethToTwelleth:

                        setFeeAmount(6000,33000,8250,12250,12250);

                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cardViewFee.setVisibility(View.GONE);

            }
        });


    }

    public void setFeeAmount(int addmission, int annual, int first, int second, int third) {

        String addmissionstr = addmission+"/-";
        String annualstr = annual+"/-";
        String firststr = first+"/-";
        String secondstr = second+"/-";
        String thirdstr = third+"/-";

        addmissionFee.setText(addmissionstr);
        AnnualTitionFee.setText(annualstr);
        firstInstallment.setText(firststr);
        secondInstallment.setText(secondstr);
        thirdInstallment.setText(thirdstr);


    }


}