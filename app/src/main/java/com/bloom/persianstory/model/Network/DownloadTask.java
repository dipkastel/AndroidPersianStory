package com.bloom.persianstory.model.Network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Integer, String>
{
  public static ProgressDialog mprogress;
  Context context;
  PowerManager.WakeLock mWakeLock;
    changeProgressListener changeProgressListener;
  public DownloadTask(Context paramContext,changeProgressListener changeProgressListener)
  {
    this.context = paramContext;

      this.changeProgressListener = changeProgressListener;
//    mprogress = new ProgressDialog(this.context);
//    mprogress.setMessage("لطفا صبر کنید");
//    mprogress.setIndeterminate(true);
//    mprogress.setCancelable(true);
//    mprogress.setProgressStyle(1);
  }

  protected String doInBackground(String[] paramArrayOfString)
  {   InputStream input = null;
    OutputStream output = null;
    HttpURLConnection connection = null;
    try {
      URL url = new URL(paramArrayOfString[0]);
      connection = (HttpURLConnection) url.openConnection();
      connection.connect();

      // expect HTTP 200 OK, so we don't mistakenly save error report
      // instead of the file
      if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
        return "Server returned HTTP " + connection.getResponseCode()
                + " " + connection.getResponseMessage();
      }

      // this will be useful to display download percentage
      // might be -1: server did not report the length
      int fileLength = connection.getContentLength();

      // download the file
      input = connection.getInputStream();
      output = this.context.openFileOutput(paramArrayOfString[1] + ".mp3", 0);

      byte data[] = new byte[4096];
      long total = 0;
      int count;
      while ((count = input.read(data)) != -1) {
        // allow canceling with back button
        if (isCancelled()) {
          input.close();
          return null;
        }
        total += count;
        // publishing the progress....
        if (fileLength > 0) // only if total length is known
          publishProgress((int) (total * 100 / fileLength));
        output.write(data, 0, count);
      }
    } catch (Exception e) {
      return e.toString();
    } finally {
      try {
        if (output != null)
          output.close();
        if (input != null)
          input.close();
      } catch (IOException ignored) {
      }

      if (connection != null)
        connection.disconnect();
    }
    return null;
//    InputStream localInputStream = null;
//    FileOutputStream localFileOutputStream = null;
//    HttpURLConnection localHttpURLConnection = null;
//    try
//    {
//      localHttpURLConnection = (HttpURLConnection)new URL(paramArrayOfString[0]).openConnection();
//      localHttpURLConnection.connect();
//      int i = localHttpURLConnection.getContentLength();
//      localInputStream = localHttpURLConnection.getInputStream();
//      localFileOutputStream = this.context.openFileOutput(paramArrayOfString[1] + ".mp3", 0);
//      byte[] arrayOfByte = new byte[4096];
//      long l = 0L;
//      while (true)
//      {
//        int j = localInputStream.read(arrayOfByte);
//        if (j == -1)
//          break;
//        l += j;
//        if (i > 0)
//        {
//          Integer[] arrayOfInteger = new Integer[1];
//          arrayOfInteger[0] = Integer.valueOf((int)(100L * l / i));
//          publishProgress(arrayOfInteger);
//        }
//        localFileOutputStream.write(arrayOfByte, 0, j);
//      }
//    }
//    catch (Exception localException)
//    {
//      localException.printStackTrace();
//      if (localFileOutputStream != null);
//      try
//      {
//        localFileOutputStream.close();
//        if (localInputStream != null)
//          localInputStream.close();
//        if (localHttpURLConnection != null)
//          localHttpURLConnection.disconnect();
//          if (localFileOutputStream != null){}
//          try
//          {
//            localFileOutputStream.close();
//            if (localInputStream != null)
//              localInputStream.close();
//            if (localHttpURLConnection == null)
//            localHttpURLConnection.disconnect();
//          }
//          catch (IOException localIOException3)
//          {
//              localIOException3.printStackTrace();
//          }
//      }
//      catch (IOException localIOException2)
//      {
//          localIOException2.printStackTrace();
//      }
//    }
//    finally
//    {
//    }
//    try
//    {
//      localFileOutputStream.close();
//      if (localInputStream != null)
//        localInputStream.close();
//      if (localHttpURLConnection != null)
//        localHttpURLConnection.disconnect();
//    }
//    catch (IOException localIOException1)
//    {
//        localIOException1.printStackTrace();
//    }
//    return "";
  }

  protected void onPostExecute(String paramString)
  {
    super.onPostExecute(paramString);
    if (paramString != null)
    {
      Toast.makeText(this.context, "خطای حین دانلود", Toast.LENGTH_LONG).show();
      changeProgressListener.Error();
      return;
    }
    changeProgressListener.downloadComplete();
  }

  protected void onPreExecute()
  {
    super.onPreExecute();
//      mprogress.setMax(100);
//      mprogress.show();
    this.mWakeLock = ((PowerManager)context.getSystemService(Context.POWER_SERVICE)).newWakeLock(1, getClass().getName());
    this.mWakeLock.acquire();
  }

  protected void onProgressUpdate(Integer[] paramArrayOfInteger)
  {
    super.onProgressUpdate(paramArrayOfInteger);
    this.changeProgressListener.progressChanged(paramArrayOfInteger[0]);
//    mprogress.setIndeterminate(false);
//      mprogress.setProgress(paramArrayOfInteger[0].intValue());
  }

    public interface changeProgressListener {
        void progressChanged(int persent);
        void downloadComplete();
        void Error();
    }
}


