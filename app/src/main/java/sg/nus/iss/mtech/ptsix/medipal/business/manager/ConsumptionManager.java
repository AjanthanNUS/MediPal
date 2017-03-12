package sg.nus.iss.mtech.ptsix.medipal.business.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.ConsumptionDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;

/**
 * Created by Ajanthan on 3/5/2017.
 */

public class ConsumptionManager {
    private ConsumptionDao consumptionDao;

//    public ConsumptionManager() {
//        consumptionDao = new ConsumptionDao();
//    }

    public List<ConsumptionVO> getAllConsumptionList() {
        List<ConsumptionVO> consumptionVOList = new ArrayList<>();

        //   List<Consumption> consumptionList = consumptionDao.getConsumptions();

        //TODO temporary
        int i = 0;
        for (i = 0; i < 10; i++) {
            ConsumptionVO vo = new ConsumptionVO();
            consumptionVOList.add(vo);
            vo.setEventConsumedOn(new Date());
            vo.setEventQuantity(i * 2);
            Medicine medicine = new Medicine();
            medicine.setEventMedicine("Paracitamole-" + i);
            medicine.setId(i);
            vo.setMedicine(medicine);
            vo.setEventMedicineID(medicine.getId());
        }


        return consumptionVOList;
    }
}
