package com.bloom.persianstory.Lists;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public abstract class EndlessScrollListener
  implements AbsListView.OnScrollListener
{
  private int currentPage = 0;
  private boolean loading = true;
  private int previousTotalItemCount = 0;
  private int startingPageIndex = 0;
  private int visibleThreshold = 0;

  public EndlessScrollListener()
  {
  }

  public EndlessScrollListener(int paramInt)
  {
    this.visibleThreshold = paramInt;
  }

  public EndlessScrollListener(int paramInt1, int paramInt2)
  {
    this.visibleThreshold = paramInt1;
    this.startingPageIndex = paramInt2;
    this.currentPage = paramInt2;
  }

  public abstract boolean onLoadMore(int paramInt1, int paramInt2);

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt3 < this.previousTotalItemCount)
    {
      this.currentPage = this.startingPageIndex;
      this.previousTotalItemCount = paramInt3;
      if (paramInt3 == 0)
        this.loading = true;
    }
    if ((this.loading) && (paramInt3 > this.previousTotalItemCount))
    {
      this.loading = false;
      this.previousTotalItemCount = paramInt3;
      this.currentPage = (1 + this.currentPage);
    }
    if ((!this.loading) && (paramInt1 + paramInt2 + this.visibleThreshold >= paramInt3))
      this.loading = onLoadMore(1 + this.currentPage, paramInt3);
  }

  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
  }
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.Lists.EndlessScrollListener
 * JD-Core Version:    0.6.0
 */