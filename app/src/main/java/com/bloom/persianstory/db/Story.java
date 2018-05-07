package com.bloom.persianstory.db;

public class Story
{
  private String description;
  private int id;
  private String name;
  private String pic;
  private int pid;
  private String price;
  private String sound;
  private String star;
  private String teller;
  private String time;
  private String uploadurl;

  public Story()
  {
  }

  public Story(int paramInt1, int paramInt2)
  {
    this.id = paramInt1;
    this.pid = paramInt2;
  }

  public Story(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    this.name = paramString1;
    this.description = paramString2;
    this.teller = paramString3;
    this.time = paramString4;
    this.sound = paramString5;
    this.pic = paramString6;
    this.star = paramString7;
    this.price = paramString8;
    this.uploadurl = paramString9;
  }

  public String getDesc()
  {
    return this.description;
  }

  public int getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public String getPic()
  {
    return this.pic;
  }

  public int getPid()
  {
    return this.pid;
  }

  public String getPrice()
  {
    return this.price;
  }

  public String getSound()
  {
    return this.sound;
  }

  public String getStar()
  {
    return this.star;
  }

  public String getTeller()
  {
    return this.teller;
  }

  public String getTime()
  {
    return this.time;
  }

  public String getUploadurl()
  {
    return this.uploadurl;
  }

  public void setDesc(String paramString)
  {
    this.description = paramString;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPic(String paramString)
  {
    this.pic = paramString;
  }

  public void setPid(int paramInt)
  {
    this.pid = paramInt;
  }

  public void setPrice(String paramString)
  {
    this.price = paramString;
  }

  public void setSound(String paramString)
  {
    this.sound = paramString;
  }

  public void setStar(String paramString)
  {
    this.star = paramString;
  }

  public void setTeller(String paramString)
  {
    this.teller = paramString;
  }

  public void setTime(String paramString)
  {
    this.time = paramString;
  }

  public void setUploadurl(String paramString)
  {
    this.uploadurl = this.uploadurl;
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.db.Story
 * JD-Core Version:    0.6.0
 */