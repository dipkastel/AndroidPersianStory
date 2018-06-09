package com.bloom.persianstory.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bloom.persianstory.Application;
import com.bloom.persianstory.BaseActivity;
import com.bloom.persianstory.R;
import com.bloom.persianstory.model.Network.ConnectivityReceiver;
import com.bloom.persianstory.model.entities.Response.OperationResult;
import com.bloom.persianstory.model.entities.Response.User;
import com.bloom.persianstory.model.util.SharedPreference;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends BaseActivity {
    private int splashTime = 3000;
    LinearLayout LL_error ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LL_error = findViewById(R.id.LL_error);
        LL_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConnectivityReceiver.isConnected(Splash.this)){
                    Login();
                }else{
                    Snackbar.make(findViewById(R.id.View_Splash), "خطا در اتصال به اینترنت", 0).show();
                    error("خطا در دریافت اطلاعات");
                }
            }
        });
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.move_up);
        findViewById(R.id.splash_erth).startAnimation(hyperspaceJumpAnimation);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setOnConnectionChange(Splash.this);
                if(ConnectivityReceiver.isConnected(Splash.this)){
                    Login();
                }else{
                    Snackbar.make(findViewById(R.id.View_Splash), "خطا در اتصال به اینترنت", 0).show();
                    error("خطا در دریافت اطلاعات");
                }
            }
        }, splashTime);
    }

    public void Login(){
        LL_error.setVisibility(View.GONE);
        Map<String,String> params = new HashMap<String, String>();
        params.put("phoneNumber", new SharedPreference(this).getUser().getPhoneNumber());
        params.put("password", new SharedPreference(this).getUser().getPassword());
        params.put("deviceId", new SharedPreference(this).getUser().getDevice_id());

        Callback<OperationResult<User>> callback = new Callback<OperationResult<User>>() {
            @Override
            public void onResponse(Call<OperationResult<User>> call, Response<OperationResult<User>> response) {
                Log.e("response",response.toString());
              if(response.body().isOk(response.body())){
                  finish();
                  startActivity(new Intent(Splash.this, MainActivity.class));
                  overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
              }else{
                  error("خطا در دریافت اطلاعات");
              }
            }
            @Override
            public void onFailure(Call<OperationResult<User>> call, Throwable t) {
                error("خطا در دریافت اطلاعات");

            }
        };
         Application.getInstance().server().Login(params).enqueue(callback);
    }

    private void error(String message) {
        LL_error.setVisibility(View.VISIBLE);
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnect) {
        if(isConnect){
        }else{
            Snackbar.make(findViewById(R.id.View_Splash), "خطا در اتصال به اینترنت", 0).show();
        }


    }
}
