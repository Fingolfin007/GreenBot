package com.iot.ece.greenbot;

/**
 * Created by xunda on 3/1/2017.
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.iot.ece.greenbot.SplineInterpolator.*;

public class LstPaint {
    private ImageView image;
    private String mystring=" ";
    private float[] nums;

	// without input data
	// use random 
    public LstPaint (ImageView image) {

        this.image = image;
        nums = new float[8];
        int s = nums.length;

        Random rand = new Random();
        for (int i=0;i<s;i++)
            nums[i]=1+rand.nextInt(14);
    }


    // with input string
    public LstPaint (ImageView image, String s) {

        this(image);
        mystring=s;

    }



    //with input data and string
    public LstPaint (ImageView image, String s, float[] nums) {

        this.image = image;
        mystring=s;
        this.nums=nums;

        int len = nums.length;

        float fmin = 999999;
        float fmax = 0;

        for (int i=0;i<len ;i++){
            if (nums[i]<fmin) fmin = nums[i];
            if (nums[i]>fmax) fmax = nums[i];



        }

		// kind of normalize to fit the screen

        float range = fmax -fmin;
        if (range == 0 ) range =1;

        for (int i=0;i<len ;i++) {

            nums[i] = 4 + 5 * (nums[i]-fmin)/range;
        }





    }




    public void draw() {

        int red,green,blue;


        Bitmap bitmap = Bitmap.createBitmap(1500, 1500, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(20);


        Rect r=new Rect();


        int s = nums.length;

        List<Float> x = new ArrayList<>();
        List<Float> y = new ArrayList<>();

        int start_pic_left=200;
        int start_pic_right=260;


		// draw histogram

        for (int i=0;i<s;i++) {
            r.left = start_pic_left + i*110;
            r.top = (int) (1300-nums[i]*60);
            r.right = start_pic_right + i*110;
            r.bottom = 1300;
            x.add( Float.valueOf(((float)(r.left+r.right))/2));
            y.add(Float.valueOf((float)r.top));



            blue= (int) ( 128 * ( nums[i] / 10 ) );
            blue = blue>255?blue:255;
            green = 233-  (int) ( 244* ( nums[i] / 20 ) );
            red = 34;


            paint.setColor(Color.rgb(red,green,blue));

            canvas.drawRect(r, paint);
        }

		// draw curve

        int draw_end = (r.left+r.right)/2;




        SplineInterpolator si = SplineInterpolator.createMonotoneCubicSpline(x,y);


        paint.setColor(Color.BLUE);


        for (int j= (start_pic_right+start_pic_left)/2;j<draw_end;j++) {

            float height = si.interpolate(j);


            red = (int) ( (1 - height/1300f) * 233 );
            green = 12 + (int) ( (height/2400f) * 233 );
            blue =25 + (int) ( (1 - height/1200f) * 400 );

            paint.setColor(Color.rgb(red,green,blue));
            canvas.drawPoint((float) j, height, paint);


        }


		// draw title

        paint.setColor(Color.BLUE);
        paint.setTextSize(50);


        TextPaint tp = new TextPaint();

        tp.setColor(Color.rgb(123,40,128));

        tp.setStyle(Paint.Style.FILL);
        tp.setTextSize(90);

        StaticLayout myStaticLayout = new StaticLayout(mystring, tp, canvas.getWidth(),
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);

        myStaticLayout.draw(canvas);



        image.setImageBitmap(bitmap);

    }
}








