package com.blue.gyrofit.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.blue.gyrofit.R;

public class PageIndex extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_index);

        initEventHandlers();
    }

    private void initEventHandlers(){
        // pass
    }


}
