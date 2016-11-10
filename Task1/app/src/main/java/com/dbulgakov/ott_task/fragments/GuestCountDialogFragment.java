package com.dbulgakov.ott_task.fragments;

import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;


import com.dbulgakov.ott_task.utils.DialogSupporter;


public class GuestCountDialogFragment extends DialogFragment {

    private DialogSupporter dialogSupporter;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        dialogSupporter.callOnOrientationChange();
        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialogSupporter = new DialogSupporter(dialog, getContext());

        dialogSupporter.initializeDialog();

        if (getArguments() != null) {
            dialogSupporter.restoreDialogState(getArguments());
        }

        return dialog;
    }
}
