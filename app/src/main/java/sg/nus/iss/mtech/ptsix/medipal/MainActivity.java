package sg.nus.iss.mtech.ptsix.medipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.CategoriesActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ConsumablesActivity;
import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.MedicineActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openConsumables(View view) {
        Intent myIntent = new Intent(MainActivity.this, ConsumablesActivity.class);
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

}
