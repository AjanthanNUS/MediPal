package sg.nus.iss.mtech.ptsix.medipal.business.services;

import android.content.Context;

import java.util.ArrayList;

import sg.nus.iss.mtech.ptsix.medipal.common.exception.AppointmentExistException;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.AppointmentDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

public class AppointmentService {
    private Context context;

    public AppointmentService(Context context) {
        this.context = context;
    }

    public long makeAppointment(Appointment appointment) throws AppointmentExistException {
        AppointmentDao appointmentDAO = new AppointmentDao(context);
        long result = 0;
        appointmentDAO.open();

        try {
            if (appointmentDAO.getAppointmentByDateTime(appointment.getAppointmentDate()) != null) {
                throw new AppointmentExistException();
            }

            result = appointmentDAO.save(appointment);
            appointment.setId((int) result);

            AppointmentAlarmReceiver.setAlarm(context, appointment);
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
        AppointmentDao appointmentDAO = new AppointmentDao(context);
        long result = 0;

        appointmentDAO.open();

        try {
            result = appointmentDAO.update(appointment);
            AppointmentAlarmReceiver.setAlarm(context, appointment);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            appointmentDAO.close();
        }

        return result;
    }

    public long deleteAppointment(Appointment appointment) {
        AppointmentDao appointmentDAO = new AppointmentDao(context);
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
        AppointmentDao appointmentDAO = new AppointmentDao(context);
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
        AppointmentDao appointmentDAO = new AppointmentDao(context);
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
