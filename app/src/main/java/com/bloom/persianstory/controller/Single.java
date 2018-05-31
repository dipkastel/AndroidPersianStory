package com.bloom.persianstory.controller;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.MyApplication;
import com.bloom.persianstory.model.Network.ConnectivityReceiver;
import com.bloom.persianstory.model.Network.DownloadTask;
import com.bloom.persianstory.model.Lists.Story;
import com.bloom.persianstory.R;
import com.bloom.persianstory.model.util.Utilities;
import com.bloom.persianstory.model.db.DBHandler;
import com.rey.material.widget.Button;
import com.rey.material.widget.ProgressView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Single extends AppCompatActivity
        implements ConnectivityReceiver.ConnectivityReceiverListener, MediaPlayer.OnBufferingUpdateListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {
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

    private void Update_seekbar_timer() {
        try {
            if (this.mediaPlayer.isPlaying()) {
                totatl_time = mediaPlayer.getDuration();
                current_time = mediaPlayer.getCurrentPosition();
                total_time_textview.setText("" + utils.milliSecondsToTimer(this.totatl_time));
                current_time_textview.setText("" + utils.milliSecondsToTimer(this.current_time));
                int i = utils.getProgressPercentage(this.current_time, totatl_time);
                seekbar.setProgress(i);
                Runnable local4 = new Runnable() {
                    public void run() {
                        Update_seekbar_timer();
                    }
                };
                handler.postDelayed(local4, 1000L);
            }
            return;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    private void checkConnection() {
        isConnected = ConnectivityReceiver.isConnected(this);
        if (!this.offlinemod)
            showSnack(this.isConnected);
    }

  private boolean isMyServiceRunning(Class<?> serviceClass)
  {
      ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
      for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
          if (serviceClass.getName().equals(service.service.getClassName())) {
              return true;
          }
      }
      return false;
  }

    private void showSnack(boolean paramBoolean) {
        Snackbar localSnackbar = Snackbar.make(this.cr, "خطا در اتصال به اینترنت", 0);
        ((TextView) localSnackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(-256);
        if (!paramBoolean)
            localSnackbar.show();
    }

    public void help(Activity paramActivity) {
        final Dialog localDialog = new Dialog(paramActivity);
        localDialog.requestWindowFeature(1);
        localDialog.setContentView(R.layout.rating);
        // localDialog.getWindow().setBackgroundDrawableResource(17170445);
        localDialog.show();
        final RatingBar localRatingBar = (RatingBar) localDialog.findViewById(R.id.ratingBar);
        Button localButton = (Button) localDialog.findViewById(R.id.btnSubmit);
        localButton.setTypeface(this.tf);
        localRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar paramRatingBar, float paramFloat, boolean paramBoolean) {
                String str;
                switch (Math.round(paramRatingBar.getRating())) {
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
        localButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Volley.newRequestQueue(getApplicationContext()).add(new JsonObjectRequest(0, "http://bahar.persianstory.ir/SinglePost/add_rate_mob?rate=" + Math.round(localRatingBar.getRating()) + "&pid=" + pidString, null, new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject paramJSONObject) {
                        Log.d("Response", paramJSONObject.toString());
                        try {
                            boolean bool = paramJSONObject.get("status").toString().equals("ok");
                            if (bool) ;
                            return;
                        } catch (JSONException localJSONException) {
                            localJSONException.printStackTrace();
                        }
                    }
                }
                        , new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError paramVolleyError) {
                    }
                }));
                Toast.makeText(getApplicationContext(), "امتیاز شما ثبت شد", Toast.LENGTH_LONG).show();
                localDialog.dismiss();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        if (this.mediaPlayer.isPlaying())
            mediaPlayer.stop();
        finish();
        overridePendingTransition(R.anim.slide2, R.anim.slide2_out);
    }

    public void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt) {
        seekbar.setSecondaryProgress(paramInt);
    }

    public void onCompletion(MediaPlayer paramMediaPlayer) {
        handler.removeCallbacks(null);
        seekbar.setProgress(0);
        total_time_textview.setText("00:00");
        current_time_textview.setText("00:00");
        play.setText("پخش");
    }

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.single);
        act = this;
        SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        uploadString = localSharedPreferences.getString("uploadurl", "");
        pv = (ProgressView) findViewById(R.id.loading);
        sound = localSharedPreferences.getString("sound", "");
        pd = new ProgressDialog(this);
        pd.setMessage("منتظر باشید...");
        pd.setCancelable(false);
        pd.show();
        context = this;
        utils = new Utilities();
        tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        dbhandler = new DBHandler(this.context);
        dbhandler.useable();
        seekbar.setOnSeekBarChangeListener(this);
        current_time_textview = (TextView) findViewById(R.id.duration);
        total_time_textview = (TextView) findViewById(R.id.endtime);
        rate = (Button) findViewById(R.id.rateSingle);
        play = (Button) findViewById(R.id.playSingle);
        save = (Button) findViewById(R.id.saveSingle);
        thumbNail = (NetworkImageView) findViewById(R.id.thumbnail2);
        nametv = (TextView) findViewById(R.id.nameStory);
        tellertv = (TextView) findViewById(R.id.tellerStory);
        ratetv = (TextView) findViewById(R.id.rateStory);
        play.setEnabled(false);
        play.setClickable(false);
        rate.setTypeface(this.tf);
        play.setTypeface(this.tf);
        save.setTypeface(this.tf);
        cr =  findViewById(R.id.c1);



        rate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                checkConnection();
                if (isConnected) {
                    help(act);
                    return;
                }
                showSnack(isConnected);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                checkConnection();
                if (mediaPlayer.isPlaying()) {
                    play.setText("پخش");
                    mediaPlayer.pause();
                    return;
                }
                mediaPlayer.start();
                Update_seekbar_timer();
                play.setText("توقف");
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                if (offlinemod) {
                    new AlertDialog.Builder(Single.this).setTitle("حذف قصه").setMessage("آیا از حذف این قصه اطمینان دارید ؟").setPositiveButton("بله", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            if (mediaPlayer.isPlaying())
                                mediaPlayer.stop();
                            new File("data/data/ir.tg.bahar/files/" + pidString + ".mp3").delete();
                            dbhandler.deleteStory(Integer.valueOf(pidString));
                            finish();
                        }
                    }).setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        }
                    }).setIcon(R.drawable.ic_delete_forever_black_24dp).show();
                    return;
                }
                DownloadTask localDownloadTask = new DownloadTask(context);
                String[] arrayOfString = new String[2];
                arrayOfString[0] = uploadString + Uri.encode(soundString);
                arrayOfString[1] = pidString;
                localDownloadTask.execute(arrayOfString);
                add.setSound(soundString);
                add.setPid(Integer.valueOf(pidString).toString());
                add.setPrice(priceString);
                add.setPic(picString);
                add.setTeller(tellerString);
                add.setName(nameString);
                stories.add(add);
                //dbhandler.addStory(add);
                new Player().execute(new Object[0]);
            }
        });



        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);

        Bundle localBundle = null;
        if (paramBundle == null) {
            localBundle = getIntent().getExtras();
            if (localBundle != null) {

                if (localBundle.getString("status").equals("offline")){

                    //offline
                    fillVars(localBundle);

                    pv.stop();
                    pd.dismiss();
                    ArrayList localArrayList = dbhandler.getStory(localBundle.getString("id"));
                    save.setText("حذف");
                    pidString = (String) localArrayList.get(0);
                    nameString = (String) localArrayList.get(1);
                    tellerString = (String) localArrayList.get(2);
                    picString = (String) localArrayList.get(3);
                    soundString = (String) localArrayList.get(4);
                    rateString = "0";
                    priceString = (String) localArrayList.get(5);
                }

            }

        }

            Intent intent = getIntent();
            offlinemod = false;
        pv.stop();
        pd.dismiss();
            //online
        uploadString = intent.getStringExtra("uploadurl");
        pidString = intent.getStringExtra("pid");
        nameString = intent.getStringExtra("name");
        tellerString = intent.getStringExtra("teller");
        picString = intent.getStringExtra("pic");
        soundString = intent.getStringExtra("sound");
        rateString = intent.getStringExtra("rate");
        priceString = intent.getStringExtra("price");
            checkConnection();
            rate.setEnabled(true);
            save.setEnabled(true);


        new Player().execute(new Object[0]);



        nametv.setText(this.nameString);
        tellertv.setText("قصه گو : " + tellerString);
        if (Float.valueOf(this.rateString).floatValue() > 0.0F)
            ratetv.setText("امتیاز : " + rateString + "");
        thumbNail.setImageUrl(this.picString, imageLoader);




    }

    private void fillVars(Bundle bundle) {
            pidString = bundle.getString("pid");
            nameString = bundle.getString("name");
            tellerString = bundle.getString("teller");
            picString = bundle.getString("pic");
            soundString = bundle.getString("sound");
            rateString = bundle.getString("rate");
            priceString = bundle.getString("price");
    }

    public void onNetworkConnectionChanged(boolean paramBoolean) {
        showSnack(paramBoolean);
    }

    public void onProgressChanged(SeekBar paramSeekBar, int paramInt, boolean paramBoolean) {
    }

    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectivityListener(this);
    }

    public void onStartTrackingTouch(SeekBar paramSeekBar) {
    }

    public void onStopTrackingTouch(SeekBar paramSeekBar) {
        handler.removeCallbacks(null);
        int i = mediaPlayer.getDuration();
        int j = utils.progressToTimer(paramSeekBar.getProgress(), i);
        mediaPlayer.seekTo(j);
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
//            Toast.makeText(getApplicationContext(), "امتیاز شما ثبت شد", 0).show();
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

    private class Player extends AsyncTask {
        private Player() {
        }

        protected Object doInBackground(Object[] paramArrayOfObject) {
            try {
                Environment.getExternalStorageDirectory();
                if (!offlinemod){
                    mediaPlayer.setDataSource(uploadString + Uri.encode(soundString));
 }else{

                    mediaPlayer.setDataSource("data/data/ir.tg.bahar/files/" + pidString + ".mp3");
                }
                mediaPlayer.prepare();
            } catch (IOException localIOException) {
                localIOException.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Object paramObject) {
            super.onPostExecute(paramObject);
            PowerManager localPowerManager = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
            mWakeLock = localPowerManager.newWakeLock(1, getClass().getName());
            mWakeLock.acquire();
            pv.stop();
            pd.dismiss();
            play.setEnabled(true);
            play.setClickable(true);
            Update_seekbar_timer();
        }
    }
}
