package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

/**
 * Created by WongCheeVui on 3/19/2017.
 */

public class Meas_Comment extends MeasurementBase {
    private String mComment;

    Meas_Comment(){mComment="";}
    @Override
    public int get_reading() { throw new UnsupportedOperationException(); }

    @Override
    public void set_reading(int reading) {
        throw new UnsupportedOperationException();
    }

    public String get_Comment() {
        return mComment;
    }

    public void set_Comment(String comment) {
        this.mComment = comment;
    }
}