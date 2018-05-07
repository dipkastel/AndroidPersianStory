package com.bloom.persianstory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

//  public void login(String paramString1, String paramString2, String paramString3)
//  {
//    RequestQueue localRequestQueue = Volley.newRequestQueue(getApplicationContext());
//    String str = "tg_" + paramString3;
//    localRequestQueue.add(new StringRequest(1, "http://bahar.persianstory.ir/JsonProfile/login", new Response.Listener(paramString1, paramString2)
//    {
//      public void onResponse(String paramString)
//      {
//        Log.i("aa", paramString);
//        if (paramString.contains("true"))
//        {
//          String[] arrayOfString = paramString.split("--tg--");
//          Login.this.pv.stop();
//          SharedPreferences.Editor localEditor;
//          if ((arrayOfString[1].toString().equals(this.val$usernamee)) && (arrayOfString[0].toString().contains("true")) && (arrayOfString[3].toString().equals("0")))
//          {
//            localEditor = PreferenceManager.getDefaultSharedPreferences(Login.this.act).edit();
//            localEditor.putString("u", Login.this.getIMEI());
//            localEditor.putString("p", arrayOfString[2].toString());
//          }
//          do
//          {
//            try
//            {
//              localEditor.putString("h", Base64.encodeToString(this.val$passs.getBytes("UTF-8"), 0));
//              localEditor.putString("us", this.val$usernamee);
//              localEditor.apply();
//              Login.this.startActivity(new Intent(Login.this, MainActivity.class));
//              Login.this.overridePendingTransition(2131034135, 2131034136);
//              Login.this.finish();
//              return;
//            }
//            catch (UnsupportedEncodingException localUnsupportedEncodingException)
//            {
//              while (true)
//                localUnsupportedEncodingException.printStackTrace();
//            }
//            if (arrayOfString[3].toString().equals("0"))
//              continue;
//            Toast.makeText(Login.this.getApplicationContext(), "حساب شما توسط مدیر بسته شده است", 1).show();
//          }
//          while ((arrayOfString[0].toString().contains("true")) || (!arrayOfString[1].toString().equals(this.val$usernamee)));
//          Toast.makeText(Login.this.getApplicationContext(), "عملیات نا مشخص", 1).show();
//          return;
//        }
//        Log.i("aa", paramString + "");
//        Toast.makeText(Login.this.getApplicationContext(), paramString, 1).show();
//        Login.this.pv.stop();
//      }
//    }
//    , new Response.ErrorListener()
//    {
//      public void onErrorResponse(VolleyError paramVolleyError)
//      {
//        Login.this.pv.stop();
//      }
//    }
//    , paramString1, paramString2, str)
//    {
//      protected Map<String, String> getParams()
//      {
//        try
//        {
//          HashMap localHashMap = new HashMap();
//          localHashMap.put("u", this.val$usernamee);
//          localHashMap.put("h", this.val$passs);
//          localHashMap.put("a", this.val$keyy);
//          return localHashMap;
//        }
//        catch (Exception localException)
//        {
//        }
//        return null;
//      }
//    });
//  }

  public void onBackPressed()
  {
    super.onBackPressed();
    finish();
    overridePendingTransition(2131034137, 2131034138);
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
        Login.this.finish();
        Login.this.overridePendingTransition(2131034137, 2131034138);
      }
    });
    this.lblusername.setTypeface(this.tf);
    this.lblpass.setTypeface(this.tf);
    this.forget.setTypeface(this.tf);
    this.forget.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Login.this.startActivity(new Intent(Login.this, Forget.class));
        Login.this.overridePendingTransition(2131034135, 2131034136);
      }
    });
    this.Login.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if ((Login.this.username.getText().equals("")) || (Login.this.username.getText().length() < 3))
        {
          Login.this.username.setError("نام کاربری وارد شده مورد قبول نیست");
          return;
        }
        if ((Login.this.pass.getText().equals("")) || (Login.this.pass.getText().length() < 4))
        {
          Login.this.pass.setError("پسور وارد شده معتبر نیست");
          return;
        }
        Login.this.pv.start();
        //Login.this.login(Login.this.username.getText().toString(), Login.this.pass.getText().toString(), Login.this.getIMEI());
      }
    });
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Login
 * JD-Core Version:    0.6.0
 */