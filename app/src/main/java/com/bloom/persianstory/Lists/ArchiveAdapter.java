package com.bloom.persianstory.Lists;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.bloom.persianstory.MyApplication;
import com.bloom.persianstory.R;

import java.util.List;

public class ArchiveAdapter extends BaseAdapter
{
  private static LayoutInflater inflater = null;
  ImageLoader imageLoader = MyApplication.getInstance().getImageLoader();
  Activity main;
  private List<Story> storyItems;

  public ArchiveAdapter(Activity paramActivity, List<Story> paramList)
  {
    this.main = paramActivity;
    this.storyItems = paramList;
  }

  public int getCount()
  {
    return this.storyItems.size();
  }

  public Object getItem(int paramInt)
  {
    return this.storyItems.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ViewHolderItem localViewHolderItem = new ViewHolderItem();
    Story localStory = null;
    if (paramView == null)
    {
      paramView = inflater.inflate(R.layout.cell_list, null);
      localViewHolderItem.price = ((TextView)paramView.findViewById(R.id.price));
      localViewHolderItem.name = ((TextView)paramView.findViewById(R.id.name));
      localViewHolderItem.ratetext = ((TextView)paramView.findViewById(R.id.ratetext));
      localViewHolderItem.rating = ((RatingBar)paramView.findViewById(R.id.ratingBar));
      localViewHolderItem.thumbNail = ((NetworkImageView)paramView.findViewById(R.id.thumbnail));
      paramView.setTag(localViewHolderItem);
      localStory = (Story)this.storyItems.get(paramInt);
      localViewHolderItem.thumbNail.setImageUrl(localStory.getPic(), this.imageLoader);
      localViewHolderItem.price.setText(localStory.getPrice() + " سکه");
      localViewHolderItem.name.setText(localStory.getName());
      localViewHolderItem.rating.setRating(Float.parseFloat(localStory.getRate()));
      localViewHolderItem = (ViewHolderItem)paramView.getTag();
      label228: localViewHolderItem.price.setText("رایگان");
    }
    return paramView;
  }

  static class ViewHolderItem
  {
    TextView name;
    TextView price;
    TextView ratetext;
    RatingBar rating;
    NetworkImageView thumbNail;
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Lists.ArchiveAdapter
 * JD-Core Version:    0.6.0
 */