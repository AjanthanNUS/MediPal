package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import sg.nus.iss.mtech.ptsix.medipal.common.enums.ICEContactTypeEnums;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.ICE;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class ICEContactServiceTest {

    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void createICEContact() throws Exception {

        ICE iceContact = new ICE("JOHN", "90008099", ICEContactTypeEnums.EMERGENCY_NUMBER.getEnumCode(), "Unit Test", 1);
        ICEContactService iceContactService = new ICEContactService(context);
        iceContact.setId(iceContactService.saveICEContact(iceContact));

        ICE iceFromDB = iceContactService.getICEContactByID(iceContact.getId());
        assertEquals(iceContact, iceFromDB);
    }


    @Test
    public void deleteCEContactsByID() throws Exception {
        ICE iceContact = new ICE("JOHN", "90008099", ICEContactTypeEnums.EMERGENCY_NUMBER.getEnumCode(), "Unit Test", 2);

        ICEContactService iceContactService = new ICEContactService(context);
        iceContact.setId(iceContactService.saveICEContact(iceContact));

        iceContactService.deleteCEContactsByID(iceContact.getId());
        ICE deletedICE = iceContactService.getICEContactByID(iceContact.getId());

        if (deletedICE != null) {
            fail();
        }
    }

    @Test
    public void getICEContactByID() throws Exception {
        ICE iceContact = new ICE("JOHN", "90008099", ICEContactTypeEnums.EMERGENCY_NUMBER.getEnumCode(), "Unit Test", 3);
        ICEContactService iceContactService = new ICEContactService(context);
        iceContact.setId(iceContactService.saveICEContact(iceContact));

        ICE iceFromDB = iceContactService.getICEContactByID(iceContact.getId());
        assertEquals(iceContact, iceFromDB);
    }

    @Test
    public void updateICEContact() throws Exception {
        ICE iceContact = new ICE("JOHN", "90008099", ICEContactTypeEnums.EMERGENCY_NUMBER.getEnumCode(), "Unit Test", 4);

        ICEContactService iceContactService = new ICEContactService(context);
        iceContact.setId(iceContactService.saveICEContact(iceContact));

        ICE iceFromDB = iceContactService.getICEContactByID(iceContact.getId());
        iceFromDB.setDescription("Text is updated from Unit Test");
        iceContactService.updateICEContact(iceFromDB);

        ICE updatedICE = iceContactService.getICEContactByID(iceContact.getId());
        assertEquals(updatedICE, iceFromDB);

    }
}
