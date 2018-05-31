package com.bloom.persianstory.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.R;
import com.rey.material.widget.ProgressView;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity
{
  Button Login;
  Activity act;
  Button back;
  TextView forget;
  TextInputLayout lblpass;
  TextInputLayout lblusername;
  EditText pass;
  ProgressView pv;
  private Typeface tf;
  EditText username;

  public String getIMEI()
  {
    if (ContextCompat.checkSelfPermission(this.act, "android.permission.READ_PHONE_STATE") == 0)
      return ( ( TelephonyManager )getSystemService( Context.TELEPHONY_SERVICE )).getDeviceId();
    return "000";
  }

  public void login(final String username, final String pass, final String Imei)
  {
    RequestQueue localRequestQueue = Volley.newRequestQueue(getApplicationContext());
    localRequestQueue.add(new StringRequest(1, "http://persianstory.ir/JsonProfile/login", new Response.Listener<String>()
    {
      public void onResponse(String paramString)
      {
        Log.i("aa", paramString);
        if (paramString.contains("true"))
        {
          String[] arrayOfString = paramString.split("--tg--");
         pv.stop();
          SharedPreferences.Editor localEditor = null;
          if ((arrayOfString[1].toString().equals(username)) && (arrayOfString[0].toString().contains("true")) && (arrayOfString[3].toString().equals("0")))
          {
            localEditor = PreferenceManager.getDefaultSharedPreferences(Login.this.act).edit();
            localEditor.putString("u",getIMEI());
            localEditor.putString("p", arrayOfString[2].toString());
          }
          do
          {
            try
            {
              localEditor.putString("h", Base64.encodeToString(pass.getBytes("UTF-8"), 0));
              localEditor.putString("us", username);
              localEditor.apply();
             startActivity(new Intent(Login.this, MainActivity.class));
             overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
             finish();
              return;
            }
            catch (UnsupportedEncodingException localUnsupportedEncodingException)
            {
                localUnsupportedEncodingException.printStackTrace();
            }
            if (arrayOfString[3].toString().equals("0"))
            Toast.makeText(Login.this.getApplicationContext(), "حساب شما توسط مدیر بسته شده است", 1).show();
          }
          while ((arrayOfString[0].toString().contains("true")) || (!arrayOfString[1].toString().equals(username)));
          Toast.makeText(Login.this.getApplicationContext(), "عملیات نا مشخص", 1).show();
          return;
        }
        Log.i("aa", paramString + "");
        Toast.makeText(Login.this.getApplicationContext(), paramString, 1).show();
       pv.stop();
      }
    }
    , new Response.ErrorListener()
    {
      public void onErrorResponse(VolleyError paramVolleyError)
      {
       pv.stop();
      }
    }
    )
    {
        @Override
      protected Map<String, String> getParams()
      {
          HashMap localHashMap = new HashMap();
          localHashMap.put("u", username);
          localHashMap.put("h", pass);
          localHashMap.put("a", "tg_"+Imei);
          return localHashMap;
      }
    });
  }

  public void onBackPressed()
  {
    super.onBackPressed();
    finish();
    overridePendingTransition(R.anim.slide2 , R.anim.slide2_out);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.act = this;
    setContentView(R.layout.login);
    this.pv = ((ProgressView)findViewById(R.id.loading));
    this.lblusername = ((TextInputLayout)findViewById(R.id.usernameWrapper));
    this.lblpass = ((TextInputLayout)findViewById(R.id.passWrapper1));
    this.username = ((EditText)findViewById(R.id.username));
    this.pass = ((EditText)findViewById(R.id.pass1));
    this.tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
    this.Login = ((Button)findViewById(R.id.login));
    this.back = ((Button)findViewById(R.id.back));
    this.forget = ((TextView)findViewById(R.id.forget));
    this.Login.setTypeface(this.tf);
    this.back.setTypeface(this.tf);
    this.back.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
       finish();
       overridePendingTransition(2131034137, 2131034138);
      }
    });
    this.lblusername.setTypeface(this.tf);
    this.lblpass.setTypeface(this.tf);
    this.forget.setTypeface(this.tf);
    this.forget.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
       startActivity(new Intent(Login.this, Forget.class));
       overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
      }
    });
    this.Login.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if ((Login.this.username.getText().equals("")) || (Login.this.username.getText().length() < 3))
        {
         username.setError("نام کاربری وارد شده مورد قبول نیست");
          return;
        }
        if ((Login.this.pass.getText().equals("")) || (Login.this.pass.getText().length() < 4))
        {
         pass.setError("پسور وارد شده معتبر نیست");
          return;
        }
       pv.start();
       login(Login.this.username.getText().toString(),pass.getText().toString(),getIMEI());
      }
    });
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Login
 * JD-Core Version:    0.6.0
 */