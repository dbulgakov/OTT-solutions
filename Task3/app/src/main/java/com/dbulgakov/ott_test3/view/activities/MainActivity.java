package com.dbulgakov.ott_test3.view.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dbulgakov.itemranger.Range;
import com.dbulgakov.ott_test3.R;
import com.dbulgakov.ott_test3.view.ActivityCallback;
import com.dbulgakov.ott_test3.view.fragments.HotelPricesRangesDialogFragment;
import com.dbulgakov.ott_test3.view.fragments.MainFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ActivityCallback{

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        replaceFragment(new MainFragment(), false);
    }

    private void replaceFragment(Fragment fragment, boolean addBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        if (addBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onStartHotelPricesRangesDialogFragment(List<Range> rangeList) {
        HotelPricesRangesDialogFragment.newInstance(rangeList).show(getSupportFragmentManager(), "tag");
    }

    @Override
    public void onDialogRangesSelected(int rangeCount) {
        Toast.makeText(this, rangeCount + " ranges selected", Toast.LENGTH_SHORT).show();
    }
}
