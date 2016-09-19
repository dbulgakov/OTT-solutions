package com.dbulgakov.task2.view.fragments;

import android.app.Fragment;

import com.dbulgakov.task2.presenter.Presenter;

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
