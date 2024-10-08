package com.example.sampleapplication.activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleapplication.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initEventHandlers();
    }

    private void initEventHandlers(){
        Button jumpBtn = findViewById(R.id.jumpBtn);
        jumpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PageIndexActivity.class);
                startActivity(intent);
            }
        });
    }

}
