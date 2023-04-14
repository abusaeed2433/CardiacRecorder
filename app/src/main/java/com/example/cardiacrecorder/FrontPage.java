package com.example.cardiacrecorder;

import androidx.appcompat.app.ActionBar;
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
        super.onCreate(savedInstanceState);
        binding = ActivityFrontPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            getSupportActionBar().hide();
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            startActivity(new Intent(FrontPage.this, HomePage.class));
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        },1200);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
