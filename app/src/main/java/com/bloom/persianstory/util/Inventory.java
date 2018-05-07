package com.bloom.persianstory.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Inventory
{
  Map<String, Purchase> mPurchaseMap = new HashMap();
  Map<String, SkuDetails> mSkuMap = new HashMap();

  void addPurchase(Purchase paramPurchase)
  {
    this.mPurchaseMap.put(paramPurchase.getSku(), paramPurchase);
  }

  void addSkuDetails(SkuDetails paramSkuDetails)
  {
    this.mSkuMap.put(paramSkuDetails.getSku(), paramSkuDetails);
  }

  public void erasePurchase(String paramString)
  {
    if (this.mPurchaseMap.containsKey(paramString))
      this.mPurchaseMap.remove(paramString);
  }

  List<String> getAllOwnedSkus()
  {
    return new ArrayList(this.mPurchaseMap.keySet());
  }

  List<String> getAllOwnedSkus(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.mPurchaseMap.values().iterator();
    while (localIterator.hasNext())
    {
      Purchase localPurchase = (Purchase)localIterator.next();
      if (!localPurchase.getItemType().equals(paramString))
        continue;
      localArrayList.add(localPurchase.getSku());
    }
    return localArrayList;
  }

  List<Purchase> getAllPurchases()
  {
    return new ArrayList(this.mPurchaseMap.values());
  }

  public Purchase getPurchase(String paramString)
  {
    return (Purchase)this.mPurchaseMap.get(paramString);
  }

  public SkuDetails getSkuDetails(String paramString)
  {
    return (SkuDetails)this.mSkuMap.get(paramString);
  }

  public boolean hasDetails(String paramString)
  {
    return this.mSkuMap.containsKey(paramString);
  }

  public boolean hasPurchase(String paramString)
  {
    return this.mPurchaseMap.containsKey(paramString);
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.util.Inventory
 * JD-Core Version:    0.6.0
 */