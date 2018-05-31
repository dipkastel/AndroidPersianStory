package com.bloom.persianstory;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.model.Lists.LruBitmapCache;
import com.bloom.persianstory.model.Network.ConnectivityReceiver;

public class MyApplication extends Application
{
  final String TAG = MyApplication.class.getSimpleName();
  private static MyApplication mInstance;
  private ImageLoader mImageLoader;
  private RequestQueue mRequestQueue;

  public static MyApplication getInstance()
  {
      return mInstance;
  }

  public ImageLoader getImageLoader()
  {
    getRequestQueue();
    if (this.mImageLoader == null)
      this.mImageLoader = new ImageLoader(this.mRequestQueue, new LruBitmapCache());
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