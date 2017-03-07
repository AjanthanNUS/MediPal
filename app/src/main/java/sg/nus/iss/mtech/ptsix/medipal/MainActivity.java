package sg.nus.iss.mtech.ptsix.medipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sg.nus.iss.mtech.ptsix.medipal.presentation.activity.ConsumptionActivity;

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

}
