package com.bloom.persianstory;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver
{
  public static ConnectivityReceiverListener connectivityReceiverListener;

  public static boolean isConnected(Context context) {
    ConnectivityManager
            cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    return activeNetwork != null
            && activeNetwork.isConnectedOrConnecting();
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnectedOrConnecting()));
    for (boolean bool = true; ; bool = false)
    {
      if (connectivityReceiverListener != null)
        connectivityReceiverListener.onNetworkConnectionChanged(bool);
      return;
    }
  }

  public static abstract interface ConnectivityReceiverListener
  {
    public abstract void onNetworkConnectionChanged(boolean paramBoolean);
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.ConnectivityReceiver
 * JD-Core Version:    0.6.0
 */