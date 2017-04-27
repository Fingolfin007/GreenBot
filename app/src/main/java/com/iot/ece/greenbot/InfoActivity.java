package com.iot.ece.greenbot;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class InfoActivity extends AppCompatActivity {

    public ImageView sunlight_chart;
    public ImageView moisture_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        moisture_chart = (ImageView) findViewById(R.id.moisture_level_chart);
        sunlight_chart = (ImageView) findViewById(R.id.sunlight_level_chart);


        // hard-coded data for test purposes
        float[] nums_m = {8.5f,9,10,9,5,4,1,4f,6 ,7 };
        float[] nums_s = {3.5f,4.9f,8,8.8f,8.5f,6.1f,4f,4f,2.2f ,1 };
        LstPaint lst_m = new LstPaint(moisture_chart, "             Moisture   ",nums_m);
        lst_m.draw();
        LstPaint lst_s = new LstPaint(sunlight_chart, "             Sunlight   ",nums_s);
        lst_s.draw();





    }
}
