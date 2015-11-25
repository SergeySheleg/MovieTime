package org.sergeysheleg.movietime;

import android.support.design.widget.TabLayout;
import android.test.ActivityInstrumentationTestCase2;

public class MainActivityTestCase extends ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity mainActivity;
    private TabLayout tabLayout;

    public MainActivityTestCase(Class<MainActivity> activityClass) {
        super(activityClass);
    }

    public MainActivityTestCase() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        tabLayout = (TabLayout) mainActivity.findViewById(R.id.tabs);
    }

    public void testActivityExists() {
        assertNotNull(mainActivity);
    }

    public void testTabLayoutExist() {
        assertNotNull(tabLayout);
    }


}
