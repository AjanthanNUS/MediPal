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

import sg.nus.iss.mtech.ptsix.medipal.common.exception.AppointmentExistException;
import sg.nus.iss.mtech.ptsix.medipal.persistence.dao.AppointmentDao;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Appointment;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class AppointmentServiceTest {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
    private Context context;


    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();

        AppointmentDao appointmentDAO = new AppointmentDao(context);
        appointmentDAO.open();
        appointmentDAO.truncateAllAppointments();
        appointmentDAO.close();

    }

    @Test
    public void makeAppointment() throws Exception {
        String appointmentDateStr = "02 MAR 2017 2:30 PM";
        Date appointmentDate = formatter.parse(appointmentDateStr);

        Appointment appointment = new Appointment("University Health Centre", "Medical Check Up", appointmentDate);
        appointment.setId(1);
        AppointmentService appointmentService = new AppointmentService(context);
        appointment.setId((int)appointmentService.makeAppointment(appointment));


        Appointment appointment1 = new Appointment("Poly Clinic Tampines", "Blood Test First", appointmentDate);

        try {
            appointmentService.makeAppointment(appointment1);
            fail();
        } catch (AppointmentExistException e1) {

        }

        Appointment appointmentFromDB = appointmentService.getAppointment(1);
        assertEquals(appointment, appointmentFromDB);

    }

    @Test
    public void deleteAppointment() throws Exception {
        String appointmentDateStr = "02 MAR 2017 2:30 PM";
        Date appointmentDate = formatter.parse(appointmentDateStr);

        Appointment appointment = new Appointment("University Health Centre", "Medical Check Up", appointmentDate);

        AppointmentService appointmentService = new AppointmentService(context);
        appointment.setId((int)appointmentService.makeAppointment(appointment));

        appointmentService.deleteAppointment(appointment);
        Appointment deletedAppointment = appointmentService.getAppointment(1);

        if (deletedAppointment != null) {
            fail();
        }
    }

    @Test
    public void getAppointment() throws Exception {
        String appointmentDateStr = "02 MAR 2017 2:30 PM";
        Date appointmentDate = formatter.parse(appointmentDateStr);

        Appointment appointment = new Appointment("University Health Centre", "Medical Check Up", appointmentDate);

        AppointmentService appointmentService = new AppointmentService(context);
        appointment.setId((int)appointmentService.makeAppointment(appointment));

        Appointment appointFromDB = appointmentService.getAppointment(appointment.getId());
        assertEquals(appointment, appointFromDB);
    }


    @Test
    public void updateAppointment() throws Exception {
        String appointmentDateStr = "02 MAR 2017 2:30 PM";
        Date appointmentDate = formatter.parse(appointmentDateStr);

        Appointment appointment = new Appointment("University Health Centre", "Medical Check Up", appointmentDate);

        AppointmentService appointmentService = new AppointmentService(context);
        appointment.setId((int)appointmentService.makeAppointment(appointment));

        Appointment appointFromDB = appointmentService.getAppointment(appointment.getId());
        appointFromDB.setLocation("Polyclinic");
        appointmentService.updateAppointment(appointFromDB);

        Appointment updatedAppointment = appointmentService.getAppointment(appointFromDB.getId());
        assertEquals(appointFromDB, updatedAppointment);

    }



}