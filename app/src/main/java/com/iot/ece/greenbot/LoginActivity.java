package com.iot.ece.greenbot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity {

    private Button lg_button;
    private Button sg_button;

    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_login);
        mImageView = (ImageView) findViewById(R.id.leaf_icon_image);

        lg_button = (Button) findViewById(R.id.button_login);
        lg_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent finishIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(finishIntent);
            }
            //}
        });

    }
}

