package com.example.cardiacrecorder;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.cardiacrecorder.classes.EachData;
import com.example.cardiacrecorder.databinding.ActivityDetailsBinding;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

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

        binding.tvDateTime.setText(getString(R.string.date_time,formatDate(data.getDate()),data.getTime()));
        binding.tvSysPressure.setText(data.getFormattedSysPressure());
        binding.tvDysPressure.setText(data.getFormattedDysPressure());
        binding.tvHeartRate.setText(data.getFormattedHeartRate());
        binding.tvComment.setText(data.getSafeComment());

        String text = data.getSysStatus();
        binding.tvIndicatorSystolic.setText(text);

        String text2 = data.getDysStatus();
        binding.tvIndicatorDiastolic.setText(text2);

        binding.tvIndicatorHeartRate.setText(data.getHeartRateStatus());

        if(data.isSysUnusual()){
            if(data.getSysPressure()<90){
                binding.viewSystolic.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_bg));
            }
            else{
                binding.viewSystolic.setBackgroundColor(ContextCompat.getColor(this, R.color.red_bg));
            }
        }

        if(data.isDysUnusual()){
            if(data.getDysPressure()<60){
                binding.viewDiastolic.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_bg));
            }
            else{
                binding.viewDiastolic.setBackgroundColor(ContextCompat.getColor(this, R.color.red_bg));
            }
        }

        if(data.isHeartRateUnusual()){
            if(data.getHeartRate() < 60){
                binding.viewHeartRate.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_bg));
            }
            else{
                binding.viewHeartRate.setBackgroundColor(ContextCompat.getColor(this, R.color.red_bg));
            }
        }
    }

    /**
     *
     * @param date to be formatted
     * @return date in 01Jul 2023 format
     */
    private String formatDate(String date){
        try {
            String pattern = "dd/MM/yyyy";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            TemporalAccessor ta = formatter.parse(date);

            String newPattern = "ddMMM yy";
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(newPattern);

            return formatter2.format(ta);
        }catch (Exception ignored){
            return date;
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
