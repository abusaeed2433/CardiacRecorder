package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.cardiacrecorder.classes.EachData;
import com.example.cardiacrecorder.classes.MyDatePicker;
import com.example.cardiacrecorder.classes.MyTimePicker;
import com.example.cardiacrecorder.databinding.ActivityAdderBinding;
import com.example.cardiacrecorder.others.StringListener;
import com.example.cardiacrecorder.roomDb.BoardViewModel;

public class AdderActivity extends AppCompatActivity {

    private ActivityAdderBinding binding = null;
    private BoardViewModel viewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        startRoom();
        setClickListener();

    }

    private void startRoom(){
        viewModel = new ViewModelProvider(this).get(BoardViewModel.class);
    }

    private void setClickListener(){


        MyDatePicker datePicker = new MyDatePicker(value -> binding.editTextDt.setText(value));
        MyTimePicker timePicker = new MyTimePicker(value -> binding.editTextTime.setText(value));

        binding.editTextDt.setOnClickListener(view -> {
            datePicker.show(getSupportFragmentManager(),"Date picker");
        });

        binding.editTextTime.setOnClickListener(view -> {
            timePicker.show(getSupportFragmentManager(),"Time picker");
        });


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

            EachData data = new EachData(System.currentTimeMillis(),date,time,sysP,dysP,rate,comment);
            viewModel.insert(data);

            Toast.makeText(this, "Successfully inserted", Toast.LENGTH_SHORT).show();
            clearAll();

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    private void clearAll(){
        binding.editTextTime.setText(null);
        binding.editTextSysPressure.setText(null);
        binding.editTextDysPressure.setText(null);
        binding.editTextHeartRate.setText(null);
        binding.editTextComment.setText(null);
    }


    private boolean isValid(String ...values){
        for(String val : values){
            if(val.isEmpty()) return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}