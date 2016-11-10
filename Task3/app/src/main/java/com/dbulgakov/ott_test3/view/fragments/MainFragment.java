package com.dbulgakov.ott_test3.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dbulgakov.itemranger.Range;
import com.dbulgakov.ott_test3.R;
import com.dbulgakov.ott_test3.other.di.view.DaggerViewComponent;
import com.dbulgakov.ott_test3.other.di.view.ViewComponent;
import com.dbulgakov.ott_test3.other.di.view.ViewDynamicModule;
import com.dbulgakov.ott_test3.presenter.MainPresenter;
import com.dbulgakov.ott_test3.presenter.Presenter;
import com.dbulgakov.ott_test3.view.ActivityCallback;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends BaseFragment implements MainView {

    @Inject
    protected MainPresenter presenter;

    @BindView(R.id.get_hotel_progressbar)
    ProgressBar getHotelRangesProgressbar;

    @BindView(R.id.main_fragment_parent_view)
    View fragmentMainView;

    private ViewComponent viewComponent;

    private ActivityCallback activityCallback;

    @OnClick(R.id.get_hotel_groups_button)
    public void onGetHotelPricesRangesClick(){
        presenter.onGetHotelPricesRangesButtonClick();
    }


    @Override
    public void startPriceRangesDialog(List<Range> rangeList) {
        activityCallback.onStartHotelPricesRangesDialogFragment(rangeList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (viewComponent == null) {
            viewComponent = DaggerViewComponent.builder()
                    .viewDynamicModule(new ViewDynamicModule(this))
                    .build();
        }
        viewComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void startProgressBar() {
        getHotelRangesProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgressBar() {
        getHotelRangesProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof UnknownHostException) {
            makeToast(getString(R.string.error_no_internet_message_string));
        } else {
            makeToast(getString(R.string.error_unknown_message_string));
        }
    }

    private void makeToast(String text) {
        Snackbar.make(fragmentMainView, text, Snackbar.LENGTH_LONG).show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ActivityCallback) {
            activityCallback = (ActivityCallback) context;
        } else {
            throw new ClassCastException("Parent activity must implement activityCallback!");
        }
    }


    @Override
    protected Presenter getPresenter() {
        return presenter;
    }
}
