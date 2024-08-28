package com.example.sampleapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class DataReceiver extends BroadcastReceiver {
    private Context mContext;
    public DataReceiver(Context context) {
        mContext = context;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("data");
        Log.v("myblue","receriver receive:"+data);
    }
}
