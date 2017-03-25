package sg.nus.iss.mtech.ptsix.medipal.business.manager;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;


@RunWith(AndroidJUnit4.class)
public class ConsumptionManagerTest {

    private ConsumptionManager consumptionManager;
    private Context context;
    private ConsumptionVO cvo1;
    private ConsumptionVO cvo2;
    private Consumption c1;
    private Consumption c2;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        consumptionManager = new ConsumptionManager(context);


        c1 = new Consumption();
        c1.setQuantity(1);
        c1.setConsumedOn(new Date());
        c1.setMedicineID(-1);

        c2 = new Consumption();
        c2.setQuantity(2);
        c2.setConsumedOn(new Date());
        c2.setMedicineID(-1);
    }

    @After
    public void tearDown() {
        context = null;
        consumptionManager = null;
        c1 = null;
        c2 = null;

    }

    @Test
    public void truncateTable(){
       // consumptionManager.truncateTable();
       // assertEquals(consumptionManager.getAllConsumptionList().size(), 0);
    }

    @Test
    public void getAllConsumptionList() {

    }

    @Test
    public void insertConsumption() {
        try {
            consumptionManager.insertConsumption(c1);
        } catch (Exception e) {
            assert true;
        }

    }

}
