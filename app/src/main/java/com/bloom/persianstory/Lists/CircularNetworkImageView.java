package com.bloom.persianstory.Lists;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import com.android.volley.toolbox.NetworkImageView;

public class CircularNetworkImageView extends NetworkImageView
{
  Context mContext;

  public CircularNetworkImageView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public CircularNetworkImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
    this.mContext = paramContext;
  }

  public CircularNetworkImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
  }

  public Bitmap getCircularBitmap(Bitmap paramBitmap)
  {
    Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
    Canvas localCanvas = new Canvas(localBitmap);
    int i = paramBitmap.getWidth();
    if (paramBitmap.getWidth() > paramBitmap.getHeight())
      i = paramBitmap.getHeight();
    Paint localPaint = new Paint();
    Rect localRect = new Rect(0, 0, i, i);
    RectF localRectF = new RectF(localRect);
    float f = i / 2;
    localPaint.setAntiAlias(true);
    localCanvas.drawARGB(0, 0, 0, 0);
    localPaint.setColor(-12434878);
    localCanvas.drawRoundRect(localRectF, f, f, localPaint);
    localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    localCanvas.drawBitmap(paramBitmap, localRect, localRect, localPaint);
    return localBitmap;
  }

  public void setImageBitmap(Bitmap paramBitmap)
  {
    if (paramBitmap == null)
      return;
    setImageDrawable(new BitmapDrawable(this.mContext.getResources(), getCircularBitmap(paramBitmap)));
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Lists.CircularNetworkImageView
 * JD-Core Version:    0.6.0
 */