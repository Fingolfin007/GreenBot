package com.iot.ece.greenbot;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Timer;
import java.util.TimerTask;

public class SchedulingActivity extends AppCompatActivity {

    private TimerTask alarmTask  = new AlarmTask(this);
    private Spinner spinner;
    private Button schedule_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheduling);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        spinner = (Spinner) findViewById(R.id.spinner1);

        schedule_button = (Button) findViewById(R.id.button_schedule);
        schedule_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long frequency  = new Long(10);
                int pos =spinner.getSelectedItemPosition();
                switch(pos) {
                    case 0:frequency=5l; break;
                    case 1:frequency=10l; break;
                    case 2:frequency=30l; break;
                    case 3:frequency=86400l; break; //one day
                    case 4:frequency=604800l; break; // one week
                    case 5:frequency=0l;
                }


                Timer timer = new Timer();
                // reset old alarmTask
                alarmTask.cancel();
                alarmTask = new AlarmTask(SchedulingActivity.this);



                if (frequency !=0) {
                    timer.schedule(alarmTask, 0, frequency * 1000);
                }

            }
        });



    }


}
