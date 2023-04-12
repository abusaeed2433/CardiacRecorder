package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cardiacrecorder.classes.EachData;
import com.example.cardiacrecorder.databinding.ActivityDetailsBinding;

public class DetailsPage extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();

        EachData eachData = (EachData) getIntent().getSerializableExtra("data");

        setData(eachData);

    }

    private void setData(EachData data){
        if(data == null) return;

        binding.DetailsDate.setText(data.getDate());
        binding.DetailsTime.setText(data.getTime());
        binding.DetailsSysPressure.setText(data.getFormattedSysPressure());
        binding.DetailsDysPressure.setText(data.getFormattedDysPressure());
        binding.DetailsHeartRate.setText(data.getFormattedHeartRate());
        binding.DetailsComment.setText(data.getComment());

        if(data.isSysUnusual()){
            binding.ivSysUnusual.setVisibility(View.VISIBLE);
            binding.tvIndicator1.setText(data.getSysStatus());
            binding.tvIndicator1.setVisibility(View.VISIBLE);
        }

        if(data.isDysUnusual()){
            binding.ivDysUnusual.setVisibility(View.VISIBLE);

            if(data.isSysUnusual()){
                binding.tvIndicator2.setText(data.getDysStatus());
                binding.tvIndicator2.setVisibility(View.VISIBLE);
            }
            else{
                binding.tvIndicator1.setText(data.getDysStatus());
                binding.tvIndicator1.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}