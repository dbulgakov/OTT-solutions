package com.dbulgakov.ott_task.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dbulgakov.entitycounterview.EntityCounterView;
import com.dbulgakov.ott_task.R;
import com.dbulgakov.ott_task.entities.GuestNumberSelection;
import com.dbulgakov.ott_task.interfaces.DataPassListener;
import com.dbulgakov.ott_task.interfaces.OrientationChangedListener;

public class DialogSupporter {

    private final Dialog dialog;
    private final Context context;

    private EntityCounterView adultsCounterView;
    private EntityCounterView teensCounterView;
    private EntityCounterView childrenCounterView;

    private DataPassListener dataPassListener;
    private OrientationChangedListener orientationChangeListener;

    public DialogSupporter(Dialog dialog, Context context) {
        this.dialog = dialog;
        this.context = context;
    }

    public void initializeDialog(){
        inflateLayout();
        getCounterViewsLinks();
        initializeCloseButton();
        initializeListeners();
    }

    public void inflateLayout(){
        View contentView = View.inflate(context, R.layout.guest_counter_fragment, null);
        dialog.setContentView(contentView);
    }


    public void saveDialogState(Bundle outState) {
        GuestNumberSelection guestNumberSelection = new GuestNumberSelection(adultsCounterView.getCurrentValue(), teensCounterView.getCurrentValue(),
                childrenCounterView.getCurrentValue());
        outState.putParcelable(context.getString(R.string.saved_guest_count_id), guestNumberSelection);
    }


    public void restoreDialogState(Bundle savedInstanceState){
        if (savedInstanceState.containsKey(context.getString(R.string.saved_guest_count_id))) {
            GuestNumberSelection guestNumberSelection = savedInstanceState.getParcelable(context.getString(R.string.saved_guest_count_id));
            assert guestNumberSelection != null;
            adultsCounterView.setCurrentValue(guestNumberSelection.getAdultsNumber());
            teensCounterView.setCurrentValue(guestNumberSelection.getTeensNumber());
            childrenCounterView.setCurrentValue(guestNumberSelection.getChildrenNumber());
        }
    }

    public void callOnOrientationChange() {
        dataPassListener.onDataPass(getCurrentGuestNumberSelection(), false);
        orientationChangeListener.onOrientationChanged();
    }

    public GuestNumberSelection getCurrentGuestNumberSelection(){
        return new GuestNumberSelection(adultsCounterView.getCurrentValue(),
                teensCounterView.getCurrentValue(), childrenCounterView.getCurrentValue());
    }

    private void initializeCloseButton(){
        Button closeButton = (Button) dialog.findViewById(R.id.close_guest_counter_fragment_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                dataPassListener.onDataPass(getCurrentGuestNumberSelection(), true);
            }
        });
    }


    private void getCounterViewsLinks() {
        adultsCounterView = (EntityCounterView) dialog.findViewById(R.id.hotel_guest_adults_counter);
        teensCounterView = (EntityCounterView) dialog.findViewById(R.id.hotel_guest_teens_counter);
        childrenCounterView = (EntityCounterView) dialog.findViewById(R.id.hotel_guest_children_counter);
    }

    private void initializeListeners() {
        dataPassListener = (DataPassListener) context;
        orientationChangeListener = (OrientationChangedListener) context;
    }
}
