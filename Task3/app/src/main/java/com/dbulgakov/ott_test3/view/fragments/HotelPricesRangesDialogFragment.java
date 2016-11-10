package com.dbulgakov.ott_test3.view.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

import com.dbulgakov.itemranger.Range;
import com.dbulgakov.ott_test3.other.App;
import com.dbulgakov.ott_test3.presenter.HotelPricesRangesDialogPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class HotelPricesRangesDialogFragment extends DialogFragment implements View {

    @Inject
    protected HotelPricesRangesDialogPresenter presenter;

    private static final String BUNDLE_RANGES_KEY = "BUNDLE_RANGES_KEY";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        App.getComponent().inject(this);
        presenter.onAttach(context);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return presenter.onCreateDialog(savedInstanceState, getArguments());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSaveInstanceState(outState);
    }

    public static HotelPricesRangesDialogFragment newInstance(List<Range> rangeList) {
        HotelPricesRangesDialogFragment rangesDialog = new HotelPricesRangesDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_RANGES_KEY, new ArrayList<>(rangeList));
        rangesDialog.setArguments(args);
        return rangesDialog;
    }

    @Override
    public void showError(Throwable throwable) {
        Toast.makeText(App.getContext(), throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart(getDialog());
    }
}
