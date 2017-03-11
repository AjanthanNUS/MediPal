package sg.nus.iss.mtech.ptsix.medipal.persistence.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;

/**
 * Created by Ajanthan on 3/5/2017.
 */

public class ConsumptionDao {
    public List<Consumption> getConsumptionList(Calendar startDate, Calendar endDate) {
        List<Consumption> consumptions = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Consumption consumption = new Consumption();
            consumption.setId(i);
            consumption.setConsumedOn(Calendar.getInstance());
            Medicine medicine = new Medicine();
            medicine.setName("Paracetamol" + i);
            consumption.setMedicine(medicine);
            consumption.setQuantity(2);
            consumptions.add(consumption);
        }
        return consumptions;
    }
}
