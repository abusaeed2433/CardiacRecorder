package com.example.cardiacrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cardiacrecorder.classes.EachData;
import com.example.cardiacrecorder.classes.MyDatePicker;
import com.example.cardiacrecorder.classes.MyTimePicker;
import com.example.cardiacrecorder.databinding.ActivityAdderBinding;
import com.example.cardiacrecorder.roomDb.BoardViewModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public class AdderActivity extends AppCompatActivity {

    private ActivityAdderBinding binding = null;
    private BoardViewModel viewModel = null;

    private MyDatePicker datePicker;
    private MyTimePicker timePicker;
    private EachData passedData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        startRoom();
        setClickListener();

        Intent intent = getIntent();
        Object obj = intent.getSerializableExtra("data");
        if(obj instanceof EachData){
            passedData = (EachData)obj;
            setPassedData(passedData);
        }

    }

    /**
     * will show passed data to editText
     * @param data to be shown editText
     */
    private void setPassedData(@NonNull EachData data){
        binding.editTextDt.setText(data.getDate());
        binding.editTextTime.setText(data.getTime());
        binding.editTextSysPressure.setText(getString(R.string.int_ph,data.getSysPressure()));
        binding.editTextDysPressure.setText(getString(R.string.int_ph,data.getDysPressure()));
        binding.editTextHeartRate.setText(getString(R.string.int_ph,data.getHeartRate()));
        binding.editTextComment.setText(data.getComment());

        binding.buttonSave.setText(getString(R.string.update));

    }


    /**
     * initializes view model
     */
    private void startRoom(){
        viewModel = new ViewModelProvider(this).get(BoardViewModel.class);
    }

    /**
     * set click listener to all views and initializes data and time picker
     */
    private void setClickListener(){

        datePicker = new MyDatePicker(value ->{
            if(isDateValid(value)) {
                binding.editTextDt.setText(value);
            }
            else{
                Toast.makeText(this, "Invalid date", Toast.LENGTH_SHORT).show();
            }
        });

        timePicker = new MyTimePicker(time ->{
            String date = String.valueOf(binding.editTextDt.getText()).trim();
            if(date.isEmpty()){
                Toast.makeText(this, "Select a date first", Toast.LENGTH_SHORT).show();
                return;
            }

            if(isTimeValid(date,time)) {
                binding.editTextTime.setText(time);
            }
            else{
                Toast.makeText(this, "Invalid time", Toast.LENGTH_SHORT).show();
            }
        });

        binding.editTextDt.setOnClickListener(view -> datePicker.show(getSupportFragmentManager(),"Date picker"));
        binding.editTextTime.setOnClickListener(view -> timePicker.show(getSupportFragmentManager(),"Time picker"));

        binding.buttonSave.setOnClickListener(view -> {
            String date = String.valueOf(binding.editTextDt.getText()).trim();
            String time = String.valueOf(binding.editTextTime.getText()).trim();
            String sys = String.valueOf(binding.editTextSysPressure.getText()).trim();
            String dys = String.valueOf(binding.editTextDysPressure.getText()).trim();
            String hRate = String.valueOf(binding.editTextHeartRate.getText()).trim();
            String comment = String.valueOf(binding.editTextComment.getText()).trim();

            if(!isValid(date,time,sys,dys,hRate)){
                Toast.makeText(this, "Fill all forms", Toast.LENGTH_SHORT).show();
                return;
            }

            int sysP = 0, dysP = 0, rate = 0;

            try{
                sysP = Integer.parseInt(sys);
                dysP = Integer.parseInt(dys);
                rate = Integer.parseInt(hRate);
            }catch (Exception ignored){}



            if(passedData == null){
                EachData data = new EachData(System.currentTimeMillis(),date,time,sysP,dysP,rate,comment);
                viewModel.insert(data);
                Toast.makeText(this, "Successfully inserted", Toast.LENGTH_SHORT).show();
            }
            else{
                EachData data = new EachData(passedData.getId(), System.currentTimeMillis(),date,time,sysP,dysP,rate,comment);
                viewModel.update(data);
                Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show();
                passedData = null;
            }

            clearAll();

        });

    }


    /**
     * check if date is valid
     * @param date to be checked. Must be in dd/MM/yyyy format
     * @return true if date is today or earlier
     */
    private boolean isDateValid(String date){
        try{
            LocalDate today = LocalDate.now();

            String pattern = "dd/MM/yyyy";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern,Locale.US);
            TemporalAccessor ta = formatter.parse(date);

            LocalDate conDate = LocalDate.from(ta);

            return !conDate.isAfter(today);

        }catch (Exception e){
            e.printStackTrace();
            return true;
        }

    }

    /**
     * check if time is before current time
     * @param time to be checked. Must be in hh:mm:a
     * @return true if before current time else false
     */
    private boolean isTimeValid(String date, String time){
        try{
            LocalDateTime cur = LocalDateTime.now();

            String comb = date+" "+time;
            String pattern = "dd/MM/yyyy hh:mma";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern,Locale.US);

            TemporalAccessor ta = formatter.parse(comb);

            LocalDateTime ldt = LocalDateTime.from(ta);

            return ldt.isBefore(cur);

        }catch (Exception e){
            e.printStackTrace();
            return true;
        }
    }


    /**
     * Clears necessary editTexts
     */
    private void clearAll(){
        binding.editTextTime.setText(null);
        binding.editTextSysPressure.setText(null);
        binding.editTextDysPressure.setText(null);
        binding.editTextHeartRate.setText(null);
        binding.editTextComment.setText(null);
    }


    /**
     *  Check editText box is empty or not
     * @param values String array of editText values
     * @return true if all editTexts are not empty
     */
    private boolean isValid(String ...values){
        for(String val : values){
            if(val.isEmpty()) return false;
        }
        return true;
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