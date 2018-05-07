package com.bloom.persianstory.db;

import java.util.ArrayList;

public abstract interface StoryListener
{
  public abstract void addStory(Story paramStory);

  public abstract ArrayList<Story> getAllStory();

  public abstract int getStoryCount();
}

/* Location:           F:\programs\androidTools\app_dex2jar.jar
 * Qualified Name:     ir.tg.bahar.db.StoryListener
 * JD-Core Version:    0.6.0
 */