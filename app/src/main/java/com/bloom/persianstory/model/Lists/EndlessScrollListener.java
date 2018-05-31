package com.bloom.persianstory.model.Lists;

import android.widget.AbsListView;

public abstract class EndlessScrollListener  implements AbsListView.OnScrollListener
{
  private int currentPage = 0;
  private boolean loading = true;
  private int previousTotalItemCount = 0;
  private int startingPageIndex = 0;
  private int visibleThreshold = 0;

  public abstract boolean onLoadMore(int paramInt1, int paramInt2);

  public void onScroll(AbsListView paramAbsListView, int FromItem, int visibleItems, int ListCount)
  {
    if (ListCount < previousTotalItemCount)
    {
      currentPage = startingPageIndex;
      previousTotalItemCount = ListCount;
      if (ListCount == 0)
        loading = true;
    }
    if ((loading) && (ListCount > previousTotalItemCount))
    {
      loading = false;
      previousTotalItemCount = ListCount;
      currentPage = (1 + currentPage);
    }
    if ((!loading) && (FromItem + visibleItems + visibleThreshold >= ListCount))
      loading = onLoadMore(1 + currentPage, ListCount);
  }

  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
  }
}
