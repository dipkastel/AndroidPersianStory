package com.bloom.persianstory;

import android.provider.Settings;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.model.Network.LruBitmapCache;
import com.bloom.persianstory.model.Network.ConnectivityReceiver;
import com.bloom.persianstory.model.Network.interfaces.storyApi;
import com.bloom.persianstory.model.entities.Response.User;
import com.bloom.persianstory.model.util.SharedPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Application extends android.app.Application {
    private static Application mInstance;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    Retrofit retrofit ;
    public static Application getInstance() {
        return mInstance;
    }

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initUser();

    }

    private void initUser() {
        if(new SharedPreference(this).getUser()==null){
            User user = new User();
            user.setDevice_id(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                    Settings.Secure.ANDROID_ID));
            new SharedPreference(this).setUser(user);
        }
    }

    public storyApi server(){
        if (retrofit==null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://persianstory.ir/Api/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(storyApi.class);
    }
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (imageLoader == null)
            imageLoader = new ImageLoader(requestQueue, new LruBitmapCache());
        return imageLoader;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        return requestQueue;
    }


    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener paramConnectivityReceiverListener) {
        ConnectivityReceiver.connectivityReceiverListener = paramConnectivityReceiverListener;
    }

}