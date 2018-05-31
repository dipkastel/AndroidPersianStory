package com.bloom.persianstory.model.util;

import android.util.Log;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class Security
{
  private static final String KEY_FACTORY_ALGORITHM = "RSA";
  private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
  private static final String TAG = "IABUtil/Security";

//  public static PublicKey generatePublicKey(String paramString)
//  {
//    try
//    {
//      byte[] arrayOfByte = Base64.decode(paramString);
//      PublicKey localPublicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(arrayOfByte));
//      return localPublicKey;
//    }
//    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
//    {
//      throw new RuntimeException(localNoSuchAlgorithmException);
//    }
//    catch (InvalidKeySpecException localInvalidKeySpecException)
//    {
//      Log.e("IABUtil/Security", "Invalid key specification.");
//      throw new IllegalArgumentException(localInvalidKeySpecException);
//    }
//    catch (Base64DecoderException localBase64DecoderException)
//    {
//      Log.e("IABUtil/Security", "Base64 decoding failed.");
//    }
//   // throw new IllegalArgumentException(localBase64DecoderException);
//  }

  public static boolean verify(PublicKey paramPublicKey, String paramString1, String paramString2)
  {
    try
    {
      Signature localSignature = Signature.getInstance("SHA1withRSA");
      localSignature.initVerify(paramPublicKey);
      localSignature.update(paramString1.getBytes());
//      if (!localSignature.verify(Base64.decode(paramString2)))
//      {
//        Log.e("IABUtil/Security", "Signature verification failed.");
//        return false;
//      }
      return true;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      Log.e("IABUtil/Security", "NoSuchAlgorithmException.");
      return false;
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      Log.e("IABUtil/Security", "Invalid key specification.");
      return false;
    }
    catch (SignatureException localSignatureException)
    {
      Log.e("IABUtil/Security", "Signature exception.");
      return false;
    }
//    catch (Base64DecoderException localBase64DecoderException)
//    {
//      Log.e("IABUtil/Security", "Base64 decoding failed.");
//    }
//    return false;
  }

//  public static boolean verifyPurchase(String paramString1, String paramString2, String paramString3)
//  {
//    if ((TextUtils.isEmpty(paramString2)) || (TextUtils.isEmpty(paramString1)) || (TextUtils.isEmpty(paramString3)))
//    {
//      Log.e("IABUtil/Security", "Purchase verification failed: missing data.");
//      return false;
//    }
//    return verify(generatePublicKey(paramString1), paramString2, paramString3);
//  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.util.Security
 * JD-Core Version:    0.6.0
 */