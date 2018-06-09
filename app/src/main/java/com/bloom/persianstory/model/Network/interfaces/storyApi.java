package com.bloom.persianstory.model.Network.interfaces;

import com.bloom.persianstory.model.entities.Response.OperationResult;
import com.bloom.persianstory.model.entities.Response.PostResult;
import com.bloom.persianstory.model.entities.Response.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by amir on 5/31/2018.
 */

public interface storyApi {
    @POST("account/login")
    @FormUrlEncoded
    Call<OperationResult<User>> Login(@FieldMap Map<String,String> params);


    @POST("stories/page")
    @FormUrlEncoded
    Call<OperationResult<PostResult>> GetStories(@FieldMap Map<String,String> params);

}
