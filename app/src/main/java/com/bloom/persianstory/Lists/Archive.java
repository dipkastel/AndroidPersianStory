package com.bloom.persianstory.Lists;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.ConnectivityReceiver;
import com.bloom.persianstory.Home;
import com.bloom.persianstory.Login;
import com.bloom.persianstory.MainActivity;
import com.bloom.persianstory.R;
import com.bloom.persianstory.Register;
import com.bloom.persianstory.Single;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Archive extends AppCompatActivity
  implements Download_data.download_complete, SwipeRefreshLayout.OnRefreshListener
{
  Activity act;
  ArchiveAdapter adapter;
  ProgressDialog alertDialog;
  private CoordinatorLayout coordinatorLayout;
  String currentprice;
  String imei;
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
  private List<Story> storylist = new ArrayList();
  private SwipeRefreshLayout swipeRefreshLayout;
  String teller;
  String uploadurl;
  String userid;

  private void checkConnection()
  {
    this.isConnected = ConnectivityReceiver.isConnected(this);
  }

//  public void check_buy(String paramString)
//  {
//    RequestQueue localRequestQueue = Volley.newRequestQueue(getApplicationContext());
//    int i = Integer.valueOf(this.price).intValue();
//    this.alertDialog = new ProgressDialog(this);
//    this.alertDialog.setMessage("منتظر باشید...");
//    this.alertDialog.setCancelable(false);
//    this.alertDialog.show();
//    localRequestQueue.add(new StringRequest(1, "http://bahar.persianstory.ir/JsonProfile/check_buy", new Response.Listener<String>()
//    {
//      public void onResponse(String paramString)
//      {
//        Log.i("aa", paramString);
//        if (paramString.contains("true"))
//        {
//          Archive.this.alertDialog.dismiss();
//          Intent localIntent = new Intent(Archive.this.getApplicationContext(), Single.class);
//          localIntent.putExtra("status", "online");
//          localIntent.putExtra("pid", Archive.this.oo + "");
//          localIntent.putExtra("name", Archive.this.name + "");
//          localIntent.putExtra("teller", Archive.this.teller + "");
//          localIntent.putExtra("uploadurl", Archive.this.uploadurl + "");
//          localIntent.putExtra("rate", Archive.this.rate + "");
//          localIntent.putExtra("sound", Archive.this.sound + "");
//          localIntent.putExtra("pic", Archive.this.pic + "");
//          localIntent.putExtra("price", price + "");
//          Archive.this.startActivity(localIntent);
//          return;
//        }
//        Archive.this.alertDialog.dismiss();
//        new AlertDialog.Builder(Archive.this).setTitle("خرید قصه").setMessage("آیا از خرید قصه \" " + Archive.this.name + " \" به قیمت " + Archive.this.price + " سکه اطمینان دارید ؟").setPositiveButton("بله میخرم", new DialogInterface.OnClickListener()
//        {
//          public void onClick(DialogInterface paramDialogInterface, int paramInt)
//          {
//           // Archive.this.checkprice(Archive.this.userid, Archive.this.imei, Archive.this.price);
//          }
//        }).setNegativeButton("انصراف", new DialogInterface.OnClickListener()
//        {
//          public void onClick(DialogInterface paramDialogInterface, int paramInt)
//          {
//          }
//        }).setIcon(2130837642).show();
//      }
//    }
//    , new Response.ErrorListener()
//    {
//      public void onErrorResponse(VolleyError paramVolleyError)
//      {
//        Archive.this.alertDialog.dismiss();
//        Log.i("aar", paramVolleyError + "");
//      }
//    }
//    , paramString)
//    {
//      protected Map<String, String> getParams()
//      {
//        try
//        {
//          HashMap localHashMap = new HashMap();
//          localHashMap.put("username", userid);
//          localHashMap.put("pid", Archive.this.oo + "");
//          return localHashMap;
//        }
//        catch (Exception localException)
//        {
//        }
//        return null;
//      }
//    });
//  }

//  public void checkprice(String paramString1, String paramString2, String paramString3)
//  {
//    RequestQueue localRequestQueue = Volley.newRequestQueue(getApplicationContext());
//    int i = Integer.valueOf(paramString3).intValue();
//    this.alertDialog = new ProgressDialog(this);
//    this.alertDialog.setMessage("منتظر باشید...");
//    this.alertDialog.setCancelable(false);
//    this.alertDialog.show();
//    localRequestQueue.add(new StringRequest(1, "http://bahar.persianstory.ir/JsonProfile/buy", new Response.Listener<String>()
//    {
//      public void onResponse(String paramString)
//      {
//        Archive.this.alertDialog.dismiss();
//        Log.i("aa", paramString);
//        String[] arrayOfString = paramString.split("--tg--");
//        if (arrayOfString[0].contains("1"))
//        {
//          Toast.makeText(Archive.this.getApplicationContext(), "حساب شما توسط مدیر مسدود شده است", 1).show();
//          Archive.this.startActivity(new Intent(Archive.this.getApplicationContext(), Home.class));
//          Archive.this.finish();
//          return;
//        }
//        if (arrayOfString[0].contains("6"))
//        {
//          Archive.this.startActivity(new Intent(Archive.this.getApplicationContext(), Home.class));
//          Archive.this.finish();
//          return;
//        }
//        SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(Archive.this.getApplicationContext()).edit();
//        localEditor.putString("p", arrayOfString[1].toString());
//        localEditor.apply();
//        if ((Integer.valueOf(price)) <= Integer.valueOf(arrayOfString[1]).intValue())
//        {
//          Intent localIntent = new Intent(Archive.this.getApplicationContext(), Single.class);
//          localIntent.putExtra("status", "online");
//          localIntent.putExtra("pid", Archive.this.oo + "");
//          localIntent.putExtra("name", Archive.this.name + "");
//          localIntent.putExtra("teller", Archive.this.teller + "");
//          localIntent.putExtra("uploadurl", Archive.this.uploadurl + "");
//          localIntent.putExtra("rate", Archive.this.rate + "");
//          localIntent.putExtra("sound", Archive.this.sound + "");
//          localIntent.putExtra("pic", Archive.this.pic + "");
//          localIntent.putExtra("price", price + "");
//          Toast.makeText(Archive.this.getApplicationContext(), "قصه با موفقیت خریداری شد", 0).show();
//          Archive.this.startActivity(localIntent);
//          return;
//        }
//        new AlertDialog.Builder(Archive.this).setTitle("افزایش سکه").setMessage("تعداد سکه های شما برای خرید قصه کافی نیست .").setPositiveButton("افزایش سکه", new DialogInterface.OnClickListener()
//        {
//          public void onClick(DialogInterface paramDialogInterface, int paramInt)
//          {
//            //Archive.this.startActivity(new Intent(Archive.this.getApplicationContext(), Buy.class));
//            Archive.this.finish();
//          }
//        }).setNegativeButton("انصراف", new DialogInterface.OnClickListener()
//        {
//          public void onClick(DialogInterface paramDialogInterface, int paramInt)
//          {
//          }
//        }).setIcon(2130837651).show();
//      }
//    }
//    , new Response.ErrorListener()
//    {
//      public void onErrorResponse(VolleyError paramVolleyError)
//      {
//        Archive.this.alertDialog.dismiss();
//        Log.i("aar", paramVolleyError + "");
//      }
//    }
//    , paramString1, i)
//    {
//      protected Map<String, String> getParams()
//      {
//        try
//        {
//          HashMap localHashMap = new HashMap();
//          localHashMap.put("username", userid);
//          localHashMap.put("pid", Archive.this.oo + "");
//          localHashMap.put("price", String.valueOf(price));
//          return localHashMap;
//        }
//        catch (Exception localException)
//        {
//        }
//        return null;
//      }
//    });
//  }

  public void customLoadMoreDataFromApi(int paramInt)
  {
    load(paramInt);
  }

  // ERROR //
  public void get_data(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc 155
    //   3: if_acmpne +19 -> 22
    //   6: aload_0
    //   7: aload_2
    //   8: invokestatic 159	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   11: invokevirtual 147	ir/tg/bahar/Lists/Archive:load	(I)V
    //   14: ldc 161
    //   16: ldc 163
    //   18: invokestatic 169	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   21: pop
    //   22: new 171	org/json/JSONObject
    //   25: dup
    //   26: aload_1
    //   27: invokespecial 173	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   30: astore 5
    //   32: aload 5
    //   34: ldc 175
    //   36: invokevirtual 179	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   39: astore 6
    //   41: aload_0
    //   42: aload 5
    //   44: ldc 181
    //   46: invokevirtual 185	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   49: putfield 187	ir/tg/bahar/Lists/Archive:uploadurl	Ljava/lang/String;
    //   52: iconst_0
    //   53: istore 7
    //   55: iload 7
    //   57: aload 6
    //   59: invokevirtual 192	org/json/JSONArray:length	()I
    //   62: if_icmpge +219 -> 281
    //   65: new 171	org/json/JSONObject
    //   68: dup
    //   69: aload 6
    //   71: iload 7
    //   73: invokevirtual 196	org/json/JSONArray:get	(I)Ljava/lang/Object;
    //   76: invokevirtual 202	java/lang/Object:toString	()Ljava/lang/String;
    //   79: invokespecial 173	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   82: astore 8
    //   84: new 204	ir/tg/bahar/Lists/Story
    //   87: dup
    //   88: invokespecial 205	ir/tg/bahar/Lists/Story:<init>	()V
    //   91: astore 9
    //   93: aload 8
    //   95: ldc 207
    //   97: invokevirtual 185	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   100: invokevirtual 210	java/lang/String:toString	()Ljava/lang/String;
    //   103: ldc 212
    //   105: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   108: ifeq +21 -> 129
    //   111: aload_0
    //   112: iconst_1
    //   113: putfield 218	ir/tg/bahar/Lists/Archive:loadingMore	Z
    //   116: aload_0
    //   117: getfield 220	ir/tg/bahar/Lists/Archive:coordinatorLayout	Landroid/support/design/widget/CoordinatorLayout;
    //   120: ldc 222
    //   122: iconst_0
    //   123: invokestatic 228	android/support/design/widget/Snackbar:make	(Landroid/view/View;Ljava/lang/CharSequence;I)Landroid/support/design/widget/Snackbar;
    //   126: invokevirtual 229	android/support/design/widget/Snackbar:show	()V
    //   129: aload 9
    //   131: aload 8
    //   133: ldc 230
    //   135: invokevirtual 185	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   138: invokevirtual 210	java/lang/String:toString	()Ljava/lang/String;
    //   141: invokevirtual 233	ir/tg/bahar/Lists/Story:setName	(Ljava/lang/String;)V
    //   144: aload 9
    //   146: aload 8
    //   148: ldc 234
    //   150: invokevirtual 185	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   153: invokevirtual 210	java/lang/String:toString	()Ljava/lang/String;
    //   156: invokevirtual 237	ir/tg/bahar/Lists/Story:setSound	(Ljava/lang/String;)V
    //   159: aload 9
    //   161: aload 8
    //   163: ldc 238
    //   165: invokevirtual 185	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   168: invokevirtual 210	java/lang/String:toString	()Ljava/lang/String;
    //   171: invokevirtual 241	ir/tg/bahar/Lists/Story:setTeller	(Ljava/lang/String;)V
    //   174: aload 9
    //   176: aload 8
    //   178: ldc 243
    //   180: invokevirtual 185	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   183: invokevirtual 210	java/lang/String:toString	()Ljava/lang/String;
    //   186: invokevirtual 246	ir/tg/bahar/Lists/Story:setRate	(Ljava/lang/String;)V
    //   189: aload 9
    //   191: aload 8
    //   193: ldc 247
    //   195: invokevirtual 185	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   198: invokevirtual 210	java/lang/String:toString	()Ljava/lang/String;
    //   201: invokevirtual 250	ir/tg/bahar/Lists/Story:setPrice	(Ljava/lang/String;)V
    //   204: aload 9
    //   206: new 252	java/lang/StringBuilder
    //   209: dup
    //   210: invokespecial 253	java/lang/StringBuilder:<init>	()V
    //   213: aload_0
    //   214: getfield 187	ir/tg/bahar/Lists/Archive:uploadurl	Ljava/lang/String;
    //   217: invokevirtual 257	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   220: aload 8
    //   222: ldc_w 258
    //   225: invokevirtual 185	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   228: invokevirtual 210	java/lang/String:toString	()Ljava/lang/String;
    //   231: invokevirtual 257	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   234: invokevirtual 259	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   237: invokevirtual 262	ir/tg/bahar/Lists/Story:setPic	(Ljava/lang/String;)V
    //   240: aload 9
    //   242: aload 8
    //   244: ldc_w 264
    //   247: invokevirtual 185	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   250: invokevirtual 210	java/lang/String:toString	()Ljava/lang/String;
    //   253: invokevirtual 267	ir/tg/bahar/Lists/Story:setPid	(Ljava/lang/String;)V
    //   256: aload_0
    //   257: getfield 50	ir/tg/bahar/Lists/Archive:storylist	Ljava/util/List;
    //   260: aload 9
    //   262: invokeinterface 271 2 0
    //   267: pop
    //   268: aload_0
    //   269: getfield 273	ir/tg/bahar/Lists/Archive:adapter	Lir/tg/bahar/Lists/ArchiveAdapter;
    //   272: invokevirtual 278	ir/tg/bahar/Lists/ArchiveAdapter:notifyDataSetChanged	()V
    //   275: iinc 7 1
    //   278: goto -223 -> 55
    //   281: aload_0
    //   282: getfield 280	ir/tg/bahar/Lists/Archive:swipeRefreshLayout	Landroid/support/v4/widget/SwipeRefreshLayout;
    //   285: iconst_0
    //   286: invokevirtual 285	android/support/v4/widget/SwipeRefreshLayout:setRefreshing	(Z)V
    //   289: aload_0
    //   290: invokestatic 291	android/preference/PreferenceManager:getDefaultSharedPreferences	(Landroid/content/Context;)Landroid/content/SharedPreferences;
    //   293: invokeinterface 297 1 0
    //   298: astore 12
    //   300: aload 12
    //   302: ldc_w 298
    //   305: aload_0
    //   306: getfield 187	ir/tg/bahar/Lists/Archive:uploadurl	Ljava/lang/String;
    //   309: invokeinterface 304 3 0
    //   314: pop
    //   315: aload 12
    //   317: invokeinterface 307 1 0
    //   322: return
    //   323: astore_3
    //   324: aload_3
    //   325: invokevirtual 310	org/json/JSONException:printStackTrace	()V
    //   328: aload_1
    //   329: ldc 155
    //   331: invokevirtual 216	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   334: ifne -12 -> 322
    //   337: aload_0
    //   338: getfield 280	ir/tg/bahar/Lists/Archive:swipeRefreshLayout	Landroid/support/v4/widget/SwipeRefreshLayout;
    //   341: iconst_0
    //   342: invokevirtual 285	android/support/v4/widget/SwipeRefreshLayout:setRefreshing	(Z)V
    //   345: return
    //   346: astore 10
    //   348: goto -219 -> 129
    //
    // Exception table:
    //   from	to	target	type
    //   6	22	323	org/json/JSONException
    //   22	52	323	org/json/JSONException
    //   55	93	323	org/json/JSONException
    //   93	129	323	org/json/JSONException
    //   129	275	323	org/json/JSONException
    //   281	322	323	org/json/JSONException
    //   93	129	346	java/lang/Exception
  }

  public void load(int paramInt)
  {
    if (this.isConnected)
    {
      this.swipeRefreshLayout.setRefreshing(true);
      Download_data localDownload_data = new Download_data(this);
      Bundle localBundle = getIntent().getExtras();
      if (localBundle != null)
        if (localBundle.getString("page").equals("best"))
          localDownload_data.download_data_from_link("http://bahar.persianstory.ir/JsonBest/page/" + paramInt, String.valueOf(paramInt));
        Log.i("page : ", "cur = " + paramInt);
        localDownload_data.download_data_from_link("http://bahar.persianstory.ir/JsonArchive/page/" + paramInt, String.valueOf(paramInt));

    }
    new Handler().postDelayed(new Runnable()
    {
      public void run()
      {
        Archive.this.startActivity(new Intent(Archive.this.getApplicationContext(), MainActivity.class));
        Toast.makeText(Archive.this.getApplicationContext(), "خطای اتصال به اینترنت", Toast.LENGTH_LONG).show();
        Archive.this.finish();
      }
    }
    , 1000L);
  }

  public void onBackPressed()
  {
    super.onBackPressed();
    new Download_data(this).kill();
    finish();
    overridePendingTransition(R.anim.slide2, R.anim.slide2_out);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.list_activity);
    this.list = ((ListView)findViewById(R.id.list));
    this.act = this;
    this.mp = new MediaPlayer();
    this.coordinatorLayout = ((CoordinatorLayout)findViewById(R.id.coordinatorLayout));
    this.adapter = new ArchiveAdapter(this, this.storylist);
    this.list.setAdapter(this.adapter);
    this.loadingMore = false;
    this.isConnected = false;
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    this.userid = localSharedPreferences.getString("us", "");
    this.imei = localSharedPreferences.getString("u", "");
    this.currentprice = localSharedPreferences.getString("p", "");
    this.swipeRefreshLayout = ((SwipeRefreshLayout)findViewById(R.id.swipe_refresh_layout));
    this.swipeRefreshLayout.setOnRefreshListener(this);
    checkConnection();
    this.swipeRefreshLayout.post(new Runnable()
    {
      public void run()
      {
        Archive.this.load(1);
      }
    });
    this.list.setOnScrollListener(new EndlessScrollListener()
    {
      public boolean onLoadMore(int paramInt1, int paramInt2)
      {
        Archive.this.customLoadMoreDataFromApi(paramInt1);
        return true;
      }
    });
    this.list.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        if (Archive.this.mp.isPlaying())
          Archive.this.mp.stop();
        try
        {
          Archive.this.mp.reset();
          AssetFileDescriptor localAssetFileDescriptor = Archive.this.getAssets().openFd("Click.wav");
          Archive.this.mp.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
          Archive.this.mp.prepare();
          Archive.this.mp.start();
          SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(Archive.this.getApplicationContext());
          Archive.this.userid = localSharedPreferences.getString("us", "");
          Archive.this.imei = localSharedPreferences.getString("u", "");
          Archive.this.currentprice = localSharedPreferences.getString("p", "");
          if (!Archive.this.isConnected){}
          Story localStory = (Story)Archive.this.storylist.get(paramInt);
          Archive.this.oo = localStory.getPid();
          Archive.this.name = localStory.getName();
          Archive.this.teller = localStory.getTeller();
          Archive.this.rate = localStory.getRate();
          Archive.this.sound = localStory.getSound();
          Archive.this.pic = localStory.getPic();
          Archive.this.price = localStory.getPrice();
          if (Integer.valueOf(Archive.this.price).intValue() > 0)
            if (Archive.this.userid.equals("مهمان"))
            {
              new AlertDialog.Builder(Archive.this).setTitle("ثبت نام / ورود").setMessage("برای استفاده از قصه های غیر رایگان لطفا ثبت نام کنید و یا وارد حساب شوید").setPositiveButton("ایجاد حساب", new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramDialogInterface, int paramInt)
                {
                  Archive.this.startActivity(new Intent(Archive.this, Register.class));
                  Archive.this.finish();
                }
              }).setNegativeButton("ورود به حساب", new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramDialogInterface, int paramInt)
                {
                  Archive.this.startActivity(new Intent(Archive.this, Login.class));
                  Archive.this.finish();
                }
              }).setNeutralButton("انصراف", new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramDialogInterface, int paramInt)
                {
                }
              }).setIcon(R.drawable.ic_account_box_black_24dp).show();
              return;
            }
        }
        catch (IllegalStateException localIllegalStateException)
        {
            localIllegalStateException.printStackTrace();
        }
        catch (IOException localIOException)
        {
            localIOException.printStackTrace();
         // Archive.this.check_buy(Archive.this.userid);
        }
        Intent localIntent = new Intent(Archive.this.getApplicationContext(), Single.class);
        localIntent.putExtra("status", "online");
        localIntent.putExtra("pid", Archive.this.oo + "");
        localIntent.putExtra("name", Archive.this.name + "");
        localIntent.putExtra("teller", Archive.this.teller + "");
        localIntent.putExtra("uploadurl", Archive.this.uploadurl + "");
        localIntent.putExtra("rate", Archive.this.rate + "");
        localIntent.putExtra("sound", Archive.this.sound + "");
        localIntent.putExtra("pic", Archive.this.pic + "");
        localIntent.putExtra("price", Archive.this.price + "");
        Archive.this.startActivity(localIntent);
        Archive.this.overridePendingTransition(2131034135, 2131034136);
        Toast.makeText(Archive.this.getApplicationContext(), "خطای اتصال به اینترنت", Toast.LENGTH_SHORT).show();
      }
    });
  }

  protected void onDestroy()
  {
    super.onDestroy();
  }

  public void onRefresh()
  {
    this.swipeRefreshLayout.post(new Runnable()
    {
      public void run()
      {
        Archive.this.storylist.clear();
        Archive.this.load(1);
        Archive.this.list.setOnScrollListener(new EndlessScrollListener()
        {
          public boolean onLoadMore(int paramInt1, int paramInt2)
          {
            Archive.this.customLoadMoreDataFromApi(paramInt1 + 1);
            return true;
          }
        });
      }
    });
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Lists.Archive
 * JD-Core Version:    0.6.0
 */