package com.bloom.persianstory.model.Network;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Download_data
  implements Runnable
{
  public download_complete caller;
  private final Handler handler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      String str = paramMessage.getData().getString("message");
      caller.get_data(str, page);
    }
  };
  private boolean killMe = false;
  private String link;
  private String page;

  public Download_data(download_complete paramdownload_complete)
  {
    caller = paramdownload_complete;
  }

  private void threadMsg(String paramString)
  {
    if ((!paramString.equals(null)) && (!paramString.equals("")))
    {
      Message localMessage = handler.obtainMessage();
      Bundle localBundle = new Bundle();
      localBundle.putString("message", paramString);
      localMessage.setData(localBundle);
      handler.sendMessage(localMessage);
    }
  }

  public void download_data_from_link(String paramString1, String paramString2)
  {
    page = paramString2;
    link = paramString1;
    new Thread(this).start();
  }

  public final void kill()
  {
    killMe = true;
  }

  public void run()
  {
    if (!killMe)
      threadMsg(link);
  }

  public static abstract interface download_complete
  {
    public abstract void get_data(String paramString1, String paramString2);
  }
}