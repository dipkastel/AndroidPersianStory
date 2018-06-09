package com.bloom.persianstory.model.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Utilities
{
  public int getProgressPercentage(long paramLong1, long paramLong2)
  {
    Double.valueOf(0.0D);
    long l1 = (int)(paramLong1 / 1000L);
    long l2 = (int)(paramLong2 / 1000L);
    return Double.valueOf( (100.0D *l1 )/ l2).intValue();
  }


  public String milliSecondsToTimer(final int duration) {
    int dur, min, sec, mil;

    dur = duration;
    min = dur / 60000;
    dur -= min * 60000;
    sec = dur / 1000;
    dur -= sec * 1000;
    mil = dur;

    return min + ":" + sec ;
  }
  public int progressToTimer(int paramInt1, int paramInt2)
  {
    int i = paramInt2 / 1000;
    return 1000 * (int)(paramInt1 / 100.0D * i);
  }

  /**
   * This method converts dp unit to equivalent pixels, depending on device density.
   *
   * @param dp A value in dp (density independent pixels) unit. Which we need to convertMlToString into pixels
   * @param context Context to get resources and device specific display metrics
   * @return A float value to represent px equivalent to dp depending on device density
   */
  public static float dip2px( Context context,float dp){
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    float px = dp * (metrics.densityDpi / 160f);
    return px;
  }

  /**
   * This method converts device specific pixels to density independent pixels.
   *
   * @param px A value in px (pixels) unit. Which we need to convertMlToString into db
   * @param context Context to get resources and device specific display metrics
   * @return A float value to represent dp equivalent to px value
   */
  public static float px2dip( Context context,float px){
    Resources resources = context.getResources();
    DisplayMetrics metrics = resources.getDisplayMetrics();
    float dp = px / (metrics.densityDpi / 160f);
    return dp;
  }

  public String getAppPath(){
   return System.getenv("APPDATA");
  }
}

