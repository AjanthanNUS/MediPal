package sg.nus.iss.mtech.ptsix.medipal.business.manager;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.common.exception.ConsumptionSaveException;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.CategoriesDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.ConsumptionDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.MedicineDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Categories;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Medicine;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;

public class ConsumptionManager {
    private final MedicineDao medicineDao;
    private final CategoriesDao categoriesDao;
    private final ConsumptionDao consumptionDao;

    public ConsumptionManager(Context context) {
        consumptionDao = new ConsumptionDao(context);
        medicineDao = new MedicineDao(context);
        categoriesDao = new CategoriesDao(context);
    }

    public void sortConsumptionList(List<ConsumptionVO> consumptions) {
        Collections.sort(consumptions, new Comparator<ConsumptionVO>() {
            @Override
            public int compare(ConsumptionVO o1, ConsumptionVO o2) {
                if (o1.getConsumedOn().after(o2.getConsumedOn())) {
                    return -1;
                } else if (o1.getConsumedOn().before(o2.getConsumedOn())) {
                    return 1;
                }
                return 0;
            }
        });

    }

    public List<Consumption> getAllConsumptionList() {
        List<Consumption> consumptions = consumptionDao.getConsumptions();
        return consumptions;
    }

    public List<ConsumptionVO> getAllConsumptionVOList() {
        List<ConsumptionVO> consumptionVOList = new ArrayList<>();
        List<Consumption> consumptions = consumptionDao.getConsumptions();
        for (Consumption consumption : consumptions) {
            consumptionVOList.add(castToConsumptionVo(consumption));
        }
        return consumptionVOList;
    }

    public ConsumptionVO castToConsumptionVo(Consumption consumption) {
        ConsumptionVO consumptionVO;
        if (consumption instanceof ConsumptionVO) {
            consumptionVO = (ConsumptionVO) consumption;
        } else {
            Consumption consFromDB = consumptionDao.getConsumption(consumption.getId());

            consumptionVO = new ConsumptionVO();
            consumptionVO.setQuantity(consFromDB.getQuantity());
            consumptionVO.setMedicineID(consFromDB.getMedicineID());
            consumptionVO.setConsumedOn(consFromDB.getConsumedOn());

            Medicine medicine = medicineDao.getMedicine(consumption.getMedicineID());
            consumptionVO.setMedicine(medicine);

            Categories category = categoriesDao.getCategories(medicine.getCatId());
            consumptionVO.setCategories(category);

        }
        return consumptionVO;
    }

    public Consumption insertConsumption(Consumption consumption) {
        long id = consumptionDao.save(consumption);

        if (id != -1) {
            consumption = consumptionDao.getConsumptionByRowID(id);
        } else {
            throw new ConsumptionSaveException();
        }
        return consumption;
    }

    public long update(Consumption consumption) {
        return consumptionDao.update(consumption);
    }
}
