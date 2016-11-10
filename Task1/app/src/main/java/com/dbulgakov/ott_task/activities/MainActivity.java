package com.dbulgakov.ott_task.activities;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.dbulgakov.ott_task.factories.DialogFragmentFactory;
import com.dbulgakov.ott_task.entities.GuestNumberSelection;
import com.dbulgakov.ott_task.interfaces.DataPassListener;
import com.dbulgakov.ott_task.R;
import com.dbulgakov.ott_task.interfaces.OrientationChangedListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements DataPassListener, OrientationChangedListener {

    private GuestNumberSelection savedGuestNumberSelection;
    private DialogFragmentFactory dialogFragmentFactory;
    private Button openDialogButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialogFragmentFactory = new DialogFragmentFactory(getString(R.string.saved_guest_count_id),
                getResources().getInteger(R.integer.minimum_screen_width_for_dialog_fragment));

        if (isFromSavedState(savedInstanceState)) {
            restoreState(savedInstanceState);
        }

        openDialogButton = (Button) findViewById(R.id.open_dialog_button);

        openDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dialogFragment = dialogFragmentFactory.getDialogFragment(getScreenWidth(), savedGuestNumberSelection);
                dialogFragment.show(getSupportFragmentManager(), null);
            }
        });
    }


    @Override
    public void onDataPass(GuestNumberSelection guestNumberSelection, boolean selectedValue) {
        this.savedGuestNumberSelection = guestNumberSelection;
        if (selectedValue) {
            Toast.makeText(getApplicationContext(), String.format(Locale.getDefault(), "A %d T %d C %d", guestNumberSelection.getAdultsNumber(),
                    guestNumberSelection.getTeensNumber(), guestNumberSelection.getChildrenNumber()), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.saved_guest_count_id), savedGuestNumberSelection);
    }


    private void restoreState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(getString(R.string.saved_guest_count_id))) {
            savedGuestNumberSelection = savedInstanceState.getParcelable(getString(R.string.saved_guest_count_id));
        }
    }


    private int getScreenWidth(){
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) (displayMetrics.widthPixels / displayMetrics.density);
    }


    private boolean isFromSavedState(Bundle savedInstanceState) {
        return savedInstanceState != null;
    }


    @Override
    public void onOrientationChanged() {
        // open dialog one more time once device screen orientation has changed
        openDialogButton.callOnClick();
    }
}
