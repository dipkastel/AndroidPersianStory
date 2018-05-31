package com.bloom.persianstory.model.Lists;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

public class LruBitmapCache extends LruCache<String, Bitmap>
  implements ImageLoader.ImageCache
{
  public LruBitmapCache()
  {
    this(getDefaultLruCacheSize());
  }

  public LruBitmapCache(int paramInt)
  {
    super(paramInt);
  }

  public static int getDefaultLruCacheSize()
  {
    return (int)(Runtime.getRuntime().maxMemory() / 1024L) / 8;
  }

  public Bitmap getBitmap(String paramString)
  {
    return (Bitmap)get(paramString);
  }

  public void putBitmap(String paramString, Bitmap paramBitmap)
  {
    put(paramString, paramBitmap);
  }

  protected int sizeOf(String paramString, Bitmap paramBitmap)
  {
    return paramBitmap.getRowBytes() * paramBitmap.getHeight() / 1024;
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Lists.LruBitmapCache
 * JD-Core Version:    0.6.0
 */