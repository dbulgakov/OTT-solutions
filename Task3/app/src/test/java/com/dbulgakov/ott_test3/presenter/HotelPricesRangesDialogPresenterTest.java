package com.dbulgakov.ott_test3.presenter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;

import com.dbulgakov.itemranger.Range;
import com.dbulgakov.ott_test3.R;
import com.dbulgakov.ott_test3.model.Model;
import com.dbulgakov.ott_test3.other.BaseTest;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.notNull;

public class HotelPricesRangesDialogPresenterTest extends BaseTest{
    @Inject
    Model model;

    private HotelPricesRangesDialogPresenter hotelPricesRangesDialogPresenter;
    private Context context;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        hotelPricesRangesDialogPresenter = new HotelPricesRangesDialogPresenter();
        context = new ContextThemeWrapper(RuntimeEnvironment.application, R.style.AppTheme);
    }

    @Test
    public void testOnCreateDialog() throws Exception{

        hotelPricesRangesDialogPresenter.setContext(context);

        Bundle bundle = new Bundle();
        ArrayList<Range> ranges = new ArrayList<>();

        ranges.add(new Range(-1, -1, 0));

        bundle.putSerializable("BUNDLE_RANGES_KEY", ranges);

        AlertDialog dialog = (AlertDialog) hotelPricesRangesDialogPresenter.onCreateDialog(bundle, bundle);

        assertEquals(1, hotelPricesRangesDialogPresenter.getCheckboxSelectionArray().length);
        assertEquals(null, dialog.getButton(DialogInterface.BUTTON_NEGATIVE));

        ranges.get(0).setStartValue(100);
        bundle.putSerializable("BUNDLE_RANGES_KEY", ranges);

        dialog = (AlertDialog) hotelPricesRangesDialogPresenter.onCreateDialog(bundle, bundle);

        assertEquals(notNull(), dialog.getButton(Dialog.BUTTON_NEGATIVE));

    }

    @Test
    public void testOnAttachWrongContext() throws Exception {
        try {
            hotelPricesRangesDialogPresenter.onAttach(RuntimeEnvironment.application);
            fail();
        } catch (Exception expected) {
            assertEquals("Parent activity must implement activityCallback!", expected.getMessage());
        }
    }

    @Test
    public void testOnStart() throws Exception {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setNegativeButton("no", null)
                .create();
        alertDialog.show();

        hotelPricesRangesDialogPresenter.onStart(alertDialog);
        hotelPricesRangesDialogPresenter.setCheckboxSelectionArray(new boolean[3]);
        assertEquals(true, alertDialog.getButton(Dialog.BUTTON_NEUTRAL).hasOnClickListeners());
    }
}
