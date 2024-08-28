package com.example.sampleapplication.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sampleapplication.R;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import retrofit2.Call;
import retrofit2.http.GET;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

import java.util.ArrayList;
import java.util.List;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;

import android.graphics.Color;


public class PageIndexActivity extends AppCompatActivity {
    ListView listView;
    ArrayAdapter<String> adapter;
    List<String> historyList;
    LineChart lineChart;
    List<HistoryItem> historyItems;

    public class HistoryItem {
        private String trainDate;
        private int score;

        // Getters
        public String getTrainDate() {
            return trainDate;
        }

        public int getScore() {
            return score;
        }

        // Setters
        public void setTrainDate(String trainDate) {
            this.trainDate = trainDate;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }

    public class HistoryResponse {
        private List<HistoryItem> history;

        public List<HistoryItem> getHistory() {
            return history;
        }

        public void setHistory(List<HistoryItem> history) {
            this.history = history;
        }
    }

    public interface HistoryApiService {
        @GET("/history/getHistory")
        Call<HistoryResponse> getHistory(@Query("id") int userId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_index);
        historyList = new ArrayList<>();
        listView = findViewById(R.id.HistoryList);
        lineChart = findViewById(R.id.lineChart);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historyList);
        listView.setAdapter(adapter);

        fetchHistoryData(1);
        initEventHandlers();

    }

    private void initEventHandlers() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_index);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_train) {
                Intent intent = new Intent(PageIndexActivity.this, PageTrainActivity.class);
                startActivity(intent);
            } else if(item.getItemId() == R.id.menu_history) {
                Intent intent2 = new Intent(PageIndexActivity.this, PageHistoryActivity.class);
                intent2.putExtra("historyData", new Gson().toJson(historyItems));
                startActivity(intent2);
            }
            return true;
        });
    }

    private void updateLineChart(List<HistoryItem> historyItems) {
        List<Entry> entries = new ArrayList<>();
        List<String> dates = new ArrayList<>();

        for (int i = 0; i < historyItems.size(); i++) {
            HistoryItem item = historyItems.get(i);
            entries.add(new Entry((float) i, (float) item.getScore()));
            dates.add(item.getTrainDate());
        }

        LineDataSet dataSet = new LineDataSet(entries, "Score Over Time");
        dataSet.setColor(Color.parseColor("#ADD8E6"));
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setDrawFilled(true);
        dataSet.setFillColor(Color.parseColor("#ADD8E6"));

        LineData lineData = new LineData(dataSet);
        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));
        xAxis.setGranularity(1f);

        lineChart.getDescription().setEnabled(false);
        lineChart.invalidate();
    }


    private void fetchHistoryData(int userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://82.156.161.216:10166")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HistoryApiService service = retrofit.create(HistoryApiService.class);
        service.getHistory(userId).enqueue(new Callback<HistoryResponse>() {
            @Override
            public void onResponse(Call<HistoryResponse> call, Response<HistoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    historyItems = response.body().getHistory();
                    for (HistoryItem item : historyItems) {
                        String displayText = "日期: " + item.getTrainDate() + ", 分数: " + item.getScore();
                        historyList.add(displayText);
                        updateLineChart(historyItems);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    // 处理错误或空响应
                    Log.e("API Error", "Failed to fetch data or data is empty");
                }
            }

            @Override
            public void onFailure(Call<HistoryResponse> call, Throwable t) {
                Log.e("PageIndexActivity", "API call failed", t);
            }
        });
    }
}
