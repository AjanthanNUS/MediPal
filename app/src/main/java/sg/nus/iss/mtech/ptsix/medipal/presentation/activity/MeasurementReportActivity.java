package sg.nus.iss.mtech.ptsix.medipal.presentation.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.R;
import sg.nus.iss.mtech.ptsix.medipal.common.util.CommonUtil;
import sg.nus.iss.mtech.ptsix.medipal.common.util.Constant;
import sg.nus.iss.mtech.ptsix.medipal.presentation.adapter.ViewPagerAdapter;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.DateRangeMeasurementFragment;
import sg.nus.iss.mtech.ptsix.medipal.presentation.fragment.TodayMeasurementFragment;

import static sg.nus.iss.mtech.ptsix.medipal.common.util.Constant.DATE_RANGE_REPORT_INDEX;
import static sg.nus.iss.mtech.ptsix.medipal.common.util.Constant.ONE_MONTH_REPORT_INDEX;
import static sg.nus.iss.mtech.ptsix.medipal.common.util.Constant.TODAY_TAB_REPORT_INDEX;

public class MeasurementReportActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TodayMeasurementFragment todayMeasurementFragment;
    private TodayMeasurementFragment oneWeekFragment;
    private TodayMeasurementFragment oneMonthFragment;
    private DateRangeMeasurementFragment dateRangeFragment;
    private Spinner spinner_ReportType;
    private TextView tvDate;
    private int selectedReportType = 0;

    private int[] tabIcons = {
            R.drawable.ic_view_list_white,
            R.drawable.ic_measurement
    };

    private static final String TODAY_TAB_NAME = "TODAY";
    private static final String ONE_WEEK_TAB_NAME = "1 WEEK";
    private static final String ONE_MONTH_TAB_NAME = "1 MONTH";
    private static final String DATE_RANGE_TAB_NAME = "RANGE";
    private static final String TAG = MeasurementReportActivity.class.getSimpleName();

    private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constant.DATE_FORMAT);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement_report);

        todayMeasurementFragment = new TodayMeasurementFragment();
        oneWeekFragment = new TodayMeasurementFragment();
        oneMonthFragment = new TodayMeasurementFragment();
        dateRangeFragment = new DateRangeMeasurementFragment();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.view_pager_measurement_report);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_measurement);
        tabLayout.setupWithViewPager(viewPager);

        this.spinner_ReportType = (Spinner) findViewById(R.id.report_category);
        this.tvDate = (TextView) findViewById(R.id.tv_date);

        List<String> reportTypes = getReportTypes();
        ArrayAdapter<String> reportTypesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, reportTypes);
        this.spinner_ReportType.setAdapter(reportTypesAdapter);


        tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onTabSelected(Tab tab) {
                showDate(tab.getPosition());
            }

            @Override
            public void onTabUnselected(Tab tab) {

            }

            @Override
            public void onTabReselected(Tab tab) {

            }
        });

        showDate(0);

       this.spinner_ReportType.setOnItemSelectedListener(new OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               selectedReportType = position;
               CommonUtil.reportType = selectedReportType;
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {
                selectedReportType = 0;
           }
       });
    }

    private void showDate(int tabPosition) {

        Date fromDate = new Date();
        Date toDate = new Date();

        switch(tabPosition) {
            case TODAY_TAB_REPORT_INDEX:

                fromDate = CommonUtil.getTodayDate();
                toDate = CommonUtil.getTodayDate();
                tvDate.setText(getFormattedDate(fromDate, toDate));
                switchTab(tabPosition, selectedReportType, fromDate, toDate);
                break;
            case Constant.ONE_WEEK_TAB_REPORT_INDEX:
                fromDate = CommonUtil.getPreviousWeekDate(new Date());
                toDate = CommonUtil.getTodayDate();
                tvDate.setText(getFormattedDate(fromDate, toDate));
                switchTab(tabPosition, selectedReportType, fromDate, toDate);
                break;
            case ONE_MONTH_REPORT_INDEX:
                fromDate = CommonUtil.getPreviousMonthDate(new Date());
                toDate = CommonUtil.getTodayDate();
                tvDate.setText(getFormattedDate(fromDate, toDate));
                switchTab(tabPosition, selectedReportType, fromDate, toDate);
                break;
            case DATE_RANGE_REPORT_INDEX:
                fromDate = CommonUtil.getPreviousMonthDate(new Date());
                toDate = CommonUtil.getTodayDate();
                tvDate.setText(getFormattedDate(fromDate, toDate));
                switchTab(tabPosition, selectedReportType, fromDate, toDate);
                break;
            default:
                tvDate.setText(getFormattedDate(CommonUtil.getTodayDate(), CommonUtil.getTodayDate()));
                break;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(todayMeasurementFragment, TODAY_TAB_NAME);
        adapter.addFragment(oneWeekFragment, ONE_WEEK_TAB_NAME);
        adapter.addFragment(oneMonthFragment, ONE_MONTH_TAB_NAME);
        adapter.addFragment(dateRangeFragment, DATE_RANGE_TAB_NAME);
        viewPager.setAdapter(adapter);
    }

    private List<String> getReportTypes() {
        List<String> reportTypes = new ArrayList<>();

        reportTypes.add("<Select Measurement Type>");
        reportTypes.add("Blood Pressure");
        reportTypes.add("Weight");
        return reportTypes;
    }


    public String getFormattedDate(Date fromDate, Date toDate) {
        String ret = "";

        if (fromDate.equals(toDate)) {
            ret = "Date (" + dateFormatter.format(fromDate) + ")";
        } else {
            ret = "Dates (" + dateFormatter.format(fromDate) + " - " + dateFormatter.format(toDate) + ")";
        }
        return ret;
    }

    public void switchTab(int index, int reportType, Date fromDate, Date toDate) {
        Bundle bundle = new Bundle();
        bundle.putLong("fromDate", fromDate.getTime());
        bundle.putLong("toDate", toDate.getTime());
        bundle.putInt("reportType", reportType);

        CommonUtil.fromDate = fromDate;
        CommonUtil.toDate = toDate;
        CommonUtil.reportType = reportType;

        if (index == Constant.TODAY_TAB_REPORT_INDEX) {
            todayMeasurementFragment = new TodayMeasurementFragment();
        } else if (index == Constant.ONE_WEEK_TAB_REPORT_INDEX) {
            oneWeekFragment = new TodayMeasurementFragment();
        } else if (index == Constant.ONE_MONTH_REPORT_INDEX) {
            oneMonthFragment = new TodayMeasurementFragment();
        } else {
            dateRangeFragment = new DateRangeMeasurementFragment();
        }
    }

}
