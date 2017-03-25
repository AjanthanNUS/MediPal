package sg.nus.iss.mtech.ptsix.medipal.persistence.entity;

public class MeasBloodPressure extends MeasurementBase {

    MeasBloodPressure(){mDiastolic=0;}

    private int mDiastolic;

    public int get_Systolic_reading() {
        return this.get_reading();
    }

    public void set_Systolic_reading(int Systolic) { this.set_reading(Systolic);
    }

    public int get_Diastolic_reading() {
        return mDiastolic;
    }

    public void set_Diastolic_reading(int Diastolic) {
        this.mDiastolic = Diastolic;
    }

}
