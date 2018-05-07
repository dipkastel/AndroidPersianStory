package com.bloom.persianstory.Fonts;

import android.content.Context;
import android.graphics.Typeface;
import java.util.Hashtable;

public class FontCache
{
  private static Hashtable<String, Typeface> fontCache = new Hashtable();

  public Typeface getTypeface(String paramString, Context paramContext)
  {
    Object localObject = (Typeface)fontCache.get(paramString);
    if (localObject == null);
    try
    {
      Typeface localTypeface = Typeface.createFromAsset(paramContext.getAssets(), paramString);
      localObject = localTypeface;
      fontCache.put(paramString, (Typeface) localObject);
      return (Typeface) localObject;
    }
    catch (Exception localException)
    {
    }
    return (Typeface)null;
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Fonts.FontCache
 * JD-Core Version:    0.6.0
 */