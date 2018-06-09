package com.bloom.persianstory.model.Lists;

import android.content.Context;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.bloom.persianstory.Application;
import com.bloom.persianstory.R;
import com.bloom.persianstory.controller.Zakhire;

public class ListAdapterZakhire extends BaseAdapter
{
  Zakhire main;
  String uploadurl;

  public ListAdapterZakhire(Zakhire paramZakhire)
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
    return Long.valueOf((main.storyList.get(paramInt)).getId());
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewHolderItem localViewHolderItem = new ViewHolderItem();
    if (paramView == null)
    {
      paramView = ((LayoutInflater)this.main.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.cell_list, null);
//      localViewHolderItem.price = (paramView.findViewById(R.id.price));
//      localViewHolderItem.name = (paramView.findViewById(R.id.name));
//      localViewHolderItem.ratetext = (paramView.findViewById(R.id.ratetext));
//      localViewHolderItem.img = (paramView.findViewById(R.id.img));
//      localViewHolderItem.rating = (paramView.findViewById(R.id.ratingBar));
//      localViewHolderItem.thumbNail = (paramView.findViewById(R.id.thumbnail));
      localViewHolderItem.rating.setVisibility(View.GONE);
      localViewHolderItem.ratetext.setVisibility(View.GONE);
      paramView.setTag(localViewHolderItem);

      return paramView;
    }
    try
    {
      this.uploadurl = PreferenceManager.getDefaultSharedPreferences(this.main).getString("uploadurl", "");
      if (Integer.valueOf((this.main.storyList.get(paramInt)).getPrice()).intValue() > 0){
        localViewHolderItem.price.setText((this.main.storyList.get(paramInt)).getPrice() + " سکه");
        localViewHolderItem.name.setText((this.main.storyList.get(paramInt)).getName());
        localViewHolderItem.thumbNail.setImageUrl((this.main.storyList.get(paramInt)).getPic(), Application.getInstance().getImageLoader());
        localViewHolderItem = (ViewHolderItem)paramView.getTag();
        localViewHolderItem.price.setText("رایگان");
      }
    }
    catch (Exception localException)
    {
      Toast.makeText(this.main, "please refresh", Toast.LENGTH_SHORT).show();
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

