package com.dbulgakov.ott_test3.presenter;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.dbulgakov.itemranger.Range;
import com.dbulgakov.ott_test3.R;
import com.dbulgakov.ott_test3.other.App;
import com.dbulgakov.ott_test3.view.ActivityCallback;

import java.util.List;
import java.util.Locale;

import rx.Observable;

import static com.dbulgakov.itemranger.ItemRanger.DEFAULT_ELEMENTS_RANGE;

public class HotelPricesRangesDialogPresenter extends BasePresenter {
    private ActivityCallback activityCallback;
    private static final String BUNDLE_RANGES_KEY = "BUNDLE_RANGES_KEY";
    private static final String BUNDLE_SELECTED_RANGES_KEY = "BUNDLE_SELECTED_RANGES_KEY";
    private boolean[] checkboxSelectionArray;
    private List<Range> rangeList;
    private Context context;

    public HotelPricesRangesDialogPresenter () {
        super();
        App.getComponent().inject(this);
    }


    public void onAttach(Context context){
        this.context = context;
        if (context instanceof ActivityCallback) {
            activityCallback = (ActivityCallback) context;
        } else {
            throw new ClassCastException("Parent activity must implement activityCallback!");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState, Bundle arguments) {
        App.getComponent().inject(this);
        rangeList = getSerializedRanges(arguments);
        checkboxSelectionArray = restoreSavedState(savedInstanceState) != null ? restoreSavedState(savedInstanceState) : new boolean[rangeList.size()];
        AlertDialog alertDialog;

        if (rangeList.get(0).getStartValue() != DEFAULT_ELEMENTS_RANGE) {
            alertDialog = new AlertDialog.Builder(context)
                    .setTitle(R.string.dialog_price_ranges_string)
                    .setMultiChoiceItems(getRangesNames(), checkboxSelectionArray, (dialogInterface, i, b) -> Log.d("Dialog selection update", String.format("selected group #%d", i)))
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        int sum = 0;
                        for (int i = 0; i < checkboxSelectionArray.length; i++) {
                            if (checkboxSelectionArray[i]) {
                                sum+=1;
                            }
                        }
                        activityCallback.onDialogRangesSelected(sum);
                    })
                    .setNegativeButton(R.string.clear_string, null)
                    .setNeutralButton(R.string.close_string, null)
                    .create();
        } else {
            alertDialog =  new AlertDialog.Builder(context)
                    .setTitle(R.string.dialog_error_message_title_string)
                    .setMessage(R.string.dialog_no_hotels_found_message_string)
                    .setPositiveButton(android.R.string.ok, null)
                    .create();
        }
        return alertDialog;
    }

    public void onStart(Dialog dialog) {
        AlertDialog alertDialog = (AlertDialog) dialog;

        if(alertDialog != null )
        {
            Button button = alertDialog.getButton(Dialog.BUTTON_NEGATIVE);
            if (button != null) {
                button.setOnClickListener(view -> {
                    ListView list = alertDialog.getListView();
                    for (int i = 0; i < list.getCount(); i++) {
                        checkboxSelectionArray[i] = false;
                        list.setItemChecked(i, false);
                    }
                });
            }
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putBooleanArray(BUNDLE_SELECTED_RANGES_KEY, checkboxSelectionArray);
    }

    private boolean[] restoreSavedState(Bundle savedInstanceState) {
        boolean[] arr = null;
        if (savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_SELECTED_RANGES_KEY)) {
            arr = savedInstanceState.getBooleanArray(BUNDLE_SELECTED_RANGES_KEY);
        }
        return arr;
    }

    private String[] getRangesNames() {
        return Observable.just(rangeList)
                .flatMap(Observable::from)
                .map((range) -> String.format(Locale.getDefault(), "%s (%d)", range, range.getElementNumber()))
                .toList()
                .toBlocking().single()
                .toArray(new String[0]);
    }

    @SuppressWarnings("unchecked")
    private List<Range> getSerializedRanges(Bundle arguments) {
        return (List<Range>) arguments.getSerializable(BUNDLE_RANGES_KEY);
    }

    public boolean[] getCheckboxSelectionArray() {
        return checkboxSelectionArray;
    }

    public void setCheckboxSelectionArray(boolean[] checkboxSelectionArray) {
        this.checkboxSelectionArray = checkboxSelectionArray;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
