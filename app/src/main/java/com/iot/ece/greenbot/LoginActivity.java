package com.iot.ece.greenbot;
import android.util.Log;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{
    static final String LOG_TAG = "greenbot_log";

    private Button lg_button;
    private Button sg_button;
    private SignInButton signIn;

    private ImageView mImageView;
    private GoogleApiClient mgoogleApiClient;
    private static final int REQ_CODE = 9001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(LoginActivity.LOG_TAG,"in on create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }


        findViewById(R.id.gbutton_signin).setOnClickListener(this);
//        signIn = (SignInButton) findViewById(R.id.gbutton_signin);
//        if (signIn == null) {
//            Log.e(this.LOG_TAG, "Is NULL");
//
//        }
//    //    signIn.setOnClickListener(this);
//        signIn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Log.e(LoginActivity.LOG_TAG, "Is inonclick");
//                doSignIn();
//            }
//            //}
//        });

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mgoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        setContentView(R.layout.activity_login);
        mImageView = (ImageView) findViewById(R.id.leaf_icon_image);

//        lg_button = (Button) findViewById(R.id.button_login);
//        lg_button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent finishIntent = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(finishIntent);
//            }
//            //}
//        });

//        sg_button = (Button) findViewById(R.id.button_signup);
//        sg_button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent finishIntent = new Intent(LoginActivity.this, SignUpActivity.class);
//                startActivity(finishIntent);
//            }
//            //}
//        });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

//    @Override
//    public void onClick(View v) {
//
//    }

    private void doSignIn() {

        Log.d(LoginActivity.LOG_TAG,"in doSignIn");
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(mgoogleApiClient);
        startActivityForResult(intent, REQ_CODE);


    }

    protected void onActivityResult(int request_code, int result_code, Intent data) {
        super.onActivityResult(request_code, result_code, data);
        Log.d(LoginActivity.LOG_TAG,"in on activity result");
        if (request_code==REQ_CODE) {
            Log.d(LoginActivity.LOG_TAG,"in on activity result with correct req code");
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

    private void handleResult(GoogleSignInResult result) {
        Log.d(LoginActivity.LOG_TAG,"in handleresult");

        if (result.isSuccess()) {
            Log.d(LoginActivity.LOG_TAG,"in handle successful result");
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            handleTransition(true);
        } else {
            handleTransition(false);
        }
    }

    private void handleTransition(boolean isLogin) {
        if (isLogin) {
            Log.d(LoginActivity.LOG_TAG,"in handle transition");
            Intent finishIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(finishIntent);
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(LoginActivity.LOG_TAG,"in onclick");
        switch (v.getId()) {

            case R.id.gbutton_signin:
                Log.d(LoginActivity.LOG_TAG,"in onclick signup button");
                doSignIn();
                break;

        }
    }
}

