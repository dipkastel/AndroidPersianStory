package com.bloom.persianstory.Fonts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

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

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Fonts.IranTextview
 * JD-Core Version:    0.6.0
 */