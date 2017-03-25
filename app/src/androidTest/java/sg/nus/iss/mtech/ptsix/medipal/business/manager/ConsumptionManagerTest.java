package sg.nus.iss.mtech.ptsix.medipal.business.manager;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.runner.AndroidJUnitRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.util.Date;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.ConsumptionDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Consumption;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.vo.ConsumptionVO;

/**
 * Created by ajay on 25/3/17.
 */

/**
 * Run this on different emulator
 */
@RunWith(AndroidJUnit4.class)
public class ConsumptionManagerTest  {

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
    }

    @After
    public  void destroy(){
        context = null;
        consumptionManager = null;
    }


    public void getAllConsumptionList(){

    }

    public void insertConsumption(){

    }

}
