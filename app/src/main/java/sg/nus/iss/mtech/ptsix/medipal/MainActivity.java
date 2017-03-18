package sg.nus.iss.mtech.ptsix.medipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sg.nus.iss.mtech.ptsix.medipal.business.manager.ReminderManager;
import sg.nus.iss.mtech.ptsix.medipal.persistence.entity.Reminder;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.AppointmentActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.CategoriesActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ConsumptionActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ReminderActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.util.UIConstants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openConsumables(View view) {
        Intent myIntent = new Intent(MainActivity.this, ConsumptionActivity.class);
        startActivity(myIntent);
    }

    public void openMedicine(View view) {
        Intent myIntent = new Intent(MainActivity.this, MedicineActivity.class);
        startActivity(myIntent);
    }

    public void openCategories(View view) {
        Intent myIntent = new Intent(MainActivity.this, CategoriesActivity.class);
        startActivity(myIntent);
    }

    public void openAppointment(View view) {
        Intent myIntent = new Intent(MainActivity.this, AppointmentActivity.class);
        startActivity(myIntent);
    }

    public void reminderEdit(View view) {
        ReminderManager reminderManager = new ReminderManager(this);
        Reminder reminder = reminderManager.getAllReminders().get(1);

        Intent myIntent = new Intent(MainActivity.this, ReminderActivity.class);
        myIntent.putExtra(UIConstants.REMINDER, reminder);
        startActivity(myIntent);
    }
}
