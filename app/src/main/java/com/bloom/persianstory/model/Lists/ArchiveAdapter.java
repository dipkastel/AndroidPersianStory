package com.bloom.persianstory.model.Lists;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.model.Lists.holder.ViewHolderItem;
import com.bloom.persianstory.R;

import java.util.List;

public class ArchiveAdapter extends BaseAdapter
{
  private ImageLoader mImageLoader;
  private RequestQueue mRequestQueue;
  Activity main;
  private PostResult storyItems;

  public ArchiveAdapter(Activity paramActivity, PostResult paramList)
  {
    main = paramActivity;
    storyItems = paramList;
    mImageLoader = getImageLoader();
  }
  public RequestQueue getRequestQueue()
  {
    if (mRequestQueue == null)
      mRequestQueue = Volley.newRequestQueue(main.getApplicationContext());
    return mRequestQueue;
  }
  public ImageLoader getImageLoader()
  {
    getRequestQueue();
    if (mImageLoader == null)
      mImageLoader = new ImageLoader(mRequestQueue,new LruBitmapCache());
    return mImageLoader;
  }
  public int getCount()
  {
    if(storyItems.getPosts() !=null)
    return storyItems.getPosts().size();
    else
      return 0;
  }

  public Object getItem(int paramInt)
  {
    return storyItems.getPosts().get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
      Log.e("parametrNimber ",String.valueOf(paramInt));
    if (paramView == null)
    {
      paramView = LayoutInflater.from(main).inflate(R.layout.cell_list, null);
      ViewHolderItem ViewHolderItem = new ViewHolderItem(paramView);
      paramView.setTag(ViewHolderItem);
        Story localStory = (Story)storyItems.getPosts().get(paramInt);
        ViewHolderItem.thumbNail.setImageUrl(storyItems.getUploadUrl()+localStory.getPic(), mImageLoader);
        ViewHolderItem.price.setText(localStory.getPrice() + " سکه");
        ViewHolderItem.name.setText(localStory.getPid());
        ViewHolderItem.rating.setRating(Float.parseFloat(localStory.getRate()));
        ViewHolderItem = (ViewHolderItem)paramView.getTag();
      label228: ViewHolderItem.price.setText("رایگان");
    }
    return paramView;
  }


  public void addRangeToTop(List<Story> stories)
  {
    storyItems.getPosts().addAll(stories);
  }
}
