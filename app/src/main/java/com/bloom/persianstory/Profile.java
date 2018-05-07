package com.bloom.persianstory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity
{
  Activity act;
  Button back;
  Button buy;
  TextView count;
  String countString;
  String imei;
  TextView lblcount;
  Button logout;
  TextView name;
  ProgressDialog pd;
  String pw;
  Typeface tf;
  String username;

//  public void check(String paramString1, String paramString2, String paramString3)
//  {
//    Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(1, "http://bahar.persianstory.ir/JsonProfile/check", new Response.Listener<String>()
//    {
//      public void onResponse(String paramString)
//      {
//        Log.i("aa", paramString);
//        String[] arrayOfString = paramString.split("--tg--");
//        if (arrayOfString[0].contains("1"))
//        {
//          Toast.makeText(Profile.this.getApplicationContext(), "حساب شما توسط مدیر مسدود شده است", 1).show();
//          Profile.this.startActivity(new Intent(Profile.this, Home.class));
//          Profile.this.overridePendingTransition(2131034135, 2131034136);
//          Profile.this.finish();
//          return;
//        }
//        if (arrayOfString[0].contains("6"))
//        {
//          Profile.this.startActivity(new Intent(Profile.this, Home.class));
//          Profile.this.overridePendingTransition(2131034135, 2131034136);
//          Profile.this.finish();
//          return;
//        }
//        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(Profile.this.act).edit();
//        localEditor.putString("p", arrayOfString[1].toString());
//        localEditor.apply();
//        Profile.this.count.setText(arrayOfString[1].toString());
//        Profile.this.pd.dismiss();
//      }
//    }
//    , new Response.ErrorListener()
//    {
//      public void onErrorResponse(VolleyError paramVolleyError)
//      {
//        Profile.this.pd.dismiss();
//      }
//    }
//    , paramString1, paramString3, paramString2)
//    {
//      protected Map<String, String> getParams()
//      {
//        try
//        {
//          HashMap localHashMap = new HashMap();
//          localHashMap.put("u", username);
//          localHashMap.put("h", pw);
//          localHashMap.put("a", imei);
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
    setContentView(R.layout.profile);
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    this.imei = localSharedPreferences.getString("u", "");
    this.username = localSharedPreferences.getString("us", "");
    this.pw = localSharedPreferences.getString("h", "");
    try
    {
      this.pw = new String(Base64.decode(this.pw, 0), "UTF-8");
      this.countString = localSharedPreferences.getString("p", "");
      if ((this.username.equals("")) || (this.username == null) || (this.username.isEmpty()))
      {
        startActivity(new Intent(this, Home.class));
        finish();
      }
      this.pd = new ProgressDialog(this);
      this.pd.setMessage("منتظر باشید...");
      this.pd.setCancelable(false);
      this.pd.show();
      this.tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
      this.buy = ((Button)findViewById(R.id.buy));
      this.back = ((Button)findViewById(R.id.back));
      this.logout = ((Button)findViewById(R.id.logout));
      this.count = ((TextView)findViewById(R.id.count));
      this.name = ((TextView)findViewById(R.id.name));
      this.lblcount = ((TextView)findViewById(R.id.lblcount));
      this.back.setTypeface(this.tf);
      this.buy.setTypeface(this.tf);
      this.logout.setTypeface(this.tf);
      if (this.username.equals("مهمان"))
      {
        this.buy.setText("ثبت نام");
        this.logout.setText("ورود به حساب");
        this.count.setVisibility(View.GONE);
        this.lblcount.setText("برای افزایش سکه و گوش کردن قصه های غیر رایگان باید ثبت نام کنید");
      }
      this.count.setTypeface(this.tf);
      this.name.setTypeface(this.tf);
      this.lblcount.setTypeface(this.tf);
      if (!this.username.equals("مهمان"))
      {
        //check(this.username, this.imei, this.pw);
        this.name.setText(this.username);
        this.count.setText(this.countString);
        this.back.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            Profile.this.finish();
            Profile.this.overridePendingTransition(R.anim.slide1, R.anim.slide2_out);
          }
        });
        this.buy.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            if (Profile.this.username.equals("مهمان"))
            {
              Profile.this.startActivity(new Intent(Profile.this, Register.class));
              Profile.this.overridePendingTransition(R.anim.slide1, R.anim.slide2_out);
              return;
            }
            Profile.this.startActivity(new Intent(Profile.this, Buy.class));
            Profile.this.overridePendingTransition(R.anim.slide1, R.anim.slide2_out);
          }
        });
        this.logout.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
            if (Profile.this.username.equals("مهمان"))
            {
              Profile.this.startActivity(new Intent(Profile.this, Login.class));
              Profile.this.overridePendingTransition(R.anim.slide1, R.anim.slide2_out);
              return;
            }
            SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(Profile.this.act).edit();
            localEditor.putString("us", "");
            localEditor.putString("p", "0");
            localEditor.apply();
            Profile.this.startActivity(new Intent(Profile.this, Home.class));
            Profile.this.overridePendingTransition(R.anim.slide1, R.anim.slide2_out);
            Profile.this.finish();
          }
        });
        return;
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
        localUnsupportedEncodingException.printStackTrace();

    }
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Profile
 * JD-Core Version:    0.6.0
 */