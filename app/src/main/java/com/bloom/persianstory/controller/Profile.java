package com.bloom.persianstory.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bloom.persianstory.model.util.Buy;
import com.bloom.persianstory.R;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    Activity act;
    Button back;
    Button buy;
    TextView count;
    String countString;
    String imei;
    TextView lblcount;
    Button logout;
    TextView name;
    ProgressDialog pd;
    String pw;
    Typeface tf;
    String username;

    public void check(final String username, final String Imei, final String pass) {

        Volley.newRequestQueue(this).add(new StringRequest(Request.Method.POST, "http://persianstory.ir/JsonProfile/check", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("aa", response);
                String[] arrayOfString = response.split("--tg--");
                if (arrayOfString[0].contains("1")) {
                    Toast.makeText(getApplicationContext(), "حساب شما توسط مدیر مسدود شده است", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Profile.this, Home.class));
                    overridePendingTransition(R.anim.slide2, R.anim.slide2_out);
                    finish();
                    return;
                }
                if (arrayOfString[0].contains("6")) {
                    startActivity(new Intent(Profile.this, Home.class));
                    overridePendingTransition(R.anim.slide2, R.anim.slide2_out);
                    finish();
                    return;
                }
                SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(act).edit();
                localEditor.putString("p", arrayOfString[1].toString());
                localEditor.apply();
                count.setText(arrayOfString[1].toString());
                pd.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("asdasdasd", "asdasdasd");
            }
        }){

            @Override
            protected Map<String, String> getParams()
            {
                HashMap localHashMap = new HashMap();
                localHashMap.put("u", username);
                localHashMap.put("h", pass);
                localHashMap.put("a", "tg_"+Imei);
                return localHashMap;
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide1, R.anim.slide2_out);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.profile);
        init();
        checkGuest();

    }

    private void checkGuest() {
        if (username.equals("مهمان")) {
            buy.setText("ثبت نام");
            logout.setText("ورود به حساب");
            count.setVisibility(View.GONE);
            lblcount.setText("برای افزایش سکه و گوش کردن قصه های غیر رایگان باید ثبت نام کنید");
            
        } else {
            pd = new ProgressDialog(this);
            pd.setMessage("منتظر باشید...");
            pd.setCancelable(false);
            pd.show();
            check(username, imei, pw);
            name.setText(username);
            count.setText(countString);
        }
    }

    private void init() {

        act = this;
        SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        imei = localSharedPreferences.getString("u", "");
        username = localSharedPreferences.getString("us", "");
        pw = localSharedPreferences.getString("h", "");
        countString = localSharedPreferences.getString("p", "");
        tf = Typeface.createFromAsset(getAssets(), "iransans.ttf");
        buy = ((Button) findViewById(R.id.buy));
        back = ((Button) findViewById(R.id.back));
        logout = ((Button) findViewById(R.id.logout));
        count = ((TextView) findViewById(R.id.count));
        name = ((TextView) findViewById(R.id.name));
        lblcount = ((TextView) findViewById(R.id.lblcount));
        back.setTypeface(tf);
        buy.setTypeface(tf);
        logout.setTypeface(tf);
        count.setTypeface(tf);
        name.setTypeface(tf);
        lblcount.setTypeface(tf);
        try {
            pw = new String(Base64.decode(pw, 0), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ((username.equals("")) || (username == null) || (username.isEmpty())) {
            startActivity(new Intent(this, Home.class));
            finish();
        }
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                finish();
                overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                if (username.equals("مهمان")) {
                    startActivity(new Intent(Profile.this, Register.class));
                    overridePendingTransition(R.anim.slide2, R.anim.slide2_out);
                    return;
                }
                startActivity(new Intent(Profile.this, Buy.class));
                overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                if (username.equals("مهمان")) {
                    startActivity(new Intent(Profile.this, Login.class));
                    overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
                    return;
                }
                SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(act).edit();
                localEditor.putString("us", "");
                localEditor.putString("p", "0");
                localEditor.apply();
                startActivity(new Intent(Profile.this, Home.class));
                overridePendingTransition(R.anim.slide1, R.anim.slide1_out);
                finish();
            }
        });
    }
}

