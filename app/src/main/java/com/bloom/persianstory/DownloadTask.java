package com.bloom.persianstory;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.Toast;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Integer, String>
{
  public static ProgressDialog mprogress;
  Context context;
  PowerManager.WakeLock mWakeLock;

  public DownloadTask(Context paramContext)
  {
    this.context = paramContext;
    mprogress = new ProgressDialog(this.context);
    mprogress.setMessage("لطفا صبر کنید");
    mprogress.setIndeterminate(true);
    mprogress.setCancelable(true);
    mprogress.setProgressStyle(1);
  }

  protected String doInBackground(String[] paramArrayOfString)
  {
    InputStream localInputStream = null;
    FileOutputStream localFileOutputStream = null;
    HttpURLConnection localHttpURLConnection = null;
    try
    {
      localHttpURLConnection = (HttpURLConnection)new URL(paramArrayOfString[0]).openConnection();
      localHttpURLConnection.connect();
      int i = localHttpURLConnection.getContentLength();
      localInputStream = localHttpURLConnection.getInputStream();
      localFileOutputStream = this.context.openFileOutput(paramArrayOfString[1] + ".mp3", 0);
      byte[] arrayOfByte = new byte[4096];
      long l = 0L;
      while (true)
      {
        int j = localInputStream.read(arrayOfByte);
        if (j == -1)
          break;
        l += j;
        if (i > 0)
        {
          Integer[] arrayOfInteger = new Integer[1];
          arrayOfInteger[0] = Integer.valueOf((int)(100L * l / i));
          publishProgress(arrayOfInteger);
        }
        localFileOutputStream.write(arrayOfByte, 0, j);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      if (localFileOutputStream != null);
      try
      {
        localFileOutputStream.close();
        if (localInputStream != null)
          localInputStream.close();
        if (localHttpURLConnection != null)
          localHttpURLConnection.disconnect();
          if (localFileOutputStream != null){}
          try
          {
            localFileOutputStream.close();
            if (localInputStream != null)
              localInputStream.close();
            if (localHttpURLConnection == null)
            localHttpURLConnection.disconnect();
          }
          catch (IOException localIOException3)
          {
              localIOException3.printStackTrace();
          }
      }
      catch (IOException localIOException2)
      {
          localIOException2.printStackTrace();
      }
    }
    finally
    {
      if (localFileOutputStream == null);
    }
    try
    {
      localFileOutputStream.close();
      if (localInputStream != null)
        localInputStream.close();
      if (localHttpURLConnection != null)
        localHttpURLConnection.disconnect();
    }
    catch (IOException localIOException1)
    {
        localIOException1.printStackTrace();
    }
    return "";
  }

  protected void onPostExecute(String paramString)
  {
    super.onPostExecute(paramString);
    mprogress.dismiss();
    if (paramString != null)
    {
      Toast.makeText(this.context, "خطای حین دانلود", Toast.LENGTH_LONG).show();
      return;
    }
    Toast.makeText(this.context, "قصه با موفقیت ذخیره شد", Toast.LENGTH_LONG).show();
  }

  protected void onPreExecute()
  {
    super.onPreExecute();
    this.mWakeLock = ((PowerManager)context.getSystemService(Context.POWER_SERVICE)).newWakeLock(1, getClass().getName());
    this.mWakeLock.acquire();
    mprogress.show();
  }

  protected void onProgressUpdate(Integer[] paramArrayOfInteger)
  {
    super.onProgressUpdate(paramArrayOfInteger);
    mprogress.setIndeterminate(false);
    mprogress.setMax(100);
    mprogress.setProgress(paramArrayOfInteger[0].intValue());
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.DownloadTask
 * JD-Core Version:    0.6.0
 */