package com.example.cardiacrecorder;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cardiacrecorder.classes.EachData;
import com.example.cardiacrecorder.databinding.ActivityDetailsBinding;

public class DetailsPage extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        EachData eachData = (EachData) getIntent().getSerializableExtra("data");

        setData(eachData);

    }

    /**
     * sets data to textViews
     * @param data class object passed by user
     */
    private void setData(EachData data){
        if(data == null) return;

        binding.DetailsDate.setText(data.getDate());
        binding.DetailsTime.setText(data.getTime());
        binding.DetailsSysPressure.setText(data.getFormattedSysPressure());
        binding.DetailsDysPressure.setText(data.getFormattedDysPressure());
        binding.DetailsHeartRate.setText(data.getFormattedHeartRate());
        binding.DetailsComment.setText(data.getSafeComment());

        String text = data.getSysStatus();
        binding.tvIndicatorSys.setText(text);

        String text2 = data.getDysStatus();
        binding.tvIndicatorDys.setText(text2);

        binding.tvIndicatorHeart.setText(data.getHeartRateStatus());

        if(data.isSysUnusual()){
            if(data.getSysPressure()<90){
                binding.layoutSys.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_bg));
            }
            else{
                binding.layoutSys.setBackgroundColor(ContextCompat.getColor(this, R.color.red_bg));
            }
        }

        if(data.isDysUnusual()){
            if(data.getDysPressure()<60){
                binding.layoutDys.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_bg));
            }
            else{
                binding.layoutDys.setBackgroundColor(ContextCompat.getColor(this, R.color.red_bg));
            }
        }

        if(data.isHeartRateUnusual()){
            if(data.getHeartRate() < 60){
                binding.layoutHeartRate.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_bg));
            }
            else{
                binding.layoutHeartRate.setBackgroundColor(ContextCompat.getColor(this, R.color.red_bg));
            }
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}
