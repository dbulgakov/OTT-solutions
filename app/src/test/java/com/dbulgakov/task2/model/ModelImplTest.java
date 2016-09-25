package com.dbulgakov.task2.model;

import com.dbulgakov.task2.model.api.ApiInterface;
import com.dbulgakov.task2.other.BaseTest;

import org.junit.Before;

import javax.inject.Inject;

public class ModelImplTest extends BaseTest{
    @Inject
    ApiInterface apiInterface;

    Model model;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        component.inject(this);
        model = new ModelImpl();
    }
}
