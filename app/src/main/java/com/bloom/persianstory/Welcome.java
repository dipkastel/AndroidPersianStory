package com.bloom.persianstory;

import android.app.Activity;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rey.material.widget.ProgressView;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Welcome extends AppCompatActivity
  implements ConnectivityReceiver.ConnectivityReceiverListener
{
  private static String[] PERMISSIONS_STORAGE = { "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE" };
  private static final int REQUEST_EXTERNAL_STORAGE = 1;
  Activity act;
  CoordinatorLayout cr;
  String imei;
  Boolean isConnected;
  String price;
  ProgressView pv;
  String pw;
  String userid;

  private void checkConnection()
  {
    this.isConnected = Boolean.valueOf(ConnectivityReceiver.isConnected(this));
    showSnack(this.isConnected.booleanValue());
  }

  private void showSnack(boolean paramBoolean)
  {
    Snackbar localSnackbar = Snackbar.make(this.cr, "خطا در اتصال به اینترنت", 0);
    Handler localHandler = new Handler();
    Runnable local2 = new Runnable()
    {
      public void run()
      {
        Welcome.this.checkConnection();
      }
    };
    ((TextView)localSnackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(-256);
    if (!paramBoolean)
    {
      localHandler.postDelayed(local2, 6000L);
      localSnackbar.show();
      if (this.userid.toString().isEmpty())
        return;
      if (this.userid.equals("مهمان"))
      {
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
    if ((!this.userid.equals("مهمان")) || (this.userid.toString().isEmpty()) || (Integer.valueOf(this.price).intValue() > 0))
    {
      check(this.userid, this.imei, this.pw);
      return;
    }
    if (this.userid.toString().isEmpty())
    {
      startActivity(new Intent(this, Home.class));
      finish();
      return;
    }
    if (this.userid.equals("مهمان"))
    {
      startActivity(new Intent(this, MainActivity.class));
      finish();
      return;
    }
    startActivity(new Intent(this, Home.class));
    finish();
  }
//TODO comment
  public void check(String paramString1, String paramString2, String paramString3)
  {
    Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET, "http://" +
            "persianstory.ir/JsonProfile/check", new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        Log.i("aa", response);
        String[] arrayOfString = response.split("--tg--");
        if (arrayOfString[0].contains("1"))
        {
          Toast.makeText(Welcome.this.getApplicationContext(), "حساب شما توسط مدیر مسدود شده است", Toast.LENGTH_SHORT).show();
          Welcome.this.startActivity(new Intent(Welcome.this, Home.class));
          Welcome.this.finish();
          return;
        }
        if (arrayOfString[0].contains("6"))
        {
          Welcome.this.startActivity(new Intent(Welcome.this, Home.class));
          Welcome.this.finish();
          return;
        }
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(Welcome.this.act).edit();
        localEditor.putString("p", arrayOfString[1].toString());
        localEditor.apply();
        Welcome.this.startActivity(new Intent(Welcome.this, MainActivity.class));
        Welcome.this.finish();
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e("asdasdasd","asdasdasd");
      }
    }));

  }

  public void onBackPressed()
  {
    super.onBackPressed();
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.welcome);
//    OneSignal.startInit(this).init();
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    this.userid = localSharedPreferences.getString("us", "");
    this.imei = localSharedPreferences.getString("u", "");
    this.price = localSharedPreferences.getString("p", "");
    this.pw = localSharedPreferences.getString("h", "");
    try
    {
      this.pw = new String(Base64.decode(this.pw, 0), "UTF-8");
      this.cr = ((CoordinatorLayout)findViewById(R.id.c1));
      int i = Build.VERSION.SDK_INT;
      this.act = this;
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          Welcome.this.checkConnection();
        }
      }
      , 1500L);
      if ((i >= 23) && (ContextCompat.checkSelfPermission(this.act, "android.permission.READ_PHONE_STATE") != 0))
        ActivityCompat.requestPermissions(this.act, PERMISSIONS_STORAGE, 1);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        localUnsupportedEncodingException.printStackTrace();
    }
  }

  protected void onDestroy()
  {
    finish();
    super.onDestroy();
  }

  public void onNetworkConnectionChanged(boolean paramBoolean)
  {
    showSnack(paramBoolean);
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Welcome
 * JD-Core Version:    0.6.0
 */