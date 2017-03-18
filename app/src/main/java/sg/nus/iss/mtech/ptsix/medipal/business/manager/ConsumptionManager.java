package sg.nus.iss.mtech.ptsix.medipal.business.manager;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.ConsumptionDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;

/**
 * Created by Ajanthan on 3/5/2017.
 */

public class ConsumptionManager {
    private ConsumptionDao consumptionDao;

    public ConsumptionManager(Context context) {
        consumptionDao = new ConsumptionDao(context);
    }

    public List<ConsumptionVO> getAllConsumptionList() {
        List<ConsumptionVO> consumptionVOList = new ArrayList<>();

        //   List<Consumption> consumptionList = consumptionDao.getConsumptions();

        //TODO temporary
        int i ;
        for (i = 0; i < 10; i++) {
            ConsumptionVO vo = new ConsumptionVO();
            consumptionVOList.add(vo);
            vo.setEventConsumedOn(new Date());
            vo.setEventQuantity(i * 2);
            Medicine medicine = new Medicine();
            medicine.setMedicine("Paracetamol-" + i);
            medicine.setId(i);
            vo.setMedicine(medicine);
            vo.setEventMedicineID(medicine.getId());
        }

        for (Consumption consumption : consumptionVOList) {
            consumptionDao.save(consumption);
            Log.e("CONSUMPTION DATA INSERT", consumption.toString());
        }
        return consumptionVOList;
    }
}
