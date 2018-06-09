package com.bloom.persianstory.model.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bloom.persianstory.model.entities.Response.Story;
import com.bloom.persianstory.model.entities.Response.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amir on 5/31/2018.
 */

public class SharedPreference  {
    private Context context;
    private String P_NAME = "PersianStory";
    private String isSound = "isSound";
    private String Liked = "Liked";
    private String prefSavedStory = "prefSavedStory";


    private String baseUrl = "baseUrl";
    Gson gson;

    private String SP_user = "user";
    private List<Story> savedStory;

    public SharedPreference(Context context) {
        this.context = context;
        gson = new Gson();
    }
    private SharedPreferences getPreference(){
        return context.getSharedPreferences(P_NAME, Context.MODE_PRIVATE);
    };

    private Editor getEditor(){
        return getPreference().edit();
    }

    public User getUser() {
        String outPut = getPreference().getString(SP_user, "");
        return gson.fromJson(outPut,User.class);
    }

    public void setUser(User user) {
        String input = gson.toJson(user);
        Editor editor=getEditor();
        editor.putString(SP_user, input);
        editor.commit();
    }

    public boolean isSound() {
        return getPreference().getBoolean(isSound, true);
    }
    public void setIsSound(boolean Sound) {
        Editor editor=getEditor();
        editor.putBoolean(isSound, Sound);
        editor.commit();
    }

    public String getBaseUrl() {
        return getPreference().getString(baseUrl, "");
    }

    public void setBaseUrl(String _baseUrl) {
        Editor editor=getEditor();
        editor.putString(baseUrl, _baseUrl);
        editor.commit();
    }

    public void LikeStory(String id) {
        List<String> LikedIds = gelLikedIds();
        if(gelLikedIds()==null)
            LikedIds = new ArrayList<>();
        LikedIds.add(id);

        Editor editor=getEditor();
        editor.putString(Liked, gson.toJson(LikedIds));
        editor.commit();

    }

    private List<String> gelLikedIds() {
        List<String> idis =new ArrayList<>();

       String sids =  getPreference().getString(Liked, "");
       return gson.fromJson(sids,idis.getClass());
    }

    public boolean isLiked(String id){
        try {
            List<String> LikedIds = gelLikedIds();
            return LikedIds.contains(id);
        }catch (Exception E){
            return false;
        }
    }

    public void disLikeStory(String id) {

        List<String> LikedIds = gelLikedIds();
        LikedIds.remove(id);

        Editor editor=getEditor();
        editor.putString(Liked, gson.toJson(LikedIds));
        editor.commit();
    }

    public void SaveStory(Story story) {
        List<Story> savedStory = getSavedStory();
        if (getSavedStory() == null)
            savedStory = new ArrayList<>();
        savedStory.add(story);

        Editor editor = getEditor();
        editor.putString(prefSavedStory, gson.toJson(savedStory));
        editor.commit();

    }
    public boolean isSavedStory(String id){
        try {
            List<Story> savedStories = getSavedStory();
            for (Story story :savedStories){
                if(story.getId().equals(id))
                    return true;
            }
            return false;
        }catch (Exception E){
            return false;
        }
    }
    public Story getStory(String id){
        try {
            List<Story> savedStories = getSavedStory();
            for (Story story : savedStories ){
                if(story.getId().equals(id))
                    return story;
            }
            return null;
        }catch (Exception E){
            return null;
        }

    }
    public void deleteStory(String id){

        List<Story> SavedStory = getSavedStory();
        for (Story story :SavedStory){
            if(story.getId().equals(id)){
                SavedStory.remove(story);
                Editor editor=getEditor();
                editor.putString(prefSavedStory, gson.toJson(SavedStory));
                editor.commit();
                return;
            }
        }
    }

    public List<Story> getSavedStory() {
        String sstory =  getPreference().getString(prefSavedStory, "");
        return gson.fromJson(sstory, new TypeToken<List<Story>>(){}.getType());
    }
}
