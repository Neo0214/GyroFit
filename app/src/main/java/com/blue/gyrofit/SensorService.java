package com.blue.gyrofit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;



public class SensorService extends Service {
    private String TAG = "SensorService";
    private String mStrData;
    private boolean mSetSergviceRunning = true;
    public SensorService() {
    }
    public void oncreate(){
        super.onCreate();
        Log.v(TAG,"onCreate()");
    }
    @Override
    public void onStart(Intent intent, int startId){
        Log.v(TAG,"onStart()");
    }

    ISensorInterface.Stub mStub = new ISensorInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void setStringData(String strData) throws RemoteException {
            Log.v(TAG,"receive:"+strData);
            mStrData = strData;
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG,"onBind()");
        return mStub;
    }
    public boolean onUnbind(Intent intent){
        Log.v(TAG,"onUnbind()");
        return true;
    }
}