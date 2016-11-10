package com.dbulgakov.itemranger.other;

import com.dbulgakov.itemranger.BuildConfig;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class,
        sdk = 23)
@Ignore
public class BaseTest {

}