package com.bloom.persianstory.controller;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.bloom.persianstory.R;
import com.bloom.persianstory.model.util.SharedPreference;
import com.flaviofaria.kenburnsview.KenBurnsView;

public class MainActivity extends AppCompatActivity {
    ImageView about, allStories, bartar, zakhire, sound;
    MediaPlayer player;


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        checkCreadit();
        initviews();
        initBgSound();
        initButtons();
        startAnimate();
    }

    private void startAnimate() {

        KenBurnsView localKenBurnsView1 = findViewById(R.id.ken);
        KenBurnsView localKenBurnsView2 = findViewById(R.id.ken2);
        localKenBurnsView1.resume();
        localKenBurnsView2.resume();
        allStories.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_bottom));
        about.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_and_rotate));
        bartar.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_cload));
        zakhire.setAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake_cload2));

    }

    private void initviews() {
        zakhire = findViewById(R.id.jadid);
        bartar = findViewById(R.id.bartar);
        allStories = findViewById(R.id.bahar);
        sound = findViewById(R.id.sound);
        about = findViewById(R.id.about);
    }

    private void initButtons() {
        sound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                if (player.isPlaying()) {
                    new SharedPreference(MainActivity.this).setIsSound(false);
                    player.pause();
                    sound.setImageResource(R.drawable.ic_volume_off_white_24dp);
                } else {
                    new SharedPreference(MainActivity.this).setIsSound(true);
                    player.start();
                    sound.setImageResource(R.drawable.ic_volume_up_white_24dp);
                }
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                new AlertDialog.Builder(MainActivity.this).setTitle("درباره ما").setMessage("با تشکر از انتخاب قصه های بهار.\r\nاین برنامه در سال 1395 در مارکت های موبایلی و با هدف گسترش سرگرمی و تفریح برای کودکان منتشر شده.\r\nقصه های بهار توسط گروه ایکس طراحی شده.\r\n تمامی حقوق برنامه و قصه های منتشر شده برای گروه ایکس محفوظ میباشد و کپی برداری از قصه ها و انتشار آن ها قانونا و شرعا غیر مجاز است.\r\nایمیل جهت ارتباط : info@studionegative.ir").setPositiveButton("بستن", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                }).setIcon(R.drawable.ic_account_box_black_24dp).show();
            }
        });

        zakhire.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(MainActivity.this, Archive.class);
                localIntent.putExtra("isSaved", true);
                startActivity(localIntent);
                overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
            }
        });
        bartar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(getApplicationContext(), Archive.class);
                localIntent.putExtra("page", "best");
                startActivity(localIntent);
                overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
            }
        });
        allStories.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent(getApplicationContext(), Archive.class);
                localIntent.putExtra("page", "last");
                startActivity(localIntent);
                overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
            }
        });
    }

    private void initBgSound() {
        player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true);
        player.setVolume(0.3F, 0.3F);
        if (new SharedPreference(this).isSound())
            player.start();
    }

    private void checkCreadit() {
        switch (new SharedPreference(this).getUser().getStatus()) {
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;

        }
    }

    protected void onResume() {

        if (new SharedPreference(this).isSound()) {
            player.start();
            sound.setImageResource(R.drawable.ic_volume_up_white_24dp);
        } else {
            sound.setImageResource(R.drawable.ic_volume_off_white_24dp);
        }
        super.onResume();
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("خروج").setMessage("آیا قصد خروج از برنامه را دارید ؟").setPositiveButton("بله ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                finish();
            }
        }).setNegativeButton("خروج و حمایت", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                try {
                    Intent localIntent = new Intent("android.intent.action.EDIT");
                    localIntent.setData(Uri.parse("bazaar://details?id=ir.tg.allStories"));
                    localIntent.setPackage("com.farsitel.bazaar");
                    startActivity(localIntent);
                    finish();
                    return;
                } catch (ActivityNotFoundException localActivityNotFoundException) {
                    Toast.makeText(getApplicationContext(), "لطفا بازار را روی گوشی نصب کنید", Toast.LENGTH_SHORT).show();
                }
            }
        }).setNeutralButton("انصراف", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
            }
        }).setIcon(R.drawable.ic_warning_black_24dp).show();
    }

    protected void onStop() {
        super.onStop();
        player.pause();
    }

    protected void onDestroy() {
        super.onDestroy();
        finish();
        player.stop();
        player.release();
    }
}
