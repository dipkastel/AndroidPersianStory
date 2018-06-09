package com.bloom.persianstory.model.Lists.holder;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloom.persianstory.R;
import com.bloom.persianstory.controller.Single;
import com.bloom.persianstory.model.entities.Response.Story;
import com.bloom.persianstory.model.util.SharedPreference;
import com.bloom.persianstory.view.CircularNetworkImageView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by amir on 6/7/2018.
 */

public class StoryListViewHolder extends BaseViewHolder<Story> {

    private CircularNetworkImageView storyImage;
    private ImageView play,Like;
    private TextView Title;
    private TextView SubTitle;

    public StoryListViewHolder(ViewGroup parent) {
        super(parent, R.layout.cell_list);
        storyImage = (CircularNetworkImageView)$(R.id.storyImage);
        Title = (TextView) $(R.id.Title);
        SubTitle = (TextView)$(R.id.SubTitle);
        play = (ImageView) $(R.id.play);
        Like = (ImageView)$(R.id.Like);
    }



    @Override
    public void setData(final Story story){
        Log.i("ViewHolder","position"+getDataPosition());

        Title.setText(story.getName());
        SubTitle.setText(story.getTeller());
        Glide.with(getContext())
                .load(new SharedPreference(itemView.getContext()).getBaseUrl()+ story.getPic())
                .placeholder(R.drawable.logo)
                .bitmapTransform(new CropCircleTransformation(getContext()))
                .into(storyImage);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                Intent localIntent = new Intent(getContext(), Single.class);
                localIntent.putExtra("data", gson.toJson(story));
                getContext().startActivity(localIntent);

            }
        });
        Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new SharedPreference(getContext()).isLiked(story.getId())){
                    new SharedPreference(getContext()).disLikeStory(story.getId());
                    Like.setImageResource(R.drawable.btn_like_off_list);
                }else{

                    new SharedPreference(getContext()).LikeStory(story.getId());
                    Like.setImageResource(R.drawable.btn_like_on_list);
                }
            }
        });
        if(new SharedPreference(getContext()).isLiked(story.getId())){
            Like.setImageResource(R.drawable.btn_like_on_list);
        }else{
            Like.setImageResource(R.drawable.btn_like_off_list);
        }
    }



}
