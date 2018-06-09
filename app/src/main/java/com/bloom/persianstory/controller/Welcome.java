package com.bloom.persianstory.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.R;
import com.bloom.persianstory.model.Network.ConnectivityReceiver;

import java.io.UnsupportedEncodingException;

public class Welcome extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
    private static String[] PERMISSIONS_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE"};

    String imei;
    String price;
    String pw;
    String userid;


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.welcome);
//    OneSignal.startInit(this).init();
        SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userid = localSharedPreferences.getString("us", "");
        imei = localSharedPreferences.getString("u", "");
        price = localSharedPreferences.getString("p", "");
        pw = localSharedPreferences.getString("h", "");
        try {
            pw = new String(Base64.decode(pw, 0), "UTF-8");
            int BuildVERSION = Build.VERSION.SDK_INT;

            if ((BuildVERSION >= 23) && (ContextCompat.checkSelfPermission(this, "android.permission.READ_PHONE_STATE") != 0))
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
            return;
        } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
            localUnsupportedEncodingException.printStackTrace();
        }
    }
    private void showSnack(boolean isConnect) {
        Snackbar localSnackbar = Snackbar.make(((CoordinatorLayout) findViewById(R.id.c1)), "خطا در اتصال به اینترنت", 0);
        Handler localHandler = new Handler();
        Runnable local2 = new Runnable() {
            public void run() {

                showSnack(Boolean.valueOf(ConnectivityReceiver.isConnected(getApplicationContext())).booleanValue());
            }
        };
        ((TextView) localSnackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(-256);
        if (!isConnect) {
            localHandler.postDelayed(local2, 6000L);
            localSnackbar.show();
            if (userid.toString().isEmpty())
                return;
            if (userid.equals("مهمان")) {
                localHandler.removeCallbacks(local2);
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return;
            }
            localHandler.removeCallbacks(local2);
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        if ((!userid.equals("مهمان")) || (userid.toString().isEmpty()) || (Integer.valueOf(price).intValue() > 0)) {
            check(userid, imei, pw);
            return;
        }
        if (userid.toString().isEmpty()) {
            startActivity(new Intent(this, Home.class));
            finish();
            return;
        }
        if (userid.equals("مهمان")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        startActivity(new Intent(this, Home.class));
        finish();
    }

    //TODO comment
    public void check(String paramString1, String paramString2, String paramString3) {
        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET, "http://persianstory.ir/JsonProfile/check", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("aa", response);
                String[] arrayOfString = response.split("--tg--");
                if (arrayOfString[0].contains("1")) {
                    Toast.makeText(getApplicationContext(), "حساب شما توسط مدیر مسدود شده است", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Welcome.this, Home.class));
                    finish();
                    return;
                }
                if (arrayOfString[0].contains("6")) {
                    startActivity(new Intent(Welcome.this, Home.class));
                    finish();
                    return;
                }
                SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                localEditor.putString("p", arrayOfString[1].toString());
                localEditor.apply();
                startActivity(new Intent(Welcome.this, MainActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("asdasdasd", "asdasdasd");
            }
        }));

    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }



    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

    public void onNetworkConnectionChanged(boolean paramBoolean) {
        showSnack(paramBoolean);
    }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.allStories.Welcome
 * JD-Core Version:    0.6.0
 */