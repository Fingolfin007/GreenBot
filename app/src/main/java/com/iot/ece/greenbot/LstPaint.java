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




        //canvas.drawCircle(50, 50, 50, paint);

        Rect r=new Rect();


        int s = nums.length;

        List<Float> x = new ArrayList<>();
        List<Float> y = new ArrayList<>();

        int start_pic_left=200;
        int start_pic_right=260;




        for (int i=0;i<s;i++) {
            r.left = start_pic_left + i*110;
            r.top = (int) (1300-nums[i]*60);
            r.right = start_pic_right + i*110;
            r.bottom = 1300;
            x.add( Float.valueOf(((float)(r.left+r.right))/2));
            y.add(Float.valueOf((float)r.top));




//            this is for sunlight

//            red= (int) ( 255 * ( nums[i] / 10 ) );
//            red = red>255?red:255;
//            green = 212-  (int) ( 222* ( nums[i] / 10 ) );
//            blue = 34;


            //this is for moisture


            blue= (int) ( 128 * ( nums[i] / 10 ) );
            blue = blue>255?blue:255;
            green = 233-  (int) ( 244* ( nums[i] / 20 ) );
            red = 34;


            paint.setColor(Color.rgb(red,green,blue));

            canvas.drawRect(r, paint);
        }

        int draw_end = (r.left+r.right)/2;




        SplineInterpolator si = SplineInterpolator.createMonotoneCubicSpline(x,y);


        paint.setColor(Color.BLUE);

//        paint.setColor(Color.YELLOW);

        for (int j= (start_pic_right+start_pic_left)/2;j<draw_end;j++) {

            float height = si.interpolate(j);


            red = (int) ( (1 - height/1300f) * 233 );
            green = 12 + (int) ( (height/2400f) * 233 );
            blue =25 + (int) ( (1 - height/1200f) * 400 );

            paint.setColor(Color.rgb(red,green,blue));
            canvas.drawPoint((float) j, height, paint);


        }


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





//// 自定义 view,需要实现 一个显式的构造函数，重写 onDraw 方法，一切的操作都在该方法上进行
//public class LstPaint extends View {
//
//    public LstPaint(Context context) {
//        super(context);
//    }
//
//    /**
//     * 要画图形，最起码要有三个对象：
//     * 1.颜色对象 Color
//     * 2.画笔对象 Paint
//     * 3.画布对象 Canvas
//     */
//
//    @Override
//    public void onDraw(Canvas canvas) {
//        // TODO Auto-generated method stub
//
//        Paint paint = new Paint();
//        paint.setColor(Color.BLUE);
//        //设置字体大小
//        paint.setTextSize(100);
//
//        //让画出的图形是空心的
//        paint.setStyle(Paint.Style.STROKE);
//        //设置画出的线的 粗细程度
//        paint.setStrokeWidth(5);
//        //画出一根线
//        canvas.drawLine(0, 0, 200, 200, paint);
//
//        //画矩形
//        canvas.drawRect(200, 500, 300, 300, paint);
//
//        //画圆
//        canvas.drawCircle(200, 200, 100, paint);
//        //画出字符串 drawText(String text, float x, float y, Paint paint)
//        // y 是 基准线 ，不是 字符串的 底部
//        canvas.drawText("apple", 60, 60, paint);
//        canvas.drawLine(0, 60, 500, 60, paint);
//
//        //绘制图片
//        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher), 150, 150, paint);
//
//        super.onDraw(canvas);
//    }
//}



