package com.bloom.persianstory.controller;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bloom.persianstory.Application;
import com.bloom.persianstory.BaseActivity;
import com.bloom.persianstory.R;
import com.bloom.persianstory.model.Network.ConnectivityReceiver;
import com.bloom.persianstory.model.Network.DownloadTask;
import com.bloom.persianstory.model.entities.Response.Story;
import com.bloom.persianstory.model.util.SharedPreference;
import com.bloom.persianstory.model.util.Utilities;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class Single extends BaseActivity implements MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnCompletionListener {

    Story story ;
    TextView endtime;
    SeekBar seekbar;
    ImageView storyImage,btn_save,btn_like,btn_play;
    MediaPlayer mediaPlayer;
    ProgressDialog pd;
    public Handler handler = new Handler();

    @Override
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.single);
        if(getIntent().getExtras().getString("data")==null){
            this.finish();
        }else{
            story = new Gson().fromJson(getIntent().getExtras().getString("data"),Story.class);
            if(story==null)this.finish();
        }
        initViews();
        setListeners();
        if(new SharedPreference(getApplicationContext()).isSavedStory(story.getId())){
            story = new SharedPreference(this).getStory(story.getId());
            btn_save.setImageResource(R.drawable.btn_saved);
        }else{
            story.setSound(new SharedPreference(this).getBaseUrl()+story.getSound());
        }
        new Player().execute();
    }

    private void setListeners() {
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(null);
                int i = mediaPlayer.getDuration();
                int j =new Utilities().progressToTimer(seekBar.getProgress(), i);
                mediaPlayer.seekTo(j);
                Update_seekbar_timer();

            }
        });
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnCompletionListener(this);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new SharedPreference(getApplicationContext()).isLiked(story.getId())){
                    btn_like.setImageResource(R.drawable.btn_like_off_list);
                    new SharedPreference(getApplicationContext()).disLikeStory(story.getId());
                }else{
                    btn_like.setImageResource(R.drawable.btn_like_on_list);
                    new SharedPreference(getApplicationContext()).LikeStory(story.getId());
                }
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    player_pause();
                }else {
                    player_play();
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new SharedPreference(getApplicationContext()).isSavedStory(story.getId())){
                    new AlertDialog.Builder(Single.this).setTitle("حذف قصه").setMessage("آیا از حذف این قصه اطمینان دارید ؟").setPositiveButton("بله", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                            new File("data/data/com.bloom.persianstory/files/" + story.getId() + ".mp3").delete();
                            new SharedPreference(getApplicationContext()).deleteStory(story.getId());
                            finish();
                        }
                    }).setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        }
                    }).setIcon(R.drawable.ic_delete_forever_black_24dp).show();
                }else{

                    if (ConnectivityReceiver.isConnected(getApplicationContext())) {

                        btn_save.setEnabled(false);
                        Environment.getExternalStorageDirectory();
                        DownloadTask localDownloadTask = new DownloadTask(getApplicationContext(), new DownloadTask.changeProgressListener() {
                            @Override
                            public void progressChanged(int persent) {
                               endtime.setText(persent+"");
                            }

                            @Override
                            public void downloadComplete() {
                                btn_save.setImageResource(R.drawable.btn_saved);
                                Story tosave = story;
                                tosave.setSound("data/data/com.bloom.persianstory/files/" + story.getId() + ".mp3");
                                new SharedPreference(getApplicationContext()).SaveStory(tosave);
                                btn_save.setEnabled(true);
                            }

                            @Override
                            public void Error() {
                                try {
                                    btn_save.setEnabled(true);
                                    new File("data/data/com.bloom.persianstory/files/" + story.getId() + ".mp3").delete();
                                    new SharedPreference(getApplicationContext()).deleteStory(story.getId());
                                }catch (Exception e){}
                            }
                        });
                        String[] arrayOfString = new String[2];
                        arrayOfString[0] = story.getSound();
                        arrayOfString[1] = story.getId();
                        localDownloadTask.execute(arrayOfString);
                    }else{
                        Toast.makeText(getApplicationContext(),"خطا در اتصال به اینترنت",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void player_play() {

        mediaPlayer.start();
        btn_play.setImageResource(R.drawable.btn_pause_list);
        Update_seekbar_timer();
    }

    private void player_pause() {

        mediaPlayer.pause();
        btn_play.setImageResource(R.drawable.btn_play_list);
    }

    private void initViews() {
        endtime = (TextView) findViewById(R.id.endtime);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        storyImage = findViewById(R.id.storyImage);
        btn_save = findViewById(R.id.btn_save);
        btn_like = findViewById(R.id.btn_Like);
        btn_play = findViewById(R.id.btn_play);

        Glide.with(this)
                .load(new SharedPreference(this).getBaseUrl()+ story.getPic())
                .placeholder(R.drawable.logo)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(storyImage);
        if(new SharedPreference(this).isLiked(story.getId())){
            btn_like.setImageResource(R.drawable.btn_like_on_list);
        }else{
            btn_like.setImageResource(R.drawable.btn_like_off_list);
        }
        mediaPlayer = new MediaPlayer();
        pd = new ProgressDialog(this);
        pd.setMessage("منتظر باشید...");
        pd.setCancelable(false);
        pd.show();
    }

    private void Update_seekbar_timer() {
        try {
            if (this.mediaPlayer.isPlaying()) {
                Utilities util = new Utilities();
                int totatl_time = mediaPlayer.getDuration();
                int current_time = mediaPlayer.getCurrentPosition();
                endtime.setText("" + util.milliSecondsToTimer(totatl_time-current_time));
                endtime.setText("" + util.milliSecondsToTimer(totatl_time-current_time));
                int i = util.getProgressPercentage(current_time, totatl_time);
                seekbar.setProgress(i);
                Runnable local4 = new Runnable() {
                    public void run() {
                        Update_seekbar_timer();
                    }
                };
                handler.postDelayed(local4, 1000L);
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
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
        endtime.setText("00:00");
        player_pause();
    }

    public void onNetworkConnectionChanged(boolean paramBoolean) {
    }

    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }

    private class Player extends AsyncTask {
        private Player() {
        }

        protected Object doInBackground(Object[] paramArrayOfObject) {
try {
    mediaPlayer.setDataSource(story.getSound());
    mediaPlayer.prepare();
//    mediaPlayer.setDataSource("data/data/com.bloom.persianstory/files/" + pidString + ".mp3");
}catch (IOException ex){
    Log.e("thisTag",ex.toString()+"---------"+ex.getMessage());
}
            return null;
        }

        protected void onPostExecute(Object paramObject) {
            super.onPostExecute(paramObject);
            PowerManager localPowerManager = (PowerManager) getApplicationContext().getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock mWakeLock = localPowerManager.newWakeLock(1, getClass().getName());
            mWakeLock.acquire();
            pd.dismiss();
            player_play();
        }
    }
}
