package com.dbulgakov.ott_test3.other;

import com.dbulgakov.ott_test3.BuildConfig;
import com.dbulgakov.ott_test3.other.App;
import com.dbulgakov.ott_test3.other.di.TestComponent;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricGradleTestRunner.class)
@Config(application = TestApplication.class,
        constants = BuildConfig.class,
        sdk = 23)
@Ignore
public class BaseTest {

    public TestComponent component;
    public TestUtils testUtils;

    @Before
    public void setUp() throws Exception {
        component = (TestComponent) App.getComponent();
        testUtils = new TestUtils();
    }

}
