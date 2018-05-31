package com.bloom.persianstory.model.Lists;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.model.Network.ConnectivityReceiver;
import com.bloom.persianstory.controller.Login;
import com.bloom.persianstory.R;
import com.bloom.persianstory.controller.Register;
import com.bloom.persianstory.controller.Single;
import com.google.gson.Gson;

import java.io.IOException;

public class Archive extends AppCompatActivity
        implements Download_data.download_complete, SwipeRefreshLayout.OnRefreshListener {
    Activity act;
    ArchiveAdapter adapter;
    ProgressDialog alertDialog;
    private CoordinatorLayout coordinatorLayout;
    String currentprice;
    boolean isConnected;
    ListView list;
    boolean loadingMore;
    MediaPlayer mp;
    String name;
    Object oo;
    String pic;
    String price;
    String rate;
    String sound;
    private PostResult storylist = new PostResult();
    private SwipeRefreshLayout swipeRefreshLayout;
    String teller;
    String uploadurl;
    String userid;


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.list_activity);
        list = ((ListView) findViewById(R.id.list));
        act = this;
        mp = new MediaPlayer();
        coordinatorLayout = ((CoordinatorLayout) findViewById(R.id.coordinatorLayout));
        adapter = new ArchiveAdapter(this, this.storylist);
        list.setAdapter(this.adapter);
        loadingMore = true;
        isConnected = false;
        SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userid = localSharedPreferences.getString("us", "");
        currentprice = localSharedPreferences.getString("p", "");
        swipeRefreshLayout = ((SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout));
        swipeRefreshLayout.setOnRefreshListener(this);
        checkConnection();
        swipeRefreshLayout.post(new Runnable() {
            public void run() {
                LoadPage(1);
            }
        });
        list.setOnScrollListener(new EndlessScrollListener() {
            public boolean onLoadMore(int paramInt1, int paramInt2) {
                LoadPage(paramInt1);
                return true;
            }
        });
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
                if (mp.isPlaying())
                    mp.stop();
                try {
                    mp.reset();
                    AssetFileDescriptor localAssetFileDescriptor = getAssets().openFd("Click.wav");
                    mp.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
                    mp.prepare();
                    mp.start();
                    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    userid = localSharedPreferences.getString("us", "");
                    currentprice = localSharedPreferences.getString("p", "");
                    if (!isConnected) {
                    }
                    Story localStory = (Story) storylist.getPosts().get(paramInt);
                    oo = localStory.getPid();
                    name = localStory.getName();
                    teller = localStory.getTeller();
                    rate = localStory.getRate();
                    sound = localStory.getSound();
                    pic = localStory.getPic();
                    price = localStory.getPrice();
                    if (Integer.valueOf(price).intValue() > 0)
                        if (userid.equals("مهمان")) {
                            new AlertDialog.Builder(Archive.this).setTitle("ثبت نام / ورود").setMessage("برای استفاده از قصه های غیر رایگان لطفا ثبت نام کنید و یا وارد حساب شوید").setPositiveButton("ایجاد حساب", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Archive.this, Register.class));
                                    finish();
                                }
                            }).setNegativeButton("ورود به حساب", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    startActivity(new Intent(Archive.this, Login.class));
                                    finish();
                                }
                            }).setNeutralButton("انصراف", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                }
                            }).setIcon(R.drawable.ic_account_box_black_24dp).show();
                            return;
                        }
                } catch (IllegalStateException localIllegalStateException) {
                    localIllegalStateException.printStackTrace();
                } catch (IOException localIOException) {
                    localIOException.printStackTrace();
                    // check_buy(userid);
                }
                Intent localIntent = new Intent(getApplicationContext(), Single.class);
                localIntent.putExtra("status", "online");
                localIntent.putExtra("pid", oo + "");
                localIntent.putExtra("name", name + "");
                localIntent.putExtra("teller", teller + "");
                localIntent.putExtra("uploadurl", storylist.getUploadUrl() + "");
                localIntent.putExtra("rate", rate + "");
                localIntent.putExtra("sound", sound + "");
                localIntent.putExtra("pic", pic + "");
                localIntent.putExtra("price", price + "");
                startActivity(localIntent);
                overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
                Toast.makeText(getApplicationContext(), "خطای اتصال به اینترنت", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkConnection() {
        isConnected = ConnectivityReceiver.isConnected(this);
    }

    public void LoadPage(int page) {
        if (this.isConnected) {
            Download_data localDownload_data = new Download_data(this);
            Bundle localBundle = getIntent().getExtras();
            if (localBundle != null)
                if (localBundle.getString("page").equals("best"))
                    localDownload_data.download_data_from_link("http://persianstory.ir/JsonBest/page/" + page, String.valueOf(page));
                else
                    localDownload_data.download_data_from_link("http://persianstory.ir/JsonArchive/page/" + page, String.valueOf(page));

        }
    }

    public void get_data(String url, String page) {

        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){


            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                PostResult p = gson.fromJson(response, PostResult.class);
                if(p.getPosts().size()<10)

                swipeRefreshLayout.setRefreshing(false);

                if(storylist.getPosts()==null) {
                    storylist = p;
                    adapter = new ArchiveAdapter(Archive.this, storylist);
                    list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }else {

                    int index = list.getFirstVisiblePosition() + p.getPosts().size();
                    View v = list.getChildAt(list.getHeaderViewsCount());
                    int top = (v == null) ? 0 : v.getTop();

                    list.setSelectionFromTop(index, top);
                    adapter.addRangeToTop(p.getPosts());
                    adapter.notifyDataSetChanged();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                swipeRefreshLayout.setRefreshing(false);
                Log.e("asdasdasd", "asdasdasd");
            }
        }));

    }



    public void onBackPressed() {
        super.onBackPressed();
        new Download_data(this).kill();
        finish();
        overridePendingTransition(R.anim.slide2, R.anim.slide2_out);
    }

    public void onRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            public void run() {
                LoadPage(1);
                list.setOnScrollListener(new EndlessScrollListener() {
                    public boolean onLoadMore(int LastPage, int itemCount) {
                        LoadPage(LastPage + 1);
                        return true;
                    }
                });
            }
        });
    }
}
