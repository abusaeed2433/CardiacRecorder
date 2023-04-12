package com.example.cardiacrecorder.classes;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.example.cardiacrecorder.others.StringListener;

import java.time.LocalTime;

public class MyTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private final StringListener listener;

    public MyTimePicker(StringListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        LocalTime localTime = LocalTime.now();

        int hour = localTime.getHour();
        int minute = localTime.getMinute();

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));

    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {



        StringBuilder builder = new StringBuilder();

        String apm = "AM";
        int h = hour;
        if(hour >= 12){
            apm = "PM";
        }

        if(hour == 0) h = 12;
        else if(hour > 12) h = hour-12;

        if(h < 10) builder.append("0");
        builder.append(h).append(":");

        if(min < 10) builder.append("0");
        builder.append(min);

        builder.append(apm);

        listener.onStringSelected(builder.toString());

    }



}
