package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.HealthBioDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.HealthBio;

public class HealthBioService {

    HealthBioDao healthbioDAO;

    public HealthBioService(Context context) {
        this.healthbioDAO = new HealthBioDao(context);
    }

    public long saveHealthBioInfo(HealthBio healthBio) {
        long id = -1;
        try {
            healthbioDAO.open();
            id = healthbioDAO.save(healthBio);
        } finally {
            healthbioDAO.close();
        }
        return id;
    }

    public void updateHealthBioInfo(HealthBio healthBio) {
        try {
            healthbioDAO.open();
            healthbioDAO.update(healthBio);
        } finally {
            healthbioDAO.close();
        }
    }

    public HealthBio getHealthBioInfo(long id) {
        HealthBio healthBio;
        try {
            healthbioDAO.open();
            healthBio = healthbioDAO.getHealthBio(id);
        } finally {
            healthbioDAO.close();
        }
        return healthBio;
    }

    public List<HealthBio> getAllHealthBio() {
        List<HealthBio> healthBios = new ArrayList<>();
        try {
            healthbioDAO.open();
            healthBios.addAll(healthbioDAO.getHealthBios());
        } finally {
            healthbioDAO.close();
        }
        return healthBios;
    }

    public void deleteHealthBio(HealthBio healthBio) {
        try {
            healthbioDAO.open();
            healthbioDAO.delete(healthBio);
        } finally {
            healthbioDAO.close();
        }
    }

    public void deleteAllHealthBio() {
        try {
            healthbioDAO.open();
            healthbioDAO.delete();
        } finally {
            healthbioDAO.close();
        }
    }

}
