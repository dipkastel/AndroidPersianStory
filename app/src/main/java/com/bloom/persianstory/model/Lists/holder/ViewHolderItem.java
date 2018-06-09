package com.bloom.persianstory.model.Lists.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by amir on 5/26/2018.
 */

public class ViewHolderItem extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView price;
    public TextView ratetext;
    public RatingBar rating;
    public NetworkImageView thumbNail;

    public ViewHolderItem(View itemView) {
        super(itemView);

//        this.price =itemView.findViewById(R.id.price);
//        this.name = itemView.findViewById(R.id.name);
//        this.ratetext =itemView.findViewById(R.id.ratetext);
//        this.rating = itemView.findViewById(R.id.ratingBar);
//        this.thumbNail = itemView.findViewById(R.id.thumbnail);
    }
}
