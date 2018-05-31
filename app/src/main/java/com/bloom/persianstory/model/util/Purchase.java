package com.bloom.persianstory.model.util;

import org.json.JSONException;
import org.json.JSONObject;

public class Purchase
{
  String mDeveloperPayload;
  String mItemType;
  String mOrderId;
  String mOriginalJson;
  String mPackageName;
  int mPurchaseState;
  long mPurchaseTime;
  String mSignature;
  String mSku;
  String mToken;

  public Purchase(String paramString1, String paramString2, String paramString3)
    throws JSONException
  {
    this.mItemType = paramString1;
    this.mOriginalJson = paramString2;
    JSONObject localJSONObject = new JSONObject(this.mOriginalJson);
    this.mOrderId = localJSONObject.optString("orderId");
    this.mPackageName = localJSONObject.optString("packageName");
    this.mSku = localJSONObject.optString("productId");
    this.mPurchaseTime = localJSONObject.optLong("purchaseTime");
    this.mPurchaseState = localJSONObject.optInt("purchaseState");
    this.mDeveloperPayload = localJSONObject.optString("developerPayload");
    this.mToken = localJSONObject.optString("token", localJSONObject.optString("purchaseToken"));
    this.mSignature = paramString3;
  }

  public String getDeveloperPayload()
  {
    return this.mDeveloperPayload;
  }

  public String getItemType()
  {
    return this.mItemType;
  }

  public String getOrderId()
  {
    return this.mOrderId;
  }

  public String getOriginalJson()
  {
    return this.mOriginalJson;
  }

  public String getPackageName()
  {
    return this.mPackageName;
  }

  public int getPurchaseState()
  {
    return this.mPurchaseState;
  }

  public long getPurchaseTime()
  {
    return this.mPurchaseTime;
  }

  public String getSignature()
  {
    return this.mSignature;
  }

  public String getSku()
  {
    return this.mSku;
  }

  public String getToken()
  {
    return this.mToken;
  }

  public String toString()
  {
    return "PurchaseInfo(type:" + this.mItemType + "):" + this.mOriginalJson;
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.util.Purchase
 * JD-Core Version:    0.6.0
 */