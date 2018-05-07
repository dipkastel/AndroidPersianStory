package com.bloom.persianstory;

import android.app.Application;
import android.text.TextUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.Lists.LruBitmapCache;

public class MyApplication extends Application
{
  public static final String TAG = MyApplication.class.getSimpleName();
  private static MyApplication mInstance;
  private ImageLoader mImageLoader;
  private RequestQueue mRequestQueue;

  public static MyApplication getInstance()
  {
    try
    {
      MyApplication localMyApplication = mInstance;
      return localMyApplication;
    }
    finally
    {
      return null;
    }
  }

  public <T> void addToRequestQueue(Request<T> paramRequest)
  {
    paramRequest.setTag(TAG);
    getRequestQueue().add(paramRequest);
  }

  public <T> void addToRequestQueue(Request<T> paramRequest, String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      paramString = TAG;
    paramRequest.setTag(paramString);
    getRequestQueue().add(paramRequest);
  }

  public void cancelPendingRequests(Object paramObject)
  {
    if (this.mRequestQueue != null)
      this.mRequestQueue.cancelAll(paramObject);
  }

  public ImageLoader getImageLoader()
  {
    getRequestQueue();
    if (this.mImageLoader == null)
      this.mImageLoader = new ImageLoader(this.mRequestQueue, (ImageLoader.ImageCache) new LruBitmapCache());
    return this.mImageLoader;
  }

  public RequestQueue getRequestQueue()
  {
    if (this.mRequestQueue == null)
      this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    return this.mRequestQueue;
  }

  public void onCreate()
  {
    super.onCreate();
    mInstance = this;
  }

  public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener paramConnectivityReceiverListener)
  {
    ConnectivityReceiver.connectivityReceiverListener = paramConnectivityReceiverListener;
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.MyApplication
 * JD-Core Version:    0.6.0
 */