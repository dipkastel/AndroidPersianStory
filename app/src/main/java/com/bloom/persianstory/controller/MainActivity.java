package com.bloom.persianstory.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.bloom.persianstory.model.Lists.Archive;
import com.bloom.persianstory.model.Lists.Zakhire;
import com.bloom.persianstory.R;
import com.flaviofaria.kenburnsview.KenBurnsView;

public class MainActivity extends AppCompatActivity
{
  ImageView about;
  Activity act;
  ImageView bahar;
  ImageView bartar;
  TableLayout cr;
  SharedPreferences.Editor editor;
  ImageView hemayat;
  String imei;
  ImageView jadid;
  MediaPlayer player;
  SharedPreferences preferences;
  String price;
  ImageView profile;
  Animation shake;
  ImageView sound;
  String soundd;
  String userid;

  public void onBackPressed()
  {
    new AlertDialog.Builder(this).setTitle("خروج").setMessage("آیا قصد خروج از برنامه را دارید ؟").setPositiveButton("بله ", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
       finish();
      }
    }).setNegativeButton("خروج و حمایت", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        try
        {
          Intent localIntent = new Intent("android.intent.action.EDIT");
          localIntent.setData(Uri.parse("bazaar://details?id=ir.tg.bahar"));
          localIntent.setPackage("com.farsitel.bazaar");
         startActivity(localIntent);
         finish();
          return;
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          Toast.makeText(MainActivity.this.getApplicationContext(), "لطفا بازار را روی گوشی نصب کنید", Toast.LENGTH_SHORT).show();
        }
      }
    }).setNeutralButton("انصراف", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
      }
    }).setIcon(R.drawable.ic_warning_black_24dp).show();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_main);
    KenBurnsView localKenBurnsView1 = (KenBurnsView)findViewById(R.id.ken);
    KenBurnsView localKenBurnsView2 = (KenBurnsView)findViewById(R.id.ken2);
    localKenBurnsView1.resume();
    localKenBurnsView2.resume();
    this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    this.editor = this.preferences.edit();
    this.userid = this.preferences.getString("us", "");
    this.imei = this.preferences.getString("u", "");
    this.price = this.preferences.getString("p", "");
    this.soundd = this.preferences.getString("sound", "");
    this.act = this;
    this.cr = ((TableLayout)findViewById(R.id.tableLayout1));
    this.jadid = ((ImageView)findViewById(R.id.jadid));
    this.bartar = ((ImageView)findViewById(R.id.bartar));
    this.hemayat = ((ImageView)findViewById(R.id.hemayat));
    this.profile = ((ImageView)findViewById(R.id.profile));
    this.bahar = ((ImageView)findViewById(R.id.bahar));
    this.sound = ((ImageView)findViewById(R.id.sound));
    this.about = ((ImageView)findViewById(R.id.about));
    this.shake = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
    this.bahar.setAnimation(this.shake);
    this.player = MediaPlayer.create(this, R.raw.music);
    this.player.setLooping(true);
    this.player.setVolume(0.3F, 0.3F);
    if (this.soundd.equals("on"))
      this.player.start();
    while (true)
    {
      this.sound.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if (MainActivity.this.player.isPlaying())
          {
           preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext());
           editor =preferences.edit();
           editor.putString("sound", "off");
           editor.apply();
           player.pause();
           sound.setImageResource(R.drawable.ic_volume_off_white_24dp);
            return;
          }
         preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this.getApplicationContext());
         editor =preferences.edit();
         editor.putString("sound", "on");
         editor.apply();
         player.start();
         sound.setImageResource(R.drawable.ic_volume_up_white_24dp);
        }
      });
      this.hemayat.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          try
          {
            Intent localIntent = new Intent("android.intent.action.EDIT");
            localIntent.setData(Uri.parse("bazaar://details?id=ir.tg.bahar"));
            localIntent.setPackage("com.farsitel.bazaar");
           startActivity(localIntent);
            return;
          }
          catch (ActivityNotFoundException localActivityNotFoundException)
          {
            Toast.makeText(MainActivity.this.getApplicationContext(), "بازار را روی گوشی نصب کنید", Toast.LENGTH_SHORT).show();
          }
        }
      });
      this.about.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          new AlertDialog.Builder(MainActivity.this).setTitle("درباره ما").setMessage("با تشکر از انتخاب قصه های بهار.\r\nاین برنامه در سال 1395 در مارکت های موبایلی و با هدف گسترش سرگرمی و تفریح برای کودکان منتشر شده.\r\nقصه های بهار توسط گروه ایکس طراحی شده.\r\n تمامی حقوق برنامه و قصه های منتشر شده برای گروه ایکس محفوظ میباشد و کپی برداری از قصه ها و انتشار آن ها قانونا و شرعا غیر مجاز است.\r\nایمیل جهت ارتباط : info@studionegative.ir").setPositiveButton("بستن", new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
            }
          }).setIcon(R.drawable.ic_account_box_black_24dp).show();
        }
      });
      this.jadid.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
         startActivity(new Intent(MainActivity.this, Zakhire.class));
         overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
        }
      });
      this.bartar.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent(MainActivity.this.getApplicationContext(), Archive.class);
          localIntent.putExtra("page", "best");
         startActivity(localIntent);
         overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
        }
      });
      this.profile.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
         startActivity(new Intent(MainActivity.this, Profile.class));
         overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
        }
      });
      this.bahar.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          Intent localIntent = new Intent(MainActivity.this.getApplicationContext(), Archive.class);
          localIntent.putExtra("page", "last");
         startActivity(localIntent);
         overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
        }
      });
      this.sound.setImageResource(R.drawable.ic_volume_off_white_24dp);
      return;
    }
  }

  protected void onDestroy()
  {
    super.onDestroy();
    finish();
    this.player.stop();
    this.player.release();
  }

  protected void onResume()
  {
    this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    this.userid = this.preferences.getString("us", "");
    this.imei = this.preferences.getString("u", "");
    this.price = this.preferences.getString("p", "");
    this.soundd = this.preferences.getString("sound", "");
    if (this.soundd.equals("on"))
    {
      this.player.start();
      this.sound.setImageResource(R.drawable.ic_volume_up_white_24dp);
    }
    while (true)
    {
      super.onResume();
      this.sound.setImageResource(R.drawable.ic_volume_off_white_24dp);
      return;
    }
  }

  protected void onStop()
  {
    super.onStop();
    this.player.pause();
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.MainActivity
 * JD-Core Version:    0.6.0
 */