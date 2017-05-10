package com.iot.ece.greenbot;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener{

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

        drawData();







    }



    public void drawData() {



        //https requets

        RequestQueue mRequestQueue = Volley.newRequestQueue(this);


        HttpsTrustManager.allowAllSSL();
        //ImageView image = (ImageView) findViewById(R.id.x7);

        String url = "";

        url="https://api.spark.io/v1/devices/360032001051353338363333/light?access_token=3758bef3d1d0ab60b473b5c5d500d2a260902efb";
        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ImageView image = (ImageView) findViewById(R.id.sunlight_level_chart);

                        String res = response.toString();
                        Map<String, Object> retMap = new Gson().fromJson(
                                res, new TypeToken<HashMap<String, Object>>() {}.getType()
                        );
                        String number = retMap.get("result").toString();
                        System.out.println("data is： " + number);


                        LstPaint lst = new LstPaint(image,"LIGHT! "+retMap.get("result").toString(),StrToArray.convert(number) );


                        lst.draw();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ImageView image = (ImageView) findViewById(R.id.sunlight_level_chart);
                LstPaint lst = new LstPaint(image,"LIGHT! did not work!");
                lst.draw();
            }
        }
        ) ;


        url="https://api.spark.io/v1/devices/360032001051353338363333/moisture?access_token=3758bef3d1d0ab60b473b5c5d500d2a260902efb";
        StringRequest stringRequest4 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ImageView image = (ImageView) findViewById(R.id.moisture_level_chart);

                        String res = response.toString();
                        Map<String, Object> retMap = new Gson().fromJson(
                                res, new TypeToken<HashMap<String, Object>>() {}.getType()
                        );
                        String number = retMap.get("result").toString();
                        System.out.println("data is： " + number);


                        LstPaint lst = new LstPaint(image,"Moisture "+retMap.get("result").toString(),StrToArray.convert(number) );


                        lst.draw();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ImageView image = (ImageView) findViewById(R.id.moisture_level_chart);
                LstPaint lst = new LstPaint(image,"Moisture did not work!");
                lst.draw();
            }
        }
        ) ;






        mRequestQueue.add(stringRequest3 );
        mRequestQueue.add(stringRequest4 );

        //https requets


    }

    @Override
    public void onClick(View v) {
        drawData();
    }
}
