package com.bloom.persianstory.model.util;

import android.support.v7.app.AppCompatActivity;

public class Buy extends AppCompatActivity {
}

//  static final int RC_REQUEST = 10001;
//  static String SKU_PREMIUM;
//  static final String TAG = "Bahar";
//  Activity act;
//  IabHelper.QueryInventoryFinishedListener mGotInventoryListener;
//  IabHelper mHelper;
//  boolean mIsPremium = false;
//  IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
//  ImageView pack1;
//  ImageView pack2;
//  ImageView pack3;
//  ProgressDialog pd;
//  private Typeface tf;
//  String username;
//
//  private void MasrafSeke(Purchase paramPurchase)
//  {
//    this.mHelper.consumeAsync(paramPurchase, new IabHelper.OnConsumeFinishedListener()
//    {
//      public void onConsumeFinished(Purchase paramPurchase, IabResult paramIabResult)
//      {
//        if (paramIabResult.isSuccess())
//        {
//          Buy.this.pd.dismiss();
//          Toast.makeText(Buy.this.getApplicationContext(), "خرید با موفقیت انجام شد", 1).show();
//          Buy.this.startActivity(new Intent(Buy.this, MainActivity.class));
//          Buy.this.finish();
//        }
//      }
//    });
//  }
//
//  public void buy(Purchase paramPurchase, String paramString1, String paramString2)
//  {
//    this.pd.show();
//    RequestQueue localRequestQueue = Volley.newRequestQueue(getApplicationContext());
//    String str = "Tg_" + "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADC==" + this.username;
//    localRequestQueue.add(new StringRequest(1, "http://bahar.persianstory.ir/JsonBuy/pay", new Response.Listener(paramPurchase)
//    {
//      public void onResponse(String paramString)
//      {
//        Log.i("aass", paramString);
//        if (paramString.contains("true"))
//        {
//          Buy.this.pd.dismiss();
//          Buy.this.MasrafSeke(this.val$purchase);
//          return;
//        }
//        Buy.this.pd.dismiss();
//        Log.i("aarr", paramString + "");
//        Buy.this.MasrafSeke(this.val$purchase);
//        Toast.makeText(Buy.this.getApplicationContext(), paramString.toString(), 1).show();
//      }
//    }
//    , new Response.ErrorListener(paramPurchase)
//    {
//      public void onErrorResponse(VolleyError paramVolleyError)
//      {
//        Buy.this.pd.dismiss();
//        Log.i("aavv", paramVolleyError + "");
//        Buy.this.MasrafSeke(this.val$purchase);
//      }
//    }
//    , paramString1, paramString2, str)
//    {
//      protected Map<String, String> getParams()
//      {
//        try
//        {
//          HashMap localHashMap = new HashMap();
//          localHashMap.put("user", Buy.this.username);
//          localHashMap.put("token", this.val$token);
//          localHashMap.put("pack", this.val$pack);
//          localHashMap.put("key", this.val$key);
//          return localHashMap;
//        }
//        catch (Exception localException)
//        {
//        }
//        return null;
//      }
//    });
//  }
//
//  public void check_patch(Purchase paramPurchase, String paramString1, String paramString2)
//  {
//    this.pd.show();
//    Volley.newRequestQueue(getApplicationContext()).add(new StringRequest(1, "http://bahar.persianstory.ir/JsonBuy/check_patch", new Response.Listener(paramPurchase, paramString1, paramString2)
//    {
//      public void onResponse(String paramString)
//      {
//        Log.i("aass2", paramString);
//        if (paramString.contains("true"))
//        {
//          Buy.this.pd.dismiss();
//          Buy.this.buy(this.val$purchase, this.val$tokenn, this.val$packk);
//          return;
//        }
//        Buy.this.pd.dismiss();
//        Log.i("aarr2", paramString + "");
//        Toast.makeText(Buy.this.getApplicationContext(), paramString.toString(), 1).show();
//      }
//    }
//    , new Response.ErrorListener()
//    {
//      public void onErrorResponse(VolleyError paramVolleyError)
//      {
//        Buy.this.pd.dismiss();
//        Log.i("aavv2", paramVolleyError + "");
//      }
//    }
//    , paramString1, paramString2)
//    {
//      protected Map<String, String> getParams()
//      {
//        try
//        {
//          HashMap localHashMap = new HashMap();
//          localHashMap.put("token", this.val$tokenn);
//          localHashMap.put("pack", this.val$packk);
//          return localHashMap;
//        }
//        catch (Exception localException)
//        {
//        }
//        return null;
//      }
//    });
//  }
//
//  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
//  {
//    super.onActivityResult(paramInt1, paramInt2, paramIntent);
//    Log.d("Bahar", "onActivityResult(" + paramInt1 + "," + paramInt2 + "," + paramIntent);
//    if (!this.mHelper.handleActivityResult(paramInt1, paramInt2, paramIntent))
//    {
//      super.onActivityResult(paramInt1, paramInt2, paramIntent);
//      return;
//    }
//    Log.d("Bahar", "onActivityResult handled by IABUtil.");
//  }
//
//  public void onBackPressed()
//  {
//    super.onBackPressed();
//    finish();
//    overridePendingTransition(2131034137, 2131034138);
//  }
//
//  protected void onCreate(Bundle paramBundle)
//  {
//    super.onCreate(paramBundle);
//    this.act = this;
//    setContentView(2130968603);
//    this.tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
//    this.pack1 = ((ImageView)findViewById(2131624097));
//    this.pack2 = ((ImageView)findViewById(2131624098));
//    this.pack3 = ((ImageView)findViewById(2131624099));
//    this.pd = new ProgressDialog(this);
//    this.pd.setMessage("منتظر باشید...");
//    this.pd.setCancelable(false);
//    this.pd.show();
//    this.username = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("us", "");
//    this.mHelper = new IabHelper(this, "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwCtbg/aP7dVwZIOSSKdO3JAJQ1mg2aor/MBj13+85UAT2M6G58l9OLUy/vSX5tjSq3zc9q6s+W9bt8kN3QlMyh72UxIJN9pZPWQ6Mjrogj9rGC26hH2doyioPjkz1K1WeECOWxz0F06hWxnrSKS5531PcB7schrBOgNjTBUzaXyitaSY6UsZ/xrYWINtZtjyDKL2K2YbnMZhV2MDsvY81uiOaKPB3uKU7WpXM+3ubkCAwEAAQ==");
//    this.mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener()
//    {
//      public void onQueryInventoryFinished(IabResult paramIabResult, Inventory paramInventory)
//      {
//        Log.d("Bahar", "Query inventory finished.");
//        if (paramIabResult.isFailure())
//        {
//          Buy.this.pd.dismiss();
//          Log.d("Bahar", "Failed to query inventory: " + paramIabResult);
//          return;
//        }
//        Buy.this.pd.dismiss();
//        Log.d("Bahar", "Query inventory was successful.");
//        Buy.this.mIsPremium = paramInventory.hasPurchase(Buy.SKU_PREMIUM);
//        if (Buy.this.mIsPremium)
//          Buy.this.MasrafSeke(paramInventory.getPurchase(Buy.SKU_PREMIUM));
//        StringBuilder localStringBuilder = new StringBuilder().append("User is ");
//        if (Buy.this.mIsPremium);
//        for (String str = "PREMIUM"; ; str = "NOT PREMIUM")
//        {
//          Log.d("Bahar", str);
//          Log.d("Bahar", "Initial inventory query finished; enabling main UI.");
//          return;
//        }
//      }
//    };
//    this.mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener()
//    {
//      public void onIabPurchaseFinished(IabResult paramIabResult, Purchase paramPurchase)
//      {
//        if (paramIabResult.isFailure())
//          Log.d("Bahar", "Error purchasing: " + paramIabResult);
//        do
//          return;
//        while (!paramPurchase.getSku().equals(Buy.SKU_PREMIUM));
//        Buy.this.check_patch(paramPurchase, paramPurchase.getToken(), paramPurchase.getSku());
//      }
//    };
//    Log.d("Bahar", "Starting setup.");
//    this.mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener()
//    {
//      public void onIabSetupFinished(IabResult paramIabResult)
//      {
//        Log.d("Bahar", "Setup finished.");
//        if (!paramIabResult.isSuccess())
//          Log.d("Bahar", "Problem setting up In-app Billing: " + paramIabResult);
//        Buy.this.mHelper.queryInventoryAsync(Buy.this.mGotInventoryListener);
//      }
//    });
//    this.pack1.setOnClickListener(new View.OnClickListener()
//    {
//      public void onClick(View paramView)
//      {
//        Buy.SKU_PREMIUM = "bahar_1000";
//        Buy.this.mHelper.launchPurchaseFlow(Buy.this, Buy.SKU_PREMIUM, 10001, Buy.this.mPurchaseFinishedListener, "tg");
//      }
//    });
//    this.pack2.setOnClickListener(new View.OnClickListener()
//    {
//      public void onClick(View paramView)
//      {
//        Buy.SKU_PREMIUM = "bahar_5000";
//        Buy.this.mHelper.launchPurchaseFlow(Buy.this, Buy.SKU_PREMIUM, 10001, Buy.this.mPurchaseFinishedListener, "tg");
//      }
//    });
//    this.pack3.setOnClickListener(new View.OnClickListener()
//    {
//      public void onClick(View paramView)
//      {
//        Buy.SKU_PREMIUM = "bahar_10000";
//        Buy.this.mHelper.launchPurchaseFlow(Buy.this, Buy.SKU_PREMIUM, 10001, Buy.this.mPurchaseFinishedListener, "tg");
//      }
//    });
//  }
//
//  public void onDestroy()
//  {
//    super.onDestroy();
//    if (this.mHelper != null)
//      this.mHelper.dispose();
//    this.mHelper = null;
//  }
//}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Buy
 * JD-Core Version:    0.6.0
 */