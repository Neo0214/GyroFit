package com.blue.gyrofit.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blue.gyrofit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PageTrainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_train);

        initEventHandlers();
    }

    private void initEventHandlers() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_train);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_index) {
                Intent intent = new Intent(PageTrainActivity.this, PageIndexActivity.class);
                startActivity(intent);
            } else if(item.getItemId() == R.id.menu_history) {
                Intent intent2 = new Intent(PageTrainActivity.this, PageHistoryActivity.class);
                startActivity(intent2);
            }
            return true;
        });
    }
}
