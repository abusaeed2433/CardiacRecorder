package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;

import com.example.cardiacrecorder.databinding.ActivityFrontPageBinding;

public class FrontPage extends AppCompatActivity {

    private ActivityFrontPageBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        binding = ActivityFrontPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler(Looper.getMainLooper()).postDelayed(() ->
                startActivity(new Intent(FrontPage.this,HomePage.class)),3000
        );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}