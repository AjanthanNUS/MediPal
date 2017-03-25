package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

public class MeasTemp extends MeasurementBase {
    private float mTemperature;

    MeasTemp(){mTemperature=0;}
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
