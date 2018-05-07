package com.bloom.persianstory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.HashMap;
import java.util.Map;

public class ResetPass extends AppCompatActivity
{
  Activity act;
  LinearLayout changepass;
  Button changepw;
  EditText code;
  TextInputLayout lblcode;
  TextInputLayout lblpass1;
  TextInputLayout lblpass2;
  EditText pass1;
  EditText pass2;
  ProgressView pv;
  Button send;
  LinearLayout sendcode;
  private Typeface tf;
  TextView time;

//  public void changepass(String paramString1, String paramString2, String paramString3)
//  {
//    RequestQueue localRequestQueue = Volley.newRequestQueue(getApplicationContext());
//    String str = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("mail", "");
//    localRequestQueue.add(new StringRequest(1, "http://bahar.persianstory.ir/JsonProfile/change_pass", new Response.Listener<String>()
//    {
//      public void onResponse(String paramString)
//      {
//        Log.i("aa", paramString);
//        if (paramString.contains("true"))
//        {
//          ResetPass.this.pv.stop();
//          Toast.makeText(ResetPass.this.getApplicationContext(), "کلمه عبور با موفقیت تغییر یافت", 0).show();
//          ResetPass.this.startActivity(new Intent(ResetPass.this, Login.class));
//          ResetPass.this.overridePendingTransition(2131034135, 2131034136);
//          ResetPass.this.finish();
//          return;
//        }
//        Log.i("aa", paramString + "");
//        Toast.makeText(ResetPass.this.getApplicationContext(), paramString.toString(), 1).show();
//        ResetPass.this.pv.stop();
//      }
//    }
//    , new Response.ErrorListener()
//    {
//      public void onErrorResponse(VolleyError paramVolleyError)
//      {
//        ResetPass.this.pv.stop();
//        Toast.makeText(ResetPass.this.getApplicationContext(), paramVolleyError.getMessage(), 1).show();
//        Log.i("aa", paramVolleyError.toString() + "");
//      }
//    }
//    , paramString3, paramString1, paramString2, str)
//    {
//      protected Map<String, String> getParams()
//      {
//        try
//        {
//          HashMap localHashMap = new HashMap();
//          localHashMap.put("code", this.val$codee);
//          localHashMap.put("h", this.val$passs1);
//          localHashMap.put("hh", this.val$passs2);
//          localHashMap.put("email", this.val$emaill);
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
    startActivity(new Intent(this, Home.class));
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.act = this;
    setContentView(R.layout.resetpass);
    this.pv = ((ProgressView)findViewById(R.id.loading));
    this.lblcode = ((TextInputLayout)findViewById(R.id.codeWrapper));
    this.lblpass1 = ((TextInputLayout)findViewById(R.id.passWrapper));
    this.lblpass2 = ((TextInputLayout)findViewById(R.id.passWrapper2));
    this.time = ((TextView)findViewById(R.id.time));
    this.code = ((EditText)findViewById(R.id.code));
    this.pass1 = ((EditText)findViewById(R.id.password1));
    this.pass2 = ((EditText)findViewById(R.id.password2));
    this.sendcode = ((LinearLayout)findViewById(R.id.boxcode));
    this.changepass = ((LinearLayout)findViewById(R.id.boxchangepass));
    this.changepass.setVisibility(View.GONE);
    this.tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
    this.send = ((Button)findViewById(R.id.send));
    this.changepw = ((Button)findViewById(R.id.changepass));
    this.send.setTypeface(this.tf);
    this.changepw.setTypeface(this.tf);
    this.lblcode.setTypeface(this.tf);
    this.lblpass1.setTypeface(this.tf);
    this.lblpass2.setTypeface(this.tf);
    new CountDownTimer(300000L, 1000L)
    {
      public void onFinish()
      {
        ResetPass.this.time.setText("زمان باقی مانده: 0 ثانیه");
      }

      public void onTick(long paramLong)
      {
        ResetPass.this.time.setText("زمان باقی مانده: " + paramLong / 1000L + " ثانیه");
      }
    }
    .start();
    this.send.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if ((ResetPass.this.code.getText().equals("")) || (ResetPass.this.code.getText().length() < 4))
        {
          ResetPass.this.code.setError("کد وارد شده معتبر نیست");
          return;
        }
        ResetPass.this.pv.start();
        //ResetPass.this.sendpass(ResetPass.this.code.getText().toString());
      }
    });
    this.changepw.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if ((ResetPass.this.pass1.getText().equals("")) || (ResetPass.this.pass1.getText().length() < 4))
        {
          ResetPass.this.pass1.setError("پسور وارد شده معتبر نیست");
          return;
        }
        if ((ResetPass.this.pass2.getText().equals("")) || (ResetPass.this.pass2.getText().length() < 4))
        {
          ResetPass.this.pass2.setError("پسور وارد شده معتبر نیست");
          return;
        }
        if (!ResetPass.this.pass2.getText().toString().equals(ResetPass.this.pass1.getText().toString()))
        {
          ResetPass.this.pass2.setError("پسورد های وارد شده با هم مطابقت ندارند");
          return;
        }
       // ResetPass.this.changepass(ResetPass.this.pass1.getText().toString(), ResetPass.this.pass2.getText().toString(), ResetPass.this.code.getText().toString());
      }
    });
  }

//  public void sendpass(String paramString)
//  {
//    Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(1, "http://bahar.persianstory.ir/JsonProfile/reset_pass", new Response.Listener<String>()
//    {
//      public void onResponse(String paramString)
//      {
//        Log.i("aa", paramString);
//        if (paramString.contains("true"))
//        {
//          ResetPass.this.pv.stop();
//          ResetPass.this.sendcode.setVisibility(8);
//          ResetPass.this.changepass.setVisibility(0);
//          return;
//        }
//        Log.i("aa", paramString + "");
//        Toast.makeText(ResetPass.this.getApplicationContext(), paramString.toString(), 1).show();
//        ResetPass.this.pv.stop();
//      }
//    }
//    , new Response.ErrorListener()
//    {
//      public void onErrorResponse(VolleyError paramVolleyError)
//      {
//        ResetPass.this.pv.stop();
//        Log.i("aa", paramVolleyError.toString() + "");
//      }
//    }
//    , paramString)
//    {
//      protected Map<String, String> getParams()
//      {
//        try
//        {
//          HashMap localHashMap = new HashMap();
//          localHashMap.put("code", code);
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
 * Qualified Name:     ir.tg.bahar.ResetPass
 * JD-Core Version:    0.6.0
 */