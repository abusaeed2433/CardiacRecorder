package com.example.cardiacrecorder.classes;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

public class EachData {
//    date measured (presented in  format)
//• time measured (presented in hh:mm format)
//• systolic pressure in mm Hg (non-negative integer)
//• diastolic pressure in mm Hg (non-negative integer)
//• heart rate in beats per minute (non-negative integer)
//• comment (textual, up to 20 characters)

    private final long timestamp;

    @NonNull
    private final String date; //dd-mm-yyyy

    @NonNull
    private final String time; // hh:mma

    private final int sysPressure; // mm Hg - non-negative
    private final int dysPressure; // mm Hg - non-negative
    private final int heartRate; // beats per minute non-negative

    @Nullable
    private final String comment;

    public EachData(long timestamp, @NonNull String date, @NonNull String time, int sysPressure,
                    int dysPressure, int heartRate, @Nullable String comment) {
        this.timestamp = timestamp;
        this.date = date;
        this.time = time;
        this.sysPressure = sysPressure;
        this.dysPressure = dysPressure;
        this.heartRate = heartRate;
        this.comment = comment;
    }

    public boolean isSysUnusual(){
        return sysPressure<90 || sysPressure>140;
    }

    public boolean isDysUnusual(){
        return dysPressure < 60 || dysPressure>90;
    }

    public boolean isIdSame(EachData item){
        return timestamp == item.timestamp;
    }

    public boolean isFullySame(EachData item){
        boolean commentCheck = (comment == null && item.comment == null);
        if(comment != null){
            commentCheck = comment.equals(item.comment);
        }

        return (timestamp == item.timestamp) && (date.equals(item.date)) && (time.equals(item.time)) &&
                (sysPressure == item.sysPressure) && (dysPressure == item.dysPressure) && (heartRate == item.heartRate) && commentCheck;

    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public String getFormattedSysPressure(){
        return sysPressure+"";
    }
    public int getSysPressure() {
        return sysPressure;
    }

    public String getFormattedDysPressure(){
        return dysPressure+"";
    }

    public int getDysPressure() {
        return dysPressure;
    }

    public String getFormattedHeartRate(){
        return heartRate+"";
    }

    public int getHeartRate() {
        return heartRate;
    }

    @Nullable
    public String getComment() {
        return comment;
    }

}
