package com.bloom.persianstory.model.Lists;

public class Story
{
  private String name;
  private String pic;
  private String id;
  private String price;
  private String rate="0";
  private String sound;
  private String teller;

  public Story()
  {
  }

  public Story(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.name = paramString1;
    this.sound = paramString2;
    this.pic = paramString3;
    this.teller = paramString4;
    this.id = paramString5;
    this.rate = paramString6;
    this.price = paramString7;
  }

  public String getName()
  {
    return this.name;
  }

  public String getPic()
  {
    return this.pic;
  }

  public String getPid()
  {
    return this.id;
  }

  public String getPrice()
  {
    return this.price;
  }

  public String getRate()
  {
    return this.rate;
  }

  public String getSound()
  {
    return this.sound;
  }

  public String getTeller()
  {
    return this.teller;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPic(String paramString)
  {
    this.pic = paramString;
  }

  public void setPid(String paramString)
  {
    this.id = paramString;
  }

  public void setPrice(String paramString)
  {
    this.price = paramString;
  }

  public void setRate(String paramString)
  {
    this.rate = paramString;
  }

  public void setSound(String paramString)
  {
    this.sound = paramString;
  }

  public void setTeller(String paramString)
  {
    this.teller = paramString;
  }
}
