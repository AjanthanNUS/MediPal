package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sg.nus.iss.mtech.ptsix.medipal.common.enums.HealthBioConditionTypeEnums;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.HealthBio;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class HealthBioServiceTest {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy", Locale.getDefault());
    private Context context;
    private String testDateStr = "Thu Mar 02 00:00:00 GMT+00:00 2017";

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void createHealthBio() throws Exception {

        Date testDate = formatter.parse(testDateStr);

        HealthBio healthBio = new HealthBio("ALLERGIES", testDate, HealthBioConditionTypeEnums.ALLERGY.getConditionTypeName());
        HealthBioService healthBioService = new HealthBioService(context);
        healthBio.setId((int)healthBioService.saveHealthBioInfo(healthBio));

        HealthBio healthBIOFromDB = healthBioService.getHealthBioInfo(healthBio.getId());
        assertEquals(healthBIOFromDB, healthBio);
    }


    @Test
    public void deleteHealthBio() throws Exception {
        Date testDate = formatter.parse(testDateStr);

        HealthBio healthBio = new HealthBio("CONDITION", testDate, HealthBioConditionTypeEnums.CONDITION.getConditionTypeName());
        HealthBioService healthBioService = new HealthBioService(context);
        healthBio.setId((int)healthBioService.saveHealthBioInfo(healthBio));

        healthBioService.deleteHealthBio(healthBio);
        HealthBio deletedHealthBIO = healthBioService.getHealthBioInfo(healthBio.getId());

        if (deletedHealthBIO != null) {
            fail();
        }
    }

    @Test
    public void getHealthBioInfo() throws Exception {
        Date testDate = formatter.parse(testDateStr);

        HealthBio healthBio = new HealthBio("ALLERGIES", testDate, HealthBioConditionTypeEnums.CONDITION.getConditionTypeName());
        HealthBioService healthBioService = new HealthBioService(context);
        healthBio.setId((int)healthBioService.saveHealthBioInfo(healthBio));

        HealthBio healthFromDB = healthBioService.getHealthBioInfo(healthBio.getId());
        assertEquals(healthFromDB, healthBio);
    }

    @Test
    public void updateICEContact() throws Exception {
        Date testDate = formatter.parse(testDateStr);

        HealthBio healthBio = new HealthBio("CONDITION", testDate, HealthBioConditionTypeEnums.CONDITION.getConditionTypeName());
        HealthBioService healthBioService = new HealthBioService(context);
        healthBio.setId((int)healthBioService.saveHealthBioInfo(healthBio));

        HealthBio healthFromDB = healthBioService.getHealthBioInfo(healthBio.getId());
        healthBioService.updateHealthBioInfo(healthFromDB);

        HealthBio updatedHealthBio = healthBioService.getHealthBioInfo(healthBio.getId());
        assertEquals(updatedHealthBio, healthBio);

    }
}
