package com.bloom.persianstory.view;

import android.content.Context;
import android.util.AttributeSet;

import com.rey.material.widget.TextView;

public class IranTextview extends TextView
{
  FontCache fc = new FontCache();

  public IranTextview(Context paramContext)
  {
    super(paramContext);
    applyCustomFont(paramContext);
  }

  public IranTextview(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    applyCustomFont(paramContext);
  }

  public IranTextview(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    applyCustomFont(paramContext);
  }

  private void applyCustomFont(Context paramContext)
  {
    setTypeface(this.fc.getTypeface("iransans.ttf", paramContext));
  }
}