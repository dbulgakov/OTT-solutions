package com.dbulgakov.ott_task.interfaces;

import com.dbulgakov.ott_task.entities.GuestNumberSelection;

public interface DataPassListener {
    void onDataPass(GuestNumberSelection guestNumberSelection, boolean selectedValue);
}
