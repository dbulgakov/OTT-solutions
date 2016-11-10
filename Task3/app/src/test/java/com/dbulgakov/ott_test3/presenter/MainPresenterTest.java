package com.dbulgakov.ott_test3.presenter;

import com.dbulgakov.itemranger.Range;
import com.dbulgakov.ott_test3.model.Model;
import com.dbulgakov.ott_test3.other.BaseTest;
import com.dbulgakov.ott_test3.other.TestConst;
import com.dbulgakov.ott_test3.view.fragments.MainView;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest extends BaseTest{
    @Inject
    protected Model model;

    private MainPresenter mainPresenter;
    private MainView mockView;
    private List<Range> priceRanges;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);

        mockView = mock(MainView.class);
        mainPresenter = new MainPresenter(mockView);

        priceRanges = Collections.singletonList(new Range(-1, -1, 0));

        when(model.getCommonPriceRanges(TestConst.TEST_CITY_ID_WITH_LOTS_OF_HOTELS)).thenReturn(Observable.just(priceRanges));
    }

    @Test
    public void testOnGetHotelPricesRangesButtonClick() throws Exception{
        mainPresenter.onGetHotelPricesRangesButtonClick();
        verify(mockView).startProgressBar();
    }
}
