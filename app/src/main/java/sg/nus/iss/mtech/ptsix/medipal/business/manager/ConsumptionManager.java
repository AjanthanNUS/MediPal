package sg.nus.iss.mtech.ptsix.medipal.business.manager;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.ConsumptionDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;

/**
 * Created by Ajanthan on 3/5/2017.
 */

public class ConsumptionManager {
    private ConsumptionDao consumptionDao;
/*
    public ConsumptionManager() {
        consumptionDao = new ConsumptionDao(this);
    }
*/
    public List<Consumption> getAllConsumptionList() {
        return consumptionDao.getConsumptions();
    }
}
