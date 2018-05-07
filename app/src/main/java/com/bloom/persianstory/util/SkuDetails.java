package com.bloom.persianstory.util;

import org.json.JSONException;
import org.json.JSONObject;

public class SkuDetails
{
  String mDescription;
  String mItemType;
  String mJson;
  String mPrice;
  String mSku;
  String mTitle;
  String mType;

  public SkuDetails(String paramString)
    throws JSONException
  {
    this("inapp", paramString);
  }

  public SkuDetails(String paramString1, String paramString2)
    throws JSONException
  {
    this.mItemType = paramString1;
    this.mJson = paramString2;
    JSONObject localJSONObject = new JSONObject(this.mJson);
    this.mSku = localJSONObject.optString("productId");
    this.mType = localJSONObject.optString("type");
    this.mPrice = localJSONObject.optString("price");
    this.mTitle = localJSONObject.optString("title");
    this.mDescription = localJSONObject.optString("description");
  }

  public String getDescription()
  {
    return this.mDescription;
  }

  public String getPrice()
  {
    return this.mPrice;
  }

  public String getSku()
  {
    return this.mSku;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public String getType()
  {
    return this.mType;
  }

  public String toString()
  {
    return "SkuDetails:" + this.mJson;
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.util.SkuDetails
 * JD-Core Version:    0.6.0
 */