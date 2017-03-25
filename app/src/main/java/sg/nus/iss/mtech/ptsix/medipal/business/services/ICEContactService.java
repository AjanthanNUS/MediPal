package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;
import android.util.Log;

import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.IceDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.ICE;

public class ICEContactService {

    private IceDao iceDAO;
    /**
     * Instantiating ICEContactService object
     * @param context
     */
    public ICEContactService(Context context) {
        this.iceDAO = new IceDao(context);
    }

    /**
     * Save ICE information into DB
     * @param iceContact
     */
    public int saveICEContact(ICE iceContact) {
        int id = -1;
        try {
            iceDAO.open();
            id = this.iceDAO.save(iceContact);
        } finally {
            iceDAO.close();
        }
        return id;
    }

    /**
     * update ICE  Contact information
     * @param iceContact
     */
    public void updateICEContact(ICE iceContact) {
        try {
            iceDAO.open();
            this.iceDAO.update(iceContact);
        } finally {
            iceDAO.close();
        }
    }

    /**
     * update ICE  Contact information
     * @param iceContactList
     */
    public void updateICEContactByID(List<ICE> iceContactList) {
        try {
            iceDAO.open();
            for(ICE iceContact : iceContactList) {
                Log.i("Final Seq Number : " + iceContact.getName(), String.valueOf(iceContact.getSequence()));
                this.iceDAO.updateByID(iceContact);
            }
        } finally {
            iceDAO.close();
        }
    }

    /**
     * update ICE  Contact information
     * @param iceContactList
     */
    public void updateAllICEContact(List<ICE> iceContactList) {
        try {
            iceDAO.open();
            for(ICE iceContact : iceContactList) {
                this.iceDAO.update(iceContact);
            }

        } finally {
            iceDAO.close();
        }
    }

    /**
     * get ICE Contact By ID
     * @param id
     * @return
     */
    public ICE getICEContactByID(int id) {
        ICE ice;
        try {
            iceDAO.open();
            ice = this.iceDAO.getICE(id);
        } finally {
            iceDAO.close();
        }
        return ice;
    }

    /**
     * get ICE Contact By Name
     * @param name
     * @return
     */
    public ICE getICEContactByName(String name) {
        ICE ice;
        try {
            iceDAO.open();
            ice = this.iceDAO.getICEByName(name);
        } finally {
            iceDAO.close();
        }
        return ice;
    }

    /**
     * get All ICE Contact
     * @return
     */
    public List<ICE> getAllICEContact() {
        List<ICE> iceList;
        try {
            iceDAO.open();
            iceList = this.iceDAO.getICEs();
        } finally {
            iceDAO.close();
        }
        return iceList;
    }

    public void deleteCEContactsByID(long id) {
        try {
            iceDAO.open();
            iceDAO.delete(id);
        } finally {
            iceDAO.close();
        }
    }

    public void deleteAllICEContacts() {
        try {
            iceDAO.open();
            iceDAO.delete();
        } finally {
             iceDAO.close();
        }
    }

    public long makeCategories(ICE ice) {
        long result = 0;

        this.iceDAO.open();
        try {
            result = this.iceDAO.save(ice);
            ice.setId((int) result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.iceDAO.close();
        }

        return result;
    }


}
