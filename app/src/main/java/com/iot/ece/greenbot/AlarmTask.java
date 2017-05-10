package com.iot.ece.greenbot;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.TimerTask;




/**
 * Created by lst2016 on 4/24/17.
 */


// with notification

public class AlarmTask extends TimerTask {

    private Context con;
    private static final String WATER_URL =
            "https://maker.ifttt.com/trigger/callPump/with/key/bbVY04YYbiLvYn8Jea_L0B";

    public AlarmTask( Context con) {
        this.con = con;
    }
    @Override
    public void run() {

        HttpsTrustManager.allowAllSSL();

        /****************https start ********************/



//        URL url = null;
//        try {
//            url = new URL("https://en.wikipedia.org/wiki/Main_Page"); //https://maker.ifttt.com/trigger/callPump/with/key/bbVY04YYbiLvYn8Jea_L0B
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        String inputLine=null;
//
//        HttpURLConnection yc = null;
//        try {
//            yc = (HttpURLConnection) url.openConnection();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("Wait");
//        int counter = 0;
//        while(true)
//        {
//            boolean flag=false;
//            try {
//                yc = (HttpURLConnection) url.openConnection();
//                if (yc.getResponseCode()==200)
//                    flag=true;
//            }
//            catch (Exception e)
//            { //
//            };
//            if (flag==true ) break;
//            counter++;
//            if (counter >= 1000) {
//                break;
//            }
//        }
//        System.out.println("W?");
//
//        BufferedReader in = null;
//        try {
//            in = new BufferedReader(  new InputStreamReader(yc.getInputStream()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            while ((inputLine = in.readLine()) != null) {
//                System.out.println(inputLine);
//                break;
//
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("What?");

        /****************https end ********************/

        // Use this instead of the above https start-end block
        sendRequest();








        /**************try notification here *********************/


        //ref : https://developer.android.com/guide/topics/ui/notifiers/notifications.html
        // MUST have an icon

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(con)
                        .setContentTitle("Green")
                        .setContentText("Your plant has been watered ~~")
                        .setLights(Color.MAGENTA, 800, 800)
                        .setPriority(2).setSmallIcon(R.drawable.leaf_icon);


        //.setSmallIcon(R.drawable.notification_icon)


// Creates an explicit intent for an Activity in your app
        //TODO
        //MainActivity main change to another class
        Intent resultIntent = new Intent(con, SchedulingActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(con);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(SchedulingActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) con.getSystemService(Context.NOTIFICATION_SERVICE);
//// mId allows you to update the notification later on.
//        int mId = 12;
        mNotificationManager.notify(0, mBuilder.build());

        // go to android monitor to check

        System.out.println("\nwait!\nShould work in background?");

        /**************try notification here *********************/



    }




    public void sendRequest() {
        HttpsTrustManager.allowAllSSL();
        RequestQueue mRequestQueue = Volley.newRequestQueue(con);

        StringRequest stringRequest4 = new StringRequest(Request.Method.POST, WATER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String s = response.toString();
//                        resultView.setText("Plant Watered!");
//                        resultView.setVisibility(View.VISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String s_error = "ERROR!";
//                resultView.setText(s_error);
//                resultView.setVisibility(View.VISIBLE);
            }
        }
        );
        mRequestQueue.add(stringRequest4);
    }
}
