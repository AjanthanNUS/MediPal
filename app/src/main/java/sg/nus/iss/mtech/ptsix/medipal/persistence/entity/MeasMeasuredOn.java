package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

import java.util.Date;

/**
 * Created by WongCheeVui on 3/19/2017.
 */

public class MeasMeasuredOn extends MeasurementBase {
    private Date mDate;

    MeasMeasuredOn(){ mDate=new Date();}
    @Override
    public int get_reading() { throw new UnsupportedOperationException(); }

    @Override
    public void set_reading(int reading) {
        throw new UnsupportedOperationException();
    }

    public Date get_MeasDate() {
        return mDate;
    }

    public void set_MeasDate(Date comment) {
        this.mDate = comment;
    }
}

