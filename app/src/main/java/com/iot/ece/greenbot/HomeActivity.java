package com.iot.ece.greenbot;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PermissionGroupInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HomeActivity extends AppCompatActivity {

    public long buttonTime = 6000;
    public Handler buttonTimeHandler;
    public ImageButton imageButton;
    public ProgressBar progressBar;
    public Button infoButton;
    public TextView resultView;
    private static final int WATER_COLOR = 0xFF3171D8;
    private static final String WATER_URL =
            "https://maker.ifttt.com/trigger/callPump/with/key/bbVY04YYbiLvYn8Jea_L0B";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        setContentView(R.layout.activity_home);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        resultView = (TextView) findViewById(R.id.result_text);
        resultView.setVisibility(View.INVISIBLE);


//        int color = 0xFF3171D8;
        progressBar.getIndeterminateDrawable().setColorFilter(WATER_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.getProgressDrawable().setColorFilter(WATER_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar.setVisibility(View.INVISIBLE);

        imageButton = (ImageButton) findViewById(R.id.leaf_icon_button);
        imageButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 0, 500); // see this max value coming back here, we animale towards that value

                animation.setInterpolator(new CycleInterpolator(3));
                animation.start();
                progressBar.setVisibility(View.VISIBLE);
                animation.setDuration(1000); //in milliseconds
                animation.end();

                buttonTimeHandler = new Handler();

                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        buttonTime = buttonTime - 1000;
                        if (buttonTime == 4000) {
                            sendRequest();
                        }

                        if (buttonTime >= 0) {
                            buttonTimeHandler.postDelayed(this, 1000);
                        } else {
                            resultView.setVisibility(View.INVISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                        }


                    }
                };

                buttonTimeHandler.postDelayed(runnable, 1000);
                buttonTime = 5000;
                resultView.setVisibility(View.INVISIBLE);


                progressBar.clearAnimation();
            }
        });
        progressBar.setVisibility(View.INVISIBLE);

        infoButton = (Button) findViewById(R.id.button_info);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infoIntent = new Intent(HomeActivity.this, InfoActivity.class);
                startActivity(infoIntent);
            }
        });



    }

    public void sendRequest() {
        HttpsTrustManager.allowAllSSL();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest4 = new StringRequest(Request.Method.POST, WATER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String s = response.toString();
                        resultView.setText("Plant Watered!");
                        resultView.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String s_error = "ERROR!";
                resultView.setText(s_error);
                resultView.setVisibility(View.VISIBLE);
            }
        }
        );
        mRequestQueue.add(stringRequest4);
    }



}
