package com.bloom.persianstory.controller;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bloom.persianstory.R;
import com.bloom.persianstory.model.Lists.ListAdapterZakhire;
import com.bloom.persianstory.model.db.DBHandler;

import java.io.IOException;
import java.util.ArrayList;

public class Zakhire extends AppCompatActivity
{
  public ListAdapterZakhire adapter;
  DBHandler handler;
  public ListView list;
  MediaPlayer mp;
  public ArrayList<com.bloom.persianstory.model.entities.Response.Story> storyList = new ArrayList();

  public void onBackPressed()
  {
    super.onBackPressed();
    finish();
    overridePendingTransition(R.anim.slide2, R.anim.slide2_out);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.list_activity);
    mp = new MediaPlayer();
    handler = new DBHandler(this);
    handler.useable();
    storyList.clear();
    //list = ((ListView)findViewById(R.id.list));
    adapter = new ListAdapterZakhire(this);
    list.setAdapter(adapter);
    storyList = handler.getAllStory();
    list.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        if (mp.isPlaying())
          mp.stop();
        try
        {
          mp.reset();
          AssetFileDescriptor localAssetFileDescriptor = getAssets().openFd("Click.wav");
          mp.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
          mp.prepare();
          mp.start();
          Long localLong = Long.valueOf(adapter.getItemId(paramInt));
          Intent localIntent = new Intent(getApplicationContext(), Single.class);
          localIntent.putExtra("status", "offline");
          localIntent.putExtra("id", localLong + "");
          startActivity(localIntent);
          overridePendingTransition(R.anim.slide2, R.anim.slide2_out);
          return;
        }
        catch (IllegalStateException localIllegalStateException)
        {
            localIllegalStateException.printStackTrace();
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
        }
      }
    });
  }


  protected void onResume()
  {
    super.onResume();
    handler = new DBHandler(this);
    handler.useable();
    storyList = handler.getAllStory();
    adapter.notifyDataSetChanged();
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Lists.Zakhire
 * JD-Core Version:    0.6.0
 */