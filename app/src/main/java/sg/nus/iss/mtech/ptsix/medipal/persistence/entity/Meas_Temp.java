package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

/**
 * Created by WongCheeVui on 3/18/2017.
 */

public class Meas_Temp extends MeasurementBase {
    private float mTemperature;

    Meas_Temp(){mTemperature=0;}
    @Override
    public int get_reading() { throw new UnsupportedOperationException(); }

    @Override
    public void set_reading(int reading) {
        throw new UnsupportedOperationException();
    }

    public float get_Temp_reading() {
        return mTemperature;
    }

    public void set_Temp_reading(float Temperature) {
        this.mTemperature = Temperature;
    }
}
