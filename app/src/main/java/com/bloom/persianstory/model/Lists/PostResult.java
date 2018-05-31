
package com.bloom.persianstory.model.Lists;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostResult {

    @SerializedName("posts")
    @Expose
    private List<Story> posts = null;
    @SerializedName("upload_url")
    @Expose
    private String uploadUrl;

    public List<Story> getPosts() {
        return posts;
    }

    public void setPosts(List<Story> posts) {
        this.posts = posts;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

}
