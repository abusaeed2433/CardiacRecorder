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
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MyTimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private final StringListener listener;

    /**
     * setter for time picker
     * @param listener
     */
    public MyTimePicker(StringListener listener){
        this.listener = listener;
    }

    /**
     * time picker dialog creator
     * @param savedInstanceState The last saved instance state of the Fragment,
     * or null if this is a freshly created Fragment.
     *
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        LocalTime localTime = LocalTime.now();

        int hour = localTime.getHour();
        int minute = localTime.getMinute();

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));

    }


    /**
     * time formatter and setter
     * @param timePicker the view associated with this listener
     * @param hour the hour that was set
     * @param min the minute that was set
     */
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {

        LocalTime localTime = LocalTime.of(hour,min);
        String pattern = "hh:mma";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.US);

        listener.onStringSelected(formatter.format(localTime));

    }

}
