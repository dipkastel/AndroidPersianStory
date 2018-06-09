package com.bloom.persianstory.model.entities.Response;

public class Story
{
  //mi story
  private String name;
  private String pic;
  private String id;
  private String price;
  private String rate="0";
  private String sound;
  private String teller;

  public String getName()
  {
    return this.name;
  }

  public String getPic()
  {
    return this.pic;
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

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
