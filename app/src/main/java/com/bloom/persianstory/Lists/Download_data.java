package com.bloom.persianstory.Lists;

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
      Download_data.this.caller.get_data(str, Download_data.this.page);
    }
  };
  private boolean killMe = false;
  private String link;
  private String page;

  public Download_data(download_complete paramdownload_complete)
  {
    this.caller = paramdownload_complete;
  }

  private void threadMsg(String paramString)
  {
    if ((!paramString.equals(null)) && (!paramString.equals("")))
    {
      Message localMessage = this.handler.obtainMessage();
      Bundle localBundle = new Bundle();
      localBundle.putString("message", paramString);
      localMessage.setData(localBundle);
      this.handler.sendMessage(localMessage);
    }
  }

  public void download_data_from_link(String paramString1, String paramString2)
  {
    this.page = paramString2;
    this.link = paramString1;
    new Thread(this).start();
  }

  public final void kill()
  {
    this.killMe = true;
  }

  public void run()
  {
    if (!this.killMe)
      threadMsg(this.link);
  }

  public static abstract interface download_complete
  {
    public abstract void get_data(String paramString1, String paramString2);
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Lists.Download_data
 * JD-Core Version:    0.6.0
 */