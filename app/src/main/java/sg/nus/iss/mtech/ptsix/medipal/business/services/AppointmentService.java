package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;

import java.util.ArrayList;

import sg.nus.iss.mtech.ptsix.medipal.common.exception.AppointmentExistException;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.AppointmentDAO;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

/**
 * Created by win on 5/3/17.
 */

public class AppointmentService {
    private Context context;
    public AppointmentService(Context context) {
        this.context = context;
    }

    public long makeAppointment(Appointment appointment) throws AppointmentExistException {
        AppointmentDAO appointmentDAO = new AppointmentDAO(context);
        long result = 0;
        appointmentDAO.open();

        try {
            if (appointmentDAO.getAppointmentByDateTime(appointment.getAppointmentDate()) != null) {
                throw new AppointmentExistException();
            }

            result = appointmentDAO.save(appointment);
        } catch (AppointmentExistException aex) {
            throw aex;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            appointmentDAO.close();
        }

        return result;
    }

    public long updateAppointment(Appointment appointment) {
        AppointmentDAO appointmentDAO = new AppointmentDAO(context);
        long result = 0;

        appointmentDAO.open();

        try {
            result = appointmentDAO.update(appointment);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            appointmentDAO.close();
        }

        return result;
    }

    public long deleteAppointment(Appointment appointment) {
        AppointmentDAO appointmentDAO = new AppointmentDAO(context);
        long result = 0;

        appointmentDAO.open();
        try {
            result = appointmentDAO.delete(appointment);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            appointmentDAO.close();
        }
        return result;
    }

    public Appointment getAppointment(int appointmentId) {
        AppointmentDAO appointmentDAO = new AppointmentDAO(context);
        Appointment appointment = null;


        appointmentDAO.open();
        try {
            appointment = appointmentDAO.getAppointment(appointmentId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            appointmentDAO.close();
        }
        return appointment;

    }

    public ArrayList<Appointment> getAppointments() {
        AppointmentDAO appointmentDAO = new AppointmentDAO(context);
        ArrayList<Appointment> ret = new ArrayList<>();
        appointmentDAO.open();
        try {
            ret = appointmentDAO.getAppointments();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            appointmentDAO.close();
        }
        return ret;
    }

}
