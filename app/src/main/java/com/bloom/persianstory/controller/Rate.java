package com.bloom.persianstory.controller;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bloom.persianstory.R;

public class Rate extends AppCompatActivity
{
  private Typeface tf;

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.rating);
    this.tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Rate
 * JD-Core Version:    0.6.0
 */