package com.bloom.persianstory;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.Lists.Story;
import com.bloom.persianstory.db.DBHandler;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Single extends AppCompatActivity
  implements ConnectivityReceiver.ConnectivityReceiverListener, MediaPlayer.OnBufferingUpdateListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener
{
  Activity act;
  Story add = new Story();
  Context context;
  CoordinatorLayout cr;
  long current_time;
  TextView current_time_textview;
  DBHandler dbhandler;
  public Handler handler = new Handler();
  ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
  boolean isConnected;
  PowerManager.WakeLock mWakeLock;
  MediaPlayer mediaPlayer;
  String nameString;
  TextView nametv;
  boolean offlinemod;
  ProgressDialog pd;
  String picString;
  String pidString;
  Button play;
  String priceString;
  ProgressView pv;
  Button rate;
  String rateString;
  TextView ratetv;
  Button save;
  SeekBar seekbar;
  String sound;
  String soundString;
  public ArrayList<Story> stories = new ArrayList();
  String tellerString;
  TextView tellertv;
  private Typeface tf;
  NetworkImageView thumbNail;
  TextView total_time_textview;
  long totatl_time;
  String uploadString;
  Utilities utils;

  private void Update_seekbar_timer()
  {
    try
    {
      if (this.mediaPlayer.isPlaying())
      {
        this.totatl_time = this.mediaPlayer.getDuration();
        this.current_time = this.mediaPlayer.getCurrentPosition();
        this.total_time_textview.setText("" + this.utils.milliSecondsToTimer(this.totatl_time));
        this.current_time_textview.setText("" + this.utils.milliSecondsToTimer(this.current_time));
        int i = this.utils.getProgressPercentage(this.current_time, this.totatl_time);
        this.seekbar.setProgress(i);
        Runnable local4 = new Runnable()
        {
          public void run()
          {
            Single.this.Update_seekbar_timer();
          }
        };
        this.handler.postDelayed(local4, 1000L);
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void checkConnection()
  {
    this.isConnected = ConnectivityReceiver.isConnected(this);
    if (!this.offlinemod)
      showSnack(this.isConnected);
  }

//  private boolean isMyServiceRunning(Class<?> paramClass)
//  {
//    Iterator localIterator =getax.getRunningServices().iterator();
//    while (localIterator.hasNext())
//    {
//      ActivityManager.RunningServiceInfo localRunningServiceInfo = (ActivityManager.RunningServiceInfo)localIterator.next();
//      if (paramClass.getName().equals(localRunningServiceInfo.service.getClassName()))
//        return true;
//    }
//    return false;
//  }

  private void showSnack(boolean paramBoolean)
  {
    Snackbar localSnackbar = Snackbar.make(this.cr, "خطا در اتصال به اینترنت", 0);
    ((TextView)localSnackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(-256);
    if (!paramBoolean)
      localSnackbar.show();
  }

  public void help(Activity paramActivity)
  {
    final Dialog localDialog = new Dialog(paramActivity);
    localDialog.requestWindowFeature(1);
    localDialog.setContentView(R.layout.rating);
   // localDialog.getWindow().setBackgroundDrawableResource(17170445);
    localDialog.show();
    final RatingBar localRatingBar = (RatingBar)localDialog.findViewById(R.id.ratingBar);
    Button localButton = (Button)localDialog.findViewById(R.id.btnSubmit);
    localButton.setTypeface(this.tf);
    localRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener()
    {
      public void onRatingChanged(RatingBar paramRatingBar, float paramFloat, boolean paramBoolean)
      {
        String str;
        switch (Math.round(paramRatingBar.getRating()))
        {
        default:
          str = "نا معین";
        case 1:
          str = "نا مناسب";
        case 2:
          str = "بد";
        case 3:
          str = "متوسط";
        case 4:
          str = "خوب";
        case 5:
          str = "عالی";
        }
        Toast.makeText(Single.this, str, Toast.LENGTH_LONG).show();
      }
    });
    localButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Volley.newRequestQueue(Single.this.getApplicationContext()).add(new JsonObjectRequest(0, "http://bahar.persianstory.ir/SinglePost/add_rate_mob?rate=" + Math.round(localRatingBar.getRating()) + "&pid=" + Single.this.pidString, null, new Response.Listener<JSONObject>()
        {
          public void onResponse(JSONObject paramJSONObject)
          {
            Log.d("Response", paramJSONObject.toString());
            try
            {
              boolean bool = paramJSONObject.get("status").toString().equals("ok");
              if (bool);
              return;
            }
            catch (JSONException localJSONException)
            {
              localJSONException.printStackTrace();
            }
          }
        }
        , new Response.ErrorListener()
        {
          public void onErrorResponse(VolleyError paramVolleyError)
          {
          }
        }));
        Toast.makeText(Single.this.getApplicationContext(), "امتیاز شما ثبت شد", Toast.LENGTH_LONG).show();
        localDialog.dismiss();
      }
    });
  }

  public void onBackPressed()
  {
    super.onBackPressed();
    if (this.mediaPlayer.isPlaying())
      this.mediaPlayer.stop();
    finish();
    overridePendingTransition(R.anim.slide2, R.anim.slide2_out);
  }

  public void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt)
  {
    this.seekbar.setSecondaryProgress(paramInt);
  }

  public void onCompletion(MediaPlayer paramMediaPlayer)
  {
    this.handler.removeCallbacks(null);
    this.seekbar.setProgress(0);
    this.total_time_textview.setText("00:00");
    this.current_time_textview.setText("00:00");
    this.play.setText("پخش");
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.single);
    this.act = this;
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    this.uploadString = localSharedPreferences.getString("uploadurl", "");
    this.pv = ((ProgressView)findViewById(R.id.loading));
    this.sound = localSharedPreferences.getString("sound", "");
    this.pd = new ProgressDialog(this);
    this.pd.setMessage("منتظر باشید...");
    this.pd.setCancelable(false);
    this.pd.show();
    this.context = this;
    this.utils = new Utilities();
    this.tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
    this.seekbar = ((SeekBar)findViewById(R.id.seekbar));
    this.dbhandler = new DBHandler(this.context);
    this.dbhandler.useable();
    this.seekbar.setOnSeekBarChangeListener(this);
    this.current_time_textview = ((TextView)findViewById(R.id.duration));
    this.total_time_textview = ((TextView)findViewById(R.id.endtime));
    this.rate = ((Button)findViewById(R.id.rateSingle));
    this.play = ((Button)findViewById(R.id.playSingle));
    this.save = ((Button)findViewById(R.id.saveSingle));
    this.thumbNail = ((NetworkImageView)findViewById(R.id.thumbnail2));
    this.nametv = ((TextView)findViewById(R.id.nameStory));
    this.tellertv = ((TextView)findViewById(R.id.tellerStory));
    this.ratetv = ((TextView)findViewById(R.id.rateStory));
    this.play.setEnabled(false);
    this.play.setClickable(false);
    this.rate.setTypeface(this.tf);
    this.play.setTypeface(this.tf);
    this.save.setTypeface(this.tf);
    this.cr = ((CoordinatorLayout)findViewById(R.id.c1));
    Bundle localBundle = null;
    if (paramBundle == null)
    {
      localBundle = getIntent().getExtras();
      if (localBundle != null)
      {
        if (!localBundle.getString("status").equals("offline"))
          return ;
        this.pv.stop();
        this.pd.dismiss();
        this.offlinemod = true;
        ArrayList localArrayList = this.dbhandler.getStory(localBundle.getString("id"));
        this.save.setText("حذف");
        this.pidString = ((String)localArrayList.get(0));
        this.nameString = ((String)localArrayList.get(1));
        this.tellerString = ((String)localArrayList.get(2));
        this.picString = ((String)localArrayList.get(3));
        this.soundString = ((String)localArrayList.get(4));
        this.rateString = "0";
        this.priceString = ((String)localArrayList.get(5));
      }
    }
    while (true)
    {
      Log.i("aa", this.uploadString + Uri.encode(this.soundString));
      Log.i("aa", this.picString);
      checkConnection();
      this.rate.setEnabled(true);
      this.save.setEnabled(true);
      this.nametv.setText(this.nameString);
      this.tellertv.setText("قصه گو : " + this.tellerString);
      if (Float.valueOf(this.rateString).floatValue() > 0.0F)
        this.ratetv.setText("امتیاز : " + this.rateString + "");
      this.thumbNail.setImageUrl(this.picString, this.imageLoader);
      this.mediaPlayer = new MediaPlayer();
      this.mediaPlayer.setOnBufferingUpdateListener(this);
      this.mediaPlayer.setOnCompletionListener(this);
      this.rate.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          Single.this.checkConnection();
          if (Single.this.isConnected)
          {
            Single.this.help(Single.this.act);
            return;
          }
          Single.this.showSnack(Single.this.isConnected);
        }
      });
      this.play.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          Single.this.checkConnection();
          if (Single.this.mediaPlayer.isPlaying())
          {
            Single.this.play.setText("پخش");
            Single.this.mediaPlayer.pause();
            return;
          }
          Single.this.mediaPlayer.start();
          Single.this.Update_seekbar_timer();
          Single.this.play.setText("توقف");
        }
      });
      this.save.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (Single.this.offlinemod)
          {
            new AlertDialog.Builder(Single.this).setTitle("حذف قصه").setMessage("آیا از حذف این قصه اطمینان دارید ؟").setPositiveButton("بله", new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramDialogInterface, int paramInt)
              {
                if (Single.this.mediaPlayer.isPlaying())
                  Single.this.mediaPlayer.stop();
                new File("data/data/ir.tg.bahar/files/" + Single.this.pidString + ".mp3").delete();
                Single.this.dbhandler.deleteStory(Integer.valueOf(Single.this.pidString));
                Single.this.finish();
              }
            }).setNegativeButton("انصراف", new DialogInterface.OnClickListener()
            {
              public void onClick(DialogInterface paramDialogInterface, int paramInt)
              {
              }
            }).setIcon(R.drawable.ic_delete_forever_black_24dp).show();
            return;
          }
          DownloadTask localDownloadTask = new DownloadTask(Single.this.context);
          String[] arrayOfString = new String[2];
          arrayOfString[0] = (Single.this.uploadString + Uri.encode(Single.this.soundString));
          arrayOfString[1] = Single.this.pidString;
          localDownloadTask.execute(arrayOfString);
          Single.this.add.setSound(Single.this.soundString);
          Single.this.add.setPid(Integer.valueOf(Single.this.pidString).toString());
          Single.this.add.setPrice(Single.this.priceString);
          Single.this.add.setPic(Single.this.picString);
          Single.this.add.setTeller(Single.this.tellerString);
          Single.this.add.setName(Single.this.nameString);
          Single.this.stories.add(Single.this.add);
          //Single.this.dbhandler.addStory(Single.this.add);
          new Player().execute(new Object[0]);
        }
      });
      if (!this.mediaPlayer.isPlaying()){
      offlinemod = false;
      this.pidString = localBundle.getString("pid");
      this.nameString = localBundle.getString("name");
      this.tellerString = localBundle.getString("teller");
      this.picString = localBundle.getString("pic");
      this.soundString = localBundle.getString("sound");
      this.rateString = localBundle.getString("rate");
      this.priceString = localBundle.getString("price");
      return;}
      offlinemod = false;
      this.pidString = ((String)paramBundle.getSerializable("pid"));
      this.nameString = ((String)paramBundle.getSerializable("name"));
      this.tellerString = ((String)paramBundle.getSerializable("teller"));
      this.picString = ((String)paramBundle.getSerializable("pic"));
      this.soundString = ((String)paramBundle.getSerializable("sound"));
      this.priceString = ((String)paramBundle.getSerializable("price"));
      this.rateString = ((String)paramBundle.getSerializable("rate"));
    }
  }

  public void onNetworkConnectionChanged(boolean paramBoolean)
  {
    showSnack(paramBoolean);
  }

  public void onProgressChanged(SeekBar paramSeekBar, int paramInt, boolean paramBoolean)
  {
  }

  protected void onResume()
  {
    super.onResume();
    MyApplication.getInstance().setConnectivityListener(this);
  }

  public void onStartTrackingTouch(SeekBar paramSeekBar)
  {
  }

  public void onStopTrackingTouch(SeekBar paramSeekBar)
  {
    this.handler.removeCallbacks(null);
    int i = this.mediaPlayer.getDuration();
    int j = this.utils.progressToTimer(paramSeekBar.getProgress(), i);
    this.mediaPlayer.seekTo(j);
    Update_seekbar_timer();
  }

//  public void sendrate(String paramString1, String paramString2)
//  {
//    Volley.newRequestQueue(this).add(new JsonObjectRequest(0, "http://bahar.persianstory.ir/SinglePost/add_rate_mob?rate=" + paramString1 + "&pid=" + paramString2, null, new Response.Listener()
//    {
//      public void onResponse(JSONObject paramJSONObject)
//      {
//        Log.d("Response", paramJSONObject.toString());
//        try
//        {
//          if (paramJSONObject.get("status").toString().equals("ok"))
//            Toast.makeText(Single.this.getApplicationContext(), "امتیاز شما ثبت شد", 0).show();
//          return;
//        }
//        catch (JSONException localJSONException)
//        {
//          localJSONException.printStackTrace();
//        }
//      }
//    }
//    , new Response.ErrorListener()
//    {
//      public void onErrorResponse(VolleyError paramVolleyError)
//      {
//        Log.d("Error.Response", paramVolleyError.getMessage());
//      }
//    }));
//  }

  private class Player extends AsyncTask
  {
    private Player()
    {
    }

    protected Object doInBackground(Object[] paramArrayOfObject)
    {
      try
      {
        if (!Single.this.offlinemod)
          Single.this.mediaPlayer.setDataSource(Single.this.uploadString + Uri.encode(Single.this.soundString));
          Single.this.mediaPlayer.prepare();
          Environment.getExternalStorageDirectory();
          Single.this.mediaPlayer.setDataSource("data/data/ir.tg.bahar/files/" + Single.this.pidString + ".mp3");
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
      return null;
    }

    protected void onPostExecute(Object paramObject)
    {
      super.onPostExecute(paramObject);
      PowerManager localPowerManager = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
      Single.this.mWakeLock = localPowerManager.newWakeLock(1, getClass().getName());
      Single.this.mWakeLock.acquire();
      Single.this.pv.stop();
      Single.this.pd.dismiss();
      Single.this.play.setEnabled(true);
      Single.this.play.setClickable(true);
      Single.this.Update_seekbar_timer();
    }
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Single
 * JD-Core Version:    0.6.0
 */