package com.bloom.persianstory;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Rate extends AppCompatActivity
{
  private Typeface tf;

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130968631);
    this.tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Rate
 * JD-Core Version:    0.6.0
 */