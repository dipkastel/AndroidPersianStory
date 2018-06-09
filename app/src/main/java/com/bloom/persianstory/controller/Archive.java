package com.bloom.persianstory.controller;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bloom.persianstory.Application;
import com.bloom.persianstory.BaseActivity;
import com.bloom.persianstory.R;
import com.bloom.persianstory.model.Lists.holder.StoryListViewHolder;
import com.bloom.persianstory.model.Network.ConnectivityReceiver;
import com.bloom.persianstory.model.entities.Response.OperationResult;
import com.bloom.persianstory.model.entities.Response.PostResult;
import com.bloom.persianstory.model.entities.Response.Story;
import com.bloom.persianstory.model.util.SharedPreference;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Archive extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    private int page = 1;
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter<Story> adapter;
    Handler handler = new Handler();
    boolean isSaved = false;

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.list_activity);
        recyclerView = findViewById(R.id.recyclerView);

            isSaved = getIntent().getExtras().getBoolean("isSaved",false);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter<Story>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new StoryListViewHolder(parent);
            }
        });
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isSaved)
                        {
                            adapter.stopMore();
                            return;
                        }
                        if (!ConnectivityReceiver.isConnected(getApplicationContext())) {
                            adapter.pauseMore();
                            return;
                        }
                        LoadPage(page);
                        page++;
                    }
                }, 100);
            }
        });

        adapter.setNoMore(R.layout.view_nomore);
        adapter.setError(R.layout.view_error, new RecyclerArrayAdapter.OnErrorListener() {
            @Override
            public void onErrorShow() {
                adapter.resumeMore();
            }

            @Override
            public void onErrorClick() {
                adapter.resumeMore();
            }
        });

        if(isSaved){
            recyclerView.getSwipeToRefresh().setEnabled(false);
            if(new SharedPreference(this).getSavedStory().size()>0){
                adapter.addAll(new SharedPreference(this).getSavedStory());
            }
            else{
                LayoutInflater factory = LayoutInflater.from(this);
                View myView = factory.inflate(R.layout.empty_view, null);
                recyclerView.setProgressView(myView);
            }
            return;
        }else{
            recyclerView.setRefreshListener(this);
            onRefresh();
        }


    }

    public void LoadPage(int page) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("phoneNumber", new SharedPreference(this).getUser().getPhoneNumber());
        params.put("password", new SharedPreference(this).getUser().getPassword());
        params.put("deviceId", new SharedPreference(this).getUser().getDevice_id());
        params.put("pageNumber", page+"");
        Application.getInstance().server().GetStories(params).enqueue(new Callback<OperationResult<PostResult>>() {
            @Override
            public void onResponse(Call<OperationResult<PostResult>> call, Response<OperationResult<PostResult>> response) {
                if(response.body().isOk(response.body())){
                    if(new SharedPreference(getApplicationContext()).getBaseUrl().isEmpty())
                        new SharedPreference(getApplicationContext()).setBaseUrl(response.body().getData().getUploadUrl());
                    adapter.addAll(response.body().getData().getPosts());
                }else{

                    Toast.makeText(getApplicationContext(),"خطا در دریافت اطلاعات",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<OperationResult<PostResult>> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide2, R.anim.slide2_out);
    }

    public void onRefresh() {
        page = 1;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                if (!ConnectivityReceiver.isConnected(getApplicationContext())) {
                    adapter.pauseMore();
                    return;
                }
                LoadPage(1);
                page=2;
            }
        }, 100);
    }

    @Override
    public void onNetworkConnectionChanged(boolean paramBoolean) {

    }

}
