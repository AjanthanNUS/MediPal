package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

/**
 * Created by WongCheeVui on 3/18/2017.
 */

public class MeasurementBase {
    MeasurementBase(){
        reading=0;
        m_unit="unit";
    }
    MeasurementBase(int reading, String unit){
        this.reading = reading;
        this.m_unit = unit;
    }
    MeasurementBase(String unit){
        this.m_unit = unit;
    }

    private int reading;
    private String m_unit;

    public int get_reading() {
        return reading;
    }

    public void set_reading(int reading) {
        this.reading = reading;
    }

    public String get_unit() {
        return m_unit;
    }

    public void set_unit(String m_unit) {
        this.m_unit = m_unit;
    }

}
