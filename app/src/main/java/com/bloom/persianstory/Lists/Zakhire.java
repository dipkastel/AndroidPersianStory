package com.bloom.persianstory.Lists;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bloom.persianstory.R;
import com.bloom.persianstory.Single;
import com.bloom.persianstory.db.*;

import java.io.IOException;
import java.util.ArrayList;

public class Zakhire extends AppCompatActivity
  implements SwipeRefreshLayout.OnRefreshListener
{
  public ListAdapterZakhire adapter;
  DBHandler handler;
  public ListView list;
  MediaPlayer mp;
  public ArrayList<com.bloom.persianstory.db.Story> storyList = new ArrayList();
  private SwipeRefreshLayout swipeRefreshLayout;

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
    this.swipeRefreshLayout = ((SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout));
    this.swipeRefreshLayout.setOnRefreshListener(this);
    this.mp = new MediaPlayer();
    this.handler = new DBHandler(this);
    this.handler.useable();
    this.storyList.clear();
    this.list = ((ListView)findViewById(R.id.list));
    this.adapter = new ListAdapterZakhire(this);
    this.list.setAdapter(this.adapter);
    this.adapter = new ListAdapterZakhire(this);
    this.list.setAdapter(this.adapter);
    this.storyList = this.handler.getAllStory();
    this.list.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        if (Zakhire.this.mp.isPlaying())
          Zakhire.this.mp.stop();
        try
        {
          Zakhire.this.mp.reset();
          AssetFileDescriptor localAssetFileDescriptor = Zakhire.this.getAssets().openFd("Click.wav");
          Zakhire.this.mp.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
          Zakhire.this.mp.prepare();
          Zakhire.this.mp.start();
          Long localLong = Long.valueOf(Zakhire.this.adapter.getItemId(paramInt));
          Intent localIntent = new Intent(Zakhire.this.getApplicationContext(), Single.class);
          localIntent.putExtra("status", "offline");
          localIntent.putExtra("id", localLong + "");
          Zakhire.this.startActivity(localIntent);
          Zakhire.this.overridePendingTransition(2131034135, 2131034136);
          return;
        }
        catch (IllegalStateException localIllegalStateException)
        {
          while (true)
            localIllegalStateException.printStackTrace();
        }
        catch (IOException localIOException)
        {
          while (true)
            localIOException.printStackTrace();
        }
      }
    });
  }

  public void onRefresh()
  {
    this.swipeRefreshLayout.setRefreshing(false);
  }

  protected void onResume()
  {
    super.onResume();
    this.handler = new DBHandler(this);
    this.handler.useable();
    this.storyList = this.handler.getAllStory();
    this.adapter.notifyDataSetChanged();
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Lists.Zakhire
 * JD-Core Version:    0.6.0
 */