package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.LinearLayout;

import org.junit.runner.RunWith;

import sg.nus.iss.mtech.ptsix.medipal.R;

@RunWith(AndroidJUnit4.class)
public class ConsumptionActivityTest extends ActivityInstrumentationTestCase2<ConsumptionActivity> {


    public ConsumptionActivityTest() {
        super(ConsumptionActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    @SmallTest
    public void testFilterBar() {

        LinearLayout filterLayout = (LinearLayout) getActivity().findViewById(R.id.filter_bar);
        assertNotNull(filterLayout);
    }
}
