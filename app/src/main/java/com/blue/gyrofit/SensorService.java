package com.blue.gyrofit;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class SensorService extends Service {
    private String TAG = "MySensor";
    private String mStrData;
    private boolean mSetServiceRunning = true;
    public SensorService() {
    }
    public void oncreate(){
        super.onCreate();
    }
    public void onStart(Intent intent, int startId)
    {
        Log.v(TAG,"onStart()");
    }

    ISensorInterface.Stub mStub = new ISensorInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void setStringData(String strData) throws RemoteException {
            Log.v(TAG,"1111receive:"+strData);
            mStrData = strData;
            // 广播更新事件
            Log.v("myblue","send broadcast");
            Intent intent = new Intent("com.blue.gyrofit.DATA_UPDATED");
            intent.putExtra("data",strData);
            sendBroadcast(intent);
        }

        @Override
        public String getStringData() throws RemoteException {
            return mStrData;
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