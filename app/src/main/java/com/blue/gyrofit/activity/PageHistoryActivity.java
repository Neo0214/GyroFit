package com.blue.gyrofit.activity;

import android.content.Intent;
import android.os.Bundle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blue.gyrofit.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import android.util.Log;

public class PageHistoryActivity extends AppCompatActivity {
    private ImageView avatarImageView;
    private TextView userInfoTextView;

    public interface getAvatar {
        @GET("user/getAvatar")
        Call<ResponseBody> getImage(@Query("userId") String userId);
    }

    public class UserInfo {
        private int id;
        private String name;

        // Getter 和 Setter 方法
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public interface UserInfoService {
        @GET("user/getInfo")
        Call<UserInfo> getUserInfo(@Query("userId") String userId);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_history);
        avatarImageView = findViewById(R.id.avatarImageView);
        userInfoTextView = findViewById(R.id.userInfoTextView);
        initEventHandlers();
        fetchUserInfo("1");
        fetchAvatar("1");
    }

    private void initEventHandlers() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_history);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_train) {
                Intent intent = new Intent(PageHistoryActivity.this, PageTrainActivity.class);
                startActivity(intent);
            } else if(item.getItemId() == R.id.menu_index) {
                Intent intent2 = new Intent(PageHistoryActivity.this, PageIndexActivity.class);
                startActivity(intent2);
            }
            return true;
        });
    }

    private void fetchAvatar(String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://82.156.161.216:10166/") // 基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getAvatar avatarService = retrofit.create(getAvatar.class);

        Call<ResponseBody> call = avatarService.getImage(userId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try (ResponseBody responseBody = response.body()) {
                        InputStream inputStream = responseBody.byteStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        avatarImageView.setImageBitmap(bitmap);
                    } catch (Exception e) {
                        Log.e("PageHistoryActivity", "Error processing the image response", e);
                        avatarImageView.setImageResource(R.drawable.default_avatar);
                    }
                } else {
                    // 设置默认头像
                    avatarImageView.setImageResource(R.drawable.default_avatar);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("PageHistoryActivity", "API call failed", t);
                avatarImageView.setImageResource(R.drawable.default_avatar);
            }
        });
    }

    private void fetchUserInfo(String userId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://82.156.161.216:10166/") // 基础URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserInfoService userInfoService = retrofit.create(UserInfoService.class);
        Call<UserInfo> call = userInfoService.getUserInfo(userId);

        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(@NonNull Call<UserInfo> call, @NonNull Response<UserInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserInfo userInfo = response.body();
                    String userInfoText = "ID： " + userInfo.getId() + "\n用户名： " + userInfo.getName();
                    userInfoTextView.setText(userInfoText);
                } else {
                    Log.e("PageHistoryActivity", "Failed to get user info");
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserInfo> call, @NonNull Throwable t) {
                Log.e("PageHistoryActivity", "API call failed", t);
            }
        });
    }
}
