package com.dbulgakov.ott_task.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.widget.FrameLayout;
import android.support.annotation.NonNull;

import com.dbulgakov.ott_task.utils.DialogSupporter;


public class GuestCountBottomSheetDialogFragment extends BottomSheetDialogFragment{

    private DialogSupporter dialogSupporter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialogSupporter = new DialogSupporter(dialog, getContext());

        initializeBottomSheetDialogToBeExpanded(dialog);

        dialogSupporter.initializeDialog();

        if (savedInstanceState != null) {
            dialogSupporter.restoreDialogState(savedInstanceState);

        } else if (getArguments() != null){
            dialogSupporter.restoreDialogState(getArguments());
        }

        return dialog;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dialogSupporter.callOnOrientationChange();
        dismiss();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        dialogSupporter.saveDialogState(outState);
    }


    private void initializeBottomSheetDialogToBeExpanded(Dialog dialog) {
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(android.support.design.R.id.design_bottom_sheet);
                assert bottomSheet != null;
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }
}
