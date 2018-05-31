package com.bloom.persianstory.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.bloom.persianstory.R;

public class Home extends AppCompatActivity
{
  Button ToLogin;
  Button ToRegister;
  Activity act;
  Button mehman;
  private Typeface tf;

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.act = this;
    setContentView(R.layout.home);
    this.tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
    this.ToRegister = ((Button)findViewById(R.id.toregister));
    this.ToLogin = ((Button)findViewById(R.id.tologin));
    this.mehman = ((Button)findViewById(R.id.mehman));
    this.ToRegister.setTypeface(this.tf);
    this.ToLogin.setTypeface(this.tf);
    this.mehman.setTypeface(this.tf);
    this.ToRegister.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Home.this.startActivity(new Intent(Home.this, Register.class));
        Home.this.overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
      }
    });
    this.ToLogin.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Home.this.startActivity(new Intent(Home.this, Login.class));
        Home.this.overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
      }
    });
    this.mehman.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(Home.this.act).edit();
        localEditor.putString("us", "مهمان");
        localEditor.putString("p", "0");
        localEditor.apply();
        Home.this.startActivity(new Intent(Home.this, MainActivity.class));
        Home.this.overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
        Home.this.finish();
      }
    });
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Home
 * JD-Core Version:    0.6.0
 */