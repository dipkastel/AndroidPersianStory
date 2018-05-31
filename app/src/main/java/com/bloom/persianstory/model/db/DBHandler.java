package com.bloom.persianstory.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper
  implements StoryListener
{
  private static final String DB_NAME = "db";
  private static final int DB_VERSION = 1;
  private static final String KEY_ID = "id";
  private static final String KEY_NAME = "name";
  private static final String KEY_PIC = "pic";
  private static final String KEY_PID = "pid";
  private static final String KEY_PRICE = "price";
  private static final String KEY_SOUND = "sound";
  private static final String KEY_STAR = "star";
  private static final String KEY_TELLER = "teller";
  private static final String KEY_TIME = "time";
  private static final String TABLE_NAME = "content";
  String CREATE_TABLE = "CREATE TABLE content (id INTEGER PRIMARY KEY,pid INTEGER,name TEXT,teller TEXT,pic TEXT,sound TEXT,star TEXT,time TEXT,price TEXT)";
  String DROP_TABLE = "DROP TABLE IF EXISTS content";
  public final String Name = "db";
  private String[] allColumns = { "id", "name", "time", "pid", "star", "pic", "sound", "teller", "price" };
  private final Context mycontext;
  public final String path = "data/data/ir.tg.bahar/databases/";
  SQLiteDatabase localSQLiteDatabase1;
  ArrayList localArrayList2;

  public DBHandler(Context paramContext)
  {
    super(paramContext, "db", null, 1);
    this.mycontext = paramContext;
  }

  public void addStory(Story paramStory)
  {
    SQLiteDatabase localSQLiteDatabase = getWritableDatabase();
    try
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(paramStory.getPid()));
      localContentValues.put("pid", Integer.valueOf(paramStory.getPid()));
      localContentValues.put("name", paramStory.getName());
      localContentValues.put("time", paramStory.getTime());
      localContentValues.put("star", paramStory.getStar());
      localContentValues.put("sound", paramStory.getSound());
      localContentValues.put("teller", paramStory.getTeller());
      localContentValues.put("pic", paramStory.getPic());
      localContentValues.put("price", paramStory.getPrice());
      localSQLiteDatabase.insert("content", null, localContentValues);
      localSQLiteDatabase.close();
      return;
    }
    catch (Exception localException)
    {
      Log.e("problem", localException + "");
    }
  }

  public boolean checkdb()
  {
    try
  {
    SQLiteDatabase localSQLiteDatabase2 = SQLiteDatabase.openDatabase("data/data/com.bloom.persianstory/databases/db", null, 1);
    localSQLiteDatabase1 = localSQLiteDatabase2;
    return localSQLiteDatabase1 != null;
  }
  catch (SQLException localSQLException)
  {
      return false;
  }
  }

  public void copydatabase()
    throws IOException
  {
    FileOutputStream localFileOutputStream = new FileOutputStream("data/data/com.bloom.persianstory/databases/db");
    byte[] arrayOfByte = new byte[1024];
    InputStream localInputStream = this.mycontext.getAssets().open("db");
    while (true)
    {
      int i = localInputStream.read(arrayOfByte);
      if (i <= 0)
        break;
      localFileOutputStream.write(arrayOfByte, 0, i);
    }
    localInputStream.close();
    localFileOutputStream.flush();
    localFileOutputStream.close();
  }

  public void deleteStory(Integer paramInteger)
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    localSQLiteDatabase.delete("content", "pid=" + paramInteger, null);
    localSQLiteDatabase.close();
  }

  public ArrayList<Story> getAllStory()
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    Cursor localCursor = localSQLiteDatabase.rawQuery("SELECT id,name, pic, price FROM content ORDER BY id DESC", null);
    try
    {
      ArrayList localArrayList1 = new ArrayList();
      try
      {
        localCursor.moveToFirst();
        while (!localCursor.isAfterLast())
        {
          Story localStory = new Story();
          localStory.setId(localCursor.getInt(0));
          localStory.setName(localCursor.getString(1));
          localStory.setPic(localCursor.getString(2));
          localStory.setPrice(localCursor.getString(3));
          localArrayList1.add(localStory);
          localCursor.moveToNext();
        }
        localCursor.close();
        localSQLiteDatabase.close();
        return localArrayList1;
      }
      catch (Exception localException1)
      {
        localArrayList2 = localArrayList1;
        Log.e("error", localException1 + "");
        return localArrayList2;
      }
    }
    catch (Exception localException2)
    {
        ArrayList localArrayList2 = null;
        return getAllStory();
    }
  }

  public ArrayList<String> getStory(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    Cursor localCursor = getReadableDatabase().rawQuery("SELECT  * FROM content WHERE id=" + paramString, null);
    if (localCursor.moveToFirst())
      do
      {
        localArrayList.add(localCursor.getString(1));
        localArrayList.add(localCursor.getString(2));
        localArrayList.add(localCursor.getString(3));
        localArrayList.add(localCursor.getString(4));
        localArrayList.add(localCursor.getString(5));
        localArrayList.add(localCursor.getString(8));
        localCursor.moveToNext();
      }
      while (localCursor.moveToNext());
    return localArrayList;
  }

  public int getStoryCount()
  {
    SQLiteDatabase localSQLiteDatabase = getReadableDatabase();
    try
    {
      int i = localSQLiteDatabase.rawQuery("SELECT * FROM content", null).getCount();
      localSQLiteDatabase.close();
      return i;
    }
    catch (Exception localException)
    {
      Log.e("error", localException + "");
    }
    return 0;
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    try
    {
      paramSQLiteDatabase.execSQL(this.CREATE_TABLE);
      return;
    }
    catch (SQLException localSQLException)
    {
      localSQLException.printStackTrace();
    }
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL(this.DROP_TABLE);
    onCreate(paramSQLiteDatabase);
  }

  public void useable()
  {
    if (checkdb())
      return;
    getReadableDatabase();
    try
    {
      copydatabase();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.db.DBHandler
 * JD-Core Version:    0.6.0
 */