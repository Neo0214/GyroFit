package com.example.sampleapplication.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleapplication.ISensorInterface;
import com.example.sampleapplication.R;
import com.example.sampleapplication.SensorService;
import com.example.sampleapplication.utils.ModelLoader;
import com.example.sampleapplication.utils.SqautDataHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.tensorflow.lite.Interpreter;

public class PageTrainActivity extends AppCompatActivity {
    // members
//    private ISensorInterface earSensor;
//    private ServiceConnection earConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName className, IBinder service) {
//            earSensor = ISensorInterface.Stub.asInterface(service);
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            earSensor = null;
//        }
//    };

    private Interpreter squatsModel;
    private SqautDataHandler sqautDataHandler;

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v("myblue", "Data received");
            if ("com.example.DATA_UPDATED".equals(intent.getAction())) {
                String updatedData = intent.getStringExtra("data");
                Log.v("myblue", "Data received: " + updatedData);
                sqautDataHandler.AddData(updatedData);
            }
        }
    };

    // methods
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_train);
        initEventHandlers();
        // 绑定数据
        //Intent intent = new Intent(this, SensorService.class);
        //bindService(intent, earConnection, BIND_AUTO_CREATE);

        // 加载模型
        ModelLoader md = new ModelLoader("sq.tflite", this);
        squatsModel = md.loadModel();
        // 加载其他处理器
        sqautDataHandler = new SqautDataHandler();
    }

    private void initEventHandlers() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_train);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_index) {
                Intent intent = new Intent(PageTrainActivity.this, PageIndexActivity.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.menu_history) {
                Intent intent2 = new Intent(PageTrainActivity.this, PageHistoryActivity.class);
                startActivity(intent2);
            }
            return true;
        });

        Button startBtn = findViewById(R.id.button);
        startBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startAll();
                                        }
                                    }

        );

    }

    private void startAll(){
        // 检测各条件是否可以开始
        if (squatsModel == null){
            Toast.makeText(this, "模型未加载", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (earSensor == null){
//            Toast.makeText(this, "传感器未连接", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (sqautDataHandler == null){
            Toast.makeText(this, "数据处理器未初始化", Toast.LENGTH_SHORT).show();
            return;
        }
        // 播放视频
        VideoView videoView = findViewById(R.id.videoView4);
        videoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.test));
        videoView.start();
    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unbindService(earConnection);
//    }


}
