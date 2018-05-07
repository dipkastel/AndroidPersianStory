package com.bloom.persianstory.Lists;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bloom.persianstory.MyApplication;
import com.bloom.persianstory.R;

public class ListAdapterZakhire extends BaseAdapter
{
  ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
  Zakhire main;
  String uploadurl;

  ListAdapterZakhire(Zakhire paramZakhire)
  {
    this.main = paramZakhire;
  }

  public int getCount()
  {
    return this.main.storyList.size();
  }

  public Object getItem(int paramInt)
  {
    return null;
  }

  public long getItemId(int paramInt)
  {
    return (main.storyList.get(paramInt)).getId();
  }

  @SuppressLint("WrongConstant")
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewHolderItem localViewHolderItem = new ViewHolderItem();
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.main.getSystemService("layout_inflater")).inflate(R.layout.cell_list, null);
      localViewHolderItem.price = ((TextView)paramView.findViewById(R.id.price));
      localViewHolderItem.name = ((TextView)paramView.findViewById(R.id.name));
      localViewHolderItem.ratetext = ((TextView)paramView.findViewById(R.id.ratetext));
      localViewHolderItem.img = ((ImageView)paramView.findViewById(R.id.img));
      localViewHolderItem.rating = ((RatingBar)paramView.findViewById(R.id.ratingBar));
      localViewHolderItem.thumbNail = ((NetworkImageView)paramView.findViewById(R.id.thumbnail));
      localViewHolderItem.rating.setVisibility(8);
      localViewHolderItem.ratetext.setVisibility(8);
      paramView.setTag(localViewHolderItem);

      return paramView;
    }
    try
    {
      this.uploadurl = PreferenceManager.getDefaultSharedPreferences(this.main).getString("uploadurl", "");
      if (Integer.valueOf((this.main.storyList.get(paramInt)).getPrice()).intValue() > 0){
        localViewHolderItem.price.setText((this.main.storyList.get(paramInt)).getPrice() + " سکه");
        localViewHolderItem.name.setText((this.main.storyList.get(paramInt)).getName());
        localViewHolderItem.thumbNail.setImageUrl((this.main.storyList.get(paramInt)).getPic(), this.imageLoader);
        localViewHolderItem = (ViewHolderItem)paramView.getTag();
        localViewHolderItem.price.setText("رایگان");
      }
    }
    catch (Exception localException)
    {
      Toast.makeText(this.main, "please refresh", 0).show();
    }
    return paramView;
  }

  static class ViewHolderItem
  {
    ImageView img;
    TextView name;
    TextView price;
    TextView ratetext;
    RatingBar rating;
    NetworkImageView thumbNail;
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Lists.ListAdapterZakhire
 * JD-Core Version:    0.6.0
 */