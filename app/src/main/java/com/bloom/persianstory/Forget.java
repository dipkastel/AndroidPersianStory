package com.bloom.persianstory;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
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
import java.util.HashMap;
import java.util.Map;

public class Forget extends AppCompatActivity
{
  Activity act;
  Button back;
  TextInputLayout lblusername;
  EditText mail;
  ProgressView pv;
  Button sendpass;
  private Typeface tf;

  public void onBackPressed()
  {
    super.onBackPressed();
    finish();
    overridePendingTransition(R.anim.slide2, R.anim.slide2_out);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.act = this;
    setContentView(R.layout.forget);
    this.pv = ((ProgressView)findViewById(R.id.loading));
    this.lblusername = ((TextInputLayout)findViewById(R.id.usernameWrapper));
    this.mail = ((EditText)findViewById(R.id.email));
    this.tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
    this.sendpass = ((Button)findViewById(R.id.sendpass));
    this.back = ((Button)findViewById(R.id.back));
    this.sendpass.setTypeface(this.tf);
    this.back.setTypeface(this.tf);
    this.back.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Forget.this.finish();
        Forget.this.overridePendingTransition(2131034137, 2131034138);
      }
    });
    this.lblusername.setTypeface(this.tf);
    this.sendpass.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if ((Forget.this.mail.getText().equals("")) || (Forget.this.mail.getText().length() < 5) || (!Forget.this.mail.getText().toString().contains("@")) || (!Forget.this.mail.getText().toString().contains(".")))
        {
          Forget.this.mail.setError("ایمیل وارد شده معتبر نیست");
          return;
        }
        Forget.this.pv.start();
        //Forget.this.sendpass(Forget.this.mail.getText().toString());
      }
    });
  }

//  public void sendpass(String paramString)
//  {
//    Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(1, "http://bahar.persianstory.ir/JsonProfile/forget", new Response.Listener<String>(paramString)
//    {
//      public void onResponse(String paramString)
//      {
//        Log.i("aa", paramString);
//        if (paramString.contains("true"))
//        {
//          Forget.this.pv.stop();
//          SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(Forget.this.act).edit();
//          localEditor.putString("mail", mail.getText().toString());
//          localEditor.apply();
//          Forget.this.startActivity(new Intent(Forget.this, ResetPass.class));
//          Forget.this.overridePendingTransition(2131034135, 2131034136);
//          Forget.this.finish();
//          return;
//        }
//        Log.i("aa", paramString + "");
//        Toast.makeText(Forget.this.getApplicationContext(), paramString.toString(), 1).show();
//        Forget.this.pv.stop();
//      }
//    }
//    , new Response.ErrorListener()
//    {
//      public void onErrorResponse(VolleyError paramVolleyError)
//      {
//        Forget.this.pv.stop();
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
//          localHashMap.put("email", this.val$maill);
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
 * Qualified Name:     ir.tg.bahar.Forget
 * JD-Core Version:    0.6.0
 */