package com.dbulgakov.ott_test3.view.fragments;

import android.support.v4.app.Fragment;

import com.dbulgakov.ott_test3.presenter.Presenter;

public abstract class BaseFragment extends Fragment {
    protected abstract Presenter getPresenter();

    @Override
    public void onStop() {
        super.onStop();
        if (getPresenter() != null) {
            getPresenter().onStop();
        }
    }
}