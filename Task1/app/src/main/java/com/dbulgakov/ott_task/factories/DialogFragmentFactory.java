package com.dbulgakov.ott_task.factories;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.dbulgakov.ott_task.entities.GuestNumberSelection;
import com.dbulgakov.ott_task.fragments.GuestCountBottomSheetDialogFragment;
import com.dbulgakov.ott_task.fragments.GuestCountDialogFragment;

public class DialogFragmentFactory {

    private GuestNumberSelection savedGuestNumberSelection;
    private final int WIDTH_TO_DIALOG_FRAGMENT;
    private final String savedValueResId;


    public DialogFragmentFactory(String savedValueResId, int widthToDialogFragment) {
        WIDTH_TO_DIALOG_FRAGMENT = widthToDialogFragment;
        this.savedValueResId = savedValueResId;
    }


    public DialogFragment getDialogFragment(int screenWidth, GuestNumberSelection savedGuestNumberSelection){
        this.savedGuestNumberSelection = savedGuestNumberSelection;
        DialogFragment dialogFragment;


        if (screenWidth < WIDTH_TO_DIALOG_FRAGMENT) {
            dialogFragment = new GuestCountBottomSheetDialogFragment();
        } else {
            dialogFragment = new GuestCountDialogFragment();
        }
        return initializeDialogFragment(dialogFragment);

    }


    private DialogFragment initializeDialogFragment(DialogFragment dialogFragment) {
        if (isValueSelected()) {
            Bundle valuesBundle = new Bundle();
            valuesBundle.putParcelable(savedValueResId, savedGuestNumberSelection);
            dialogFragment.setArguments(valuesBundle);
        }
        return dialogFragment;
    }


    private boolean isValueSelected() {
        return savedGuestNumberSelection != null;
    }
}
