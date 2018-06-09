package com.bloom.persianstory.model.db;

import com.bloom.persianstory.model.entities.Response.Story;

import java.util.ArrayList;

public abstract interface StoryListener
{
  public abstract void addStory(Story paramStory);

  public abstract ArrayList<Story> getAllStory();

  public abstract int getStoryCount();
}