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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.R;
import com.rey.material.widget.ProgressView;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity
{
  Button Register;
  Activity act;
  Button back;
  TextInputLayout lblmail;
  TextInputLayout lblpass1;
  TextInputLayout lblpass2;
  TextInputLayout lblusername;
  EditText mail;
  EditText pass1;
  EditText pass2;
  ProgressView pv;
  private Typeface tf;
  EditText username;

  public String getIMEI()
  {
    if (ContextCompat.checkSelfPermission(this.act, "android.permission.READ_PHONE_STATE") == 0)
      return  ((TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    return "000";
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
    setContentView(R.layout.register);
    this.pv = ((ProgressView)findViewById(R.id.loading));
    this.lblusername = ((TextInputLayout)findViewById(R.id.usernameWrapper));
    this.lblpass1 = ((TextInputLayout)findViewById(R.id.passWrapper1));
    this.lblpass2 = ((TextInputLayout)findViewById(R.id.passWrapper2));
    this.lblmail = ((TextInputLayout)findViewById(R.id.emailWrapper));
    this.username = ((EditText)findViewById(R.id.username));
    this.pass1 = ((EditText)findViewById(R.id.pass1));
    this.pass2 = ((EditText)findViewById(R.id.pass2));
    this.mail = ((EditText)findViewById(R.id.email));
    this.tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
    this.Register = ((Button)findViewById(R.id.register));
    this.back = ((Button)findViewById(R.id.back));
    this.Register.setTypeface(this.tf);
    this.back.setTypeface(this.tf);
    this.lblusername.setTypeface(this.tf);
    this.lblpass1.setTypeface(this.tf);
    this.lblpass2.setTypeface(this.tf);
    this.lblmail.setTypeface(this.tf);
    this.back.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
       finish();
       overridePendingTransition(2131034137, 2131034138);
      }
    });
    this.Register.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if ((Register.this.username.getText().equals("")) || (Register.this.username.getText().length() < 3))
        {
         username.setError("نام کاربری وارد شده معتبر نیست");
          return;
        }
        if ((Register.this.pass1.getText().equals("")) || (Register.this.pass1.getText().length() < 4))
        {
         pass1.setError("پسور وارد شده معتبر نیست");
          return;
        }
        if ((Register.this.pass2.getText().equals("")) || (Register.this.pass2.getText().length() < 4))
        {
         pass2.setError("پسور وارد شده معتبر نیست");
          return;
        }
        if (!Register.this.pass2.getText().toString().equals(Register.this.pass1.getText().toString()))
        {
         pass2.setError("پسورد های وارد شده با هم مطابقت ندارند");
          return;
        }
        if ((Register.this.mail.getText().equals("")) || (Register.this.mail.getText().length() < 5) || (!Register.this.mail.getText().toString().contains("@")) || (!Register.this.mail.getText().toString().contains(".")))
        {
         mail.setError("ایمیل وارد شده معتبر نیست");
          return;
        }
       pv.start();
      register(Register.this.username.getText().toString(),pass1.getText().toString(),pass2.getText().toString(),mail.getText().toString(),getIMEI());
      }
    });
  }

  public void register(final String _username, final String _pass1,final String _pass2,final String _mail,final String _IMEI)
  {


    Volley.newRequestQueue(this).add(new StringRequest(Request.Method.POST, "http://persianstory.ir/JsonProfile/register", new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        if (response.contains("true"))
        {
         pv.stop();
          SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(Register.this.act).edit();
          localEditor.putString("u",_username);
          localEditor.putString("us", _username);
          try
          {
            localEditor.putString("h", Base64.encodeToString(pass1.getText().toString().getBytes("UTF-8"), 0));
            localEditor.putString("p", "0");
            localEditor.apply();
            Toast.makeText(Register.this.getApplicationContext(), "حساب شما با موفقیت ایجاد شد", 0).show();
           startActivity(new Intent(Register.this, MainActivity.class));
           overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
           finish();
            return;
          }
          catch (UnsupportedEncodingException localUnsupportedEncodingException)
          {
            localUnsupportedEncodingException.printStackTrace();
          }
        }
        Toast.makeText(Register.this.getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
       pv.stop();

      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        Log.e("asdasdasd","asdasdasd");
      }
    }){
      @Override
      protected Map<String,String> getParams(){
        Map<String,String> localHashMap = new HashMap<String, String>();

        localHashMap.put("u", _username);
        localHashMap.put("h", _pass1);
        localHashMap.put("hh", _pass2);
        localHashMap.put("email", _mail);
        localHashMap.put("a", _IMEI);

        return localHashMap;
      }
    });



  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Register
 * JD-Core Version:    0.6.0
 */