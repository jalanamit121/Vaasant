package com.winbee.vasantvalleyschool.Ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.winbee.vasantvalleyschool.R;
//boss log out pr click krne pr direct logout ho rha he ek warning to deni chhiye apn ko?

public class bottomDialogForAttachment extends BottomSheetDialogFragment {
    BottomSheetListener bottomSheetListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.bottom_sheet_attachment,container,false);

        Button addPhoto = view.findViewById(R.id.addPhotoBottomSheet);
        Button addPdf = view.findViewById(R.id.addPdfBottomSheet);

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetListener.onPhotoAttachmentClicked();
                dismiss();
            }
        });
        addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetListener.onPdfAttachmentClicked();
                dismiss();
            }
        });


        return view;
    }

    public interface BottomSheetListener{
        void onPhotoAttachmentClicked();
        void onPdfAttachmentClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof  BottomSheetListener)
         {
            bottomSheetListener = (BottomSheetListener) context;
        } else  {
//            throw new ClassCastException(context.toString()
//                    + " must implement BottomSheetListener");
            throw new RuntimeException(context.toString()
                    + " must implement OnGreenFragmentListener");
        }
    }


}
