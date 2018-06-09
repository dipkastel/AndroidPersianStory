package com.bloom.persianstory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bloom.persianstory.model.Network.ConnectivityReceiver;

/**
 * Created by amir on 5/31/2018.
 */

public abstract class BaseActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener  {

    ConnectivityReceiver connectivityReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        connectivityReceiver = new ConnectivityReceiver();
    }
      public void  setOnConnectionChange(ConnectivityReceiver.ConnectivityReceiverListener Listener){

            connectivityReceiver.setConnectivityReceiverListener(Listener);
        }
    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    protected void onStop() {
        super.onStop();
        System.gc();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectivityReceiver.setConnectivityReceiverListener(null);
    }


    @Override
    public void onBackPressed() {
    }

}
