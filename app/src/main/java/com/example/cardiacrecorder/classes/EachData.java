package com.example.cardiacrecorder.classes;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@Entity(tableName = "data_table")
public class EachData implements Serializable {
//  date measured (presented in  format)
//• time measured (presented in hh:mm format)
//• systolic pressure in mm Hg (non-negative integer)
//• diastolic pressure in mm Hg (non-negative integer)
//• heart rate in beats per minute (non-negative integer)
//• comment (textual, up to 20 characters)

    @PrimaryKey(autoGenerate = true)
    private int id;

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

    @Ignore
    public EachData(int id,long timestamp, @NonNull String date, @NonNull String time, int sysPressure,
                    int dysPressure, int heartRate, @Nullable String comment) {
        this.id = id;
        this.timestamp = timestamp;
        this.date = date;
        this.time = time;
        this.sysPressure = sysPressure;
        this.dysPressure = dysPressure;
        this.heartRate = heartRate;
        this.comment = comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * primary key timestamp generator
     * @return timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * checks if Sys Pressure is usual or not
     * @return true if Sys pressure < 90 or >140
     */
    public boolean isSysUnusual(){
        return sysPressure<90 || sysPressure>140;
    }

    /**
     * returns string for pressure status
     * @return return "Sys Pressure: LOW" if Sys Pressure is < 90 && "Sys Pressure: HIGH" if Sys Pressure is > 140
     */
    public String getSysStatus(){
        if(!isSysUnusual()) return null;
        if(sysPressure < 90) return "Sys pressure: LOW";
        if(sysPressure > 140) return "Sys pressure: HIGH";
        return null;
    }

    /**
     * checks if Dys Pressure is usual or not
     * @return true if Dys pressure < 60 or > 90
     */
    public boolean isDysUnusual(){
        return dysPressure < 60 || dysPressure>90;
    }

    /**
     * returns string for pressure status
     * @return return "Dys Pressure: LOW" if Dys Pressure is < 60 && "Dys Pressure: HIGH" if Dys Pressure is > 90
     */
    public String getDysStatus(){
        if(!isDysUnusual()) return null;
        if(dysPressure < 60) return "Dys pressure: LOW";
        if(dysPressure > 90) return "Dys pressure: HIGH";
        return null;
    }

    /**
     * picks color based on unusual Sys Pressure
     * @return BLUE if "Sys Pressure < 90" && RED if "Sys Pressure > 140"
     */
    public int getSysColor(){
        if(sysPressure < 90) return Color.BLUE;
        if(sysPressure > 140) return Color.RED;
        return Color.BLACK;
    }

    /**
     * picks color based on unusual Dys Pressure
     * @return BLUE if "Dys Pressure < 60" && RED if "Dys Pressure > 90"
     */
    public int getDysColor(){
        if(dysPressure < 60) return Color.BLUE;
        if(dysPressure > 90) return Color.RED;
        return Color.BLACK;
    }

    /**
     * check if two item ids are same
     * @param item object of new item
     * @return true if same id
     */
    public boolean isIdSame(EachData item){
        return timestamp == item.timestamp;
    }

    /**
     * checks if two objects are totally same
     * @param item object of new item
     * @return true if objects are totally same
     */
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

    /**
     * formats Sys Pressure
     * @return Sys pressure with unit
     */
    public String getFormattedSysPressure(){
        return sysPressure+"mm Hg";
    }
    public int getSysPressure() {
        return sysPressure;
    }

    /**
     * formats Dys Pressure
     * @return Dys pressure with unit
     */
    public String getFormattedDysPressure(){
        return dysPressure+"mm Hg";
    }

    public int getDysPressure() {
        return dysPressure;
    }

    /**
     * formats Heart Rate
     * @return Heart Rate with unit
     */
    public String getFormattedHeartRate(){
        return heartRate+"BPM";
    }

    public int getHeartRate() {
        return heartRate;
    }

    @Nullable
    public String getComment() {
        return comment;
    }

}
