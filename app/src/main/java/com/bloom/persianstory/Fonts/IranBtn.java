package com.bloom.persianstory.Fonts;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class IranBtn extends Button
{
  FontCache fc = new FontCache();

  public IranBtn(Context paramContext)
  {
    super(paramContext);
    applyCustomFont(paramContext);
  }

  public IranBtn(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    applyCustomFont(paramContext);
  }

  public IranBtn(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
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
 * Qualified Name:     ir.tg.bahar.Fonts.IranBtn
 * JD-Core Version:    0.6.0
 */