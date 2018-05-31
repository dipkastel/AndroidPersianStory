package com.bloom.persianstory.controller;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bloom.persianstory.R;
import com.bloom.persianstory.model.Network.ConnectivityReceiver;
import com.rey.material.widget.RelativeLayout;
import com.rey.material.widget.SnackBar;

import static android.support.v4.content.ContextCompat.startActivity;

public class Splash extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private int splashTime = 3000;
    ConnectivityReceiver connectivityReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        View View_Splash = findViewById(R.id.View_Splash);

        connectivityReceiver = new ConnectivityReceiver();
        connectivityReceiver.setConnectivityReceiverListener(this);

        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.move_up);
        findViewById(R.id.splash_erth).startAnimation(hyperspaceJumpAnimation);

        final Snackbar connectionError = Snackbar.make(View_Splash, "خطا در اتصال به اینترنت", 0);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(ConnectivityReceiver.isConnected(Splash.this)){
                        finish();
                        startActivity(new Intent(Splash.this, Welcome.class));
                        overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
                }else{
                    connectionError.show();
                }
            }
        }, splashTime);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnect) {
        if(isConnect){
            this.finish();
            startActivity(new Intent(Splash.this, Welcome.class));
            overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
        }


    }


    @Override
    protected void onDestroy() {
        connectivityReceiver.setConnectivityReceiverListener(null);
        super.onDestroy();
    }
}
