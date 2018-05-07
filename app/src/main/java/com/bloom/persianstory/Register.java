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
    overridePendingTransition(2131034137, 2131034138);
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
        Register.this.finish();
        Register.this.overridePendingTransition(2131034137, 2131034138);
      }
    });
    this.Register.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if ((Register.this.username.getText().equals("")) || (Register.this.username.getText().length() < 3))
        {
          Register.this.username.setError("نام کاربری وارد شده معتبر نیست");
          return;
        }
        if ((Register.this.pass1.getText().equals("")) || (Register.this.pass1.getText().length() < 4))
        {
          Register.this.pass1.setError("پسور وارد شده معتبر نیست");
          return;
        }
        if ((Register.this.pass2.getText().equals("")) || (Register.this.pass2.getText().length() < 4))
        {
          Register.this.pass2.setError("پسور وارد شده معتبر نیست");
          return;
        }
        if (!Register.this.pass2.getText().toString().equals(Register.this.pass1.getText().toString()))
        {
          Register.this.pass2.setError("پسورد های وارد شده با هم مطابقت ندارند");
          return;
        }
        if ((Register.this.mail.getText().equals("")) || (Register.this.mail.getText().length() < 5) || (!Register.this.mail.getText().toString().contains("@")) || (!Register.this.mail.getText().toString().contains(".")))
        {
          Register.this.mail.setError("ایمیل وارد شده معتبر نیست");
          return;
        }
        Register.this.pv.start();
       // Register.this.register(Register.this.username.getText().toString(), Register.this.pass1.getText().toString(), Register.this.pass2.getText().toString(), Register.this.mail.getText().toString(), Register.this.getIMEI());
      }
    });
  }
//
//  public void register(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
//  {
//    Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(1, "http://bahar.persianstory.ir/JsonProfile/register", new Response.Listener<String>(paramString1, paramString2)
//    {
//      public void onResponse(String paramString)
//      {
//        if (paramString.contains("true"))
//        {
//          Register.this.pv.stop();
//          SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(Register.this.act).edit();
//          localEditor.putString("u", Register.this.getIMEI());
//          localEditor.putString("us", username.getText().toString());
//          try
//          {
//            localEditor.putString("h", Base64.encodeToString(pass1.getText().toString().getBytes("UTF-8"), 0));
//            localEditor.putString("p", "0");
//            localEditor.apply();
//            Toast.makeText(Register.this.getApplicationContext(), "حساب شما با موفقیت ایجاد شد", 0).show();
//            Register.this.startActivity(new Intent(Register.this, MainActivity.class));
//            Register.this.overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
//            Register.this.finish();
//            return;
//          }
//          catch (UnsupportedEncodingException localUnsupportedEncodingException)
//          {
//              localUnsupportedEncodingException.printStackTrace();
//          }
//        }
//        Log.i("aa", paramString + "");
//        Toast.makeText(Register.this.getApplicationContext(), paramString.toString(), 1).show();
//        Register.this.pv.stop();
//      }
//    }
//    , new Response.ErrorListener()
//    {
//      public void onErrorResponse(VolleyError paramVolleyError)
//      {
//        Register.this.pv.stop();
//      }
//    }
//    , paramString1, paramString2, paramString3, paramString4, paramString5)
//    {
//      protected Map<String, String> getParams()
//      {
//        try
//        {
//          HashMap localHashMap = new HashMap();
//          localHashMap.put("u", username.getText());
//          localHashMap.put("h", pass1.getText());
//          localHashMap.put("hh", pass2.getText());
//          localHashMap.put("email", mail.getText());
//          localHashMap.put("a", "as");
//          return localHashMap;
//        }
//        catch (Exception localException)
//        {
//        }
//        return null;
//      }
//    });
//  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Register
 * JD-Core Version:    0.6.0
 */