package com.example.cardiacrecorder.classes;

import static com.example.cardiacrecorder.viewmodel.FilterViewModel.ALL;
import static com.example.cardiacrecorder.viewmodel.FilterViewModel.HIGH;
import static com.example.cardiacrecorder.viewmodel.FilterViewModel.LOW;
import static com.example.cardiacrecorder.viewmodel.FilterViewModel.NORMAL;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.cardiacrecorder.R;
import com.google.firebase.database.Exclude;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Entity(tableName = "data_table")
public class EachData implements Serializable {
//  date measured (presented in  format)
//• time measured (presented in hh:mm:a format)
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

    @Ignore
    private final long epochDate;
    private final int sysPressure; // mm Hg - non-negative
    private final int dysPressure; // mm Hg - non-negative
    private final int heartRate; // beats per minute non-negative
    @Nullable
    private final String comment;

    @Ignore
    @Exclude
    private transient SpannableString spanSys = null, spanDys = null, spanHeart = null, spanDateTime = null;

    /**
     * constructor
     * @param timestamp
     * @param date
     * @param time
     * @param sysPressure
     * @param dysPressure
     * @param heartRate
     * @param comment
     */
    public EachData(long timestamp, @NonNull String date, @NonNull String time, int sysPressure,
                    int dysPressure, int heartRate, @Nullable String comment) {
        this.timestamp = timestamp;
        this.date = date;
        this.time = time;
        this.sysPressure = sysPressure;
        this.dysPressure = dysPressure;
        this.heartRate = heartRate;
        this.comment = comment;
        this.epochDate = getEpochDate(date);
    }

    /**
     * constructor with id
     * @param id
     * @param timestamp
     * @param date
     * @param time
     * @param sysPressure
     * @param dysPressure
     * @param heartRate
     * @param comment
     */
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
        this.epochDate = getEpochDate(date);
    }

    /**
     * constructor with model
     * @param model
     */
    public EachData(DataModel model) {
        id = model.id;
        timestamp = model.timestamp;
        time = model.time;
        date = model.date;
        epochDate = model.epochDate;
        sysPressure = model.sysPressure;
        dysPressure = model.dysPressure;
        heartRate = model.heartRate;
        comment = model.comment;
    }

    /**
     * checker for sys, dys and heart rate
     * @param sysBy
     * @param dysBy
     * @param heartBy
     * @return
     */
    public boolean isThisOK(int sysBy, int dysBy, int heartBy){
        boolean sys = sysBy == ALL ||
                (sysBy == LOW && sysPressure < 90) ||
                (sysBy == HIGH && sysPressure > 140) ||
                (sysBy == NORMAL && !isSysUnusual());

        boolean dys = dysBy == ALL ||
                (dysBy == LOW && dysPressure < 60) ||
                (dysBy == HIGH && dysPressure > 90) ||
                (dysBy == NORMAL && !isDysUnusual());

        boolean heart = heartBy == ALL ||
                (heartBy == LOW && heartRate < 60) ||
                (heartBy == HIGH && heartRate > 100) ||
                (heartBy == NORMAL && (heartRate >= 60 && heartRate <= 100));

        return sys && dys && heart;
    }

    /**
     * getter for date
     * @return
     */
    public long getEpochDate() {
        return epochDate;
    }

    /**
     * formatter for date
     * @param date
     * @return
     */
    public static long getEpochDate(String date){
        try{
            String pattern = "dd/MM/yyyy";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, Locale.US);

            TemporalAccessor accessor = formatter.parse(date);

            LocalDate localDate = LocalDate.from(accessor);
            return localDate.toEpochDay();
        }
        catch (Exception ignored){
            return Long.MIN_VALUE;
        }
    }


    /**
     * setter for id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * getter for id
     * @return
     */
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
    public synchronized boolean isSysUnusual(){
        boolean ok = sysPressure<90 || sysPressure>140;
        return ok;
    }

    /**
     * returns string for pressure status
     * @return return "Sys Pressure: LOW" if Sys Pressure is < 90 && "Sys Pressure: HIGH" if Sys Pressure is > 140
     */
    public String getSysStatus(){
        if(!isSysUnusual()) {
            return "ok";
        }
        if(sysPressure < 90) return "low";
        if(sysPressure > 140) return "high";
        return "ok";
    }

    /**
     * heart rate status checker
     * @return
     */
    public String getHeartRateStatus(){
        if(!isHeartRateUnusual()) return "ok";
        if(heartRate < 60) return "low";
        if(heartRate > 100) return "high";
        return "ok";
    }

    /**
     * sys setter
     * @param spanSys
     */
    public void setSpanSys(SpannableString spanSys) {
        this.spanSys = spanSys;
    }

    /**
     * dys setter
     * @param spanDys
     */
    public void setSpanDys(SpannableString spanDys) {
        this.spanDys = spanDys;
    }

    /**
     * heart rate setter
     * @param spanHeart
     */
    public void setSpanHeart(SpannableString spanHeart) {
        this.spanHeart = spanHeart;
    }

    /**
     * time and date setter
     * @param spanDateTime
     */
    public void setSpanDateTime(SpannableString spanDateTime) {
        this.spanDateTime = spanDateTime;
    }
    /**
     * checks if Dys Pressure is usual or not
     * @return true if Dys pressure < 60 or > 90
     */
    public boolean isDysUnusual(){
        return dysPressure < 60 || dysPressure>90;
    }

    /**
     * checker for heart rate
     * @return
     */
    public boolean isHeartRateUnusual() {return heartRate < 60 || heartRate > 100; }

    /**
     * returns string for pressure status
     * @return return "Dys Pressure: LOW" if Dys Pressure is < 60 && "Dys Pressure: HIGH" if Dys Pressure is > 90
     */
    public String getDysStatus(){
        if(!isDysUnusual()) return "ok";
        if(dysPressure < 60) return "low";
        if(dysPressure > 90) return "high";
        return "ok";
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

    /**
     * date getter
     * @return
     */
    @NonNull
    public String getDate() {
        return date;
    }

    /**
     * time getter
     * @return
     */
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

    /**
     * sys getter
     * @return
     */
    public int getSysPressure() {
        return sysPressure;
    }

    /**
     *
     * @return formatted String in spannable format
     */
    public SpannableString getSpannableSys(){

        if(spanSys != null) return spanSys;

        String sys = String.valueOf(sysPressure);
        String s = sys+"\nmm Hg";

        spanSys = new SpannableString(s);

        spanSys.setSpan(new AbsoluteSizeSpan(18,true),0,sys.length(),0);
        spanSys.setSpan(new AbsoluteSizeSpan(14,true),sys.length(),s.length(),0);

        return spanSys;
    }


    /**
     *
     * @return background resource for textview based on value
     */
    public int getSysBackground(){
        if(sysPressure < 90) return R.drawable.round_back_down;
        if(sysPressure <= 140) return R.drawable.round_back_normal;
        return R.drawable.round_back_up;
    }

    /**
     *
     * @return background resource for textview based on value
     */
    public int getDysBackground(){
        if(dysPressure < 60) return R.drawable.round_back_down;
        if(dysPressure <= 90) return R.drawable.round_back_normal;
        return R.drawable.round_back_up;
    }

    /**
     *
     * @return background resource for textview based on value
     */
    public int getHeartBackground(){
        if(heartRate < 60) return R.drawable.round_back_down;
        if(heartRate <= 100) return R.drawable.round_back_normal;
        return R.drawable.round_back_up;
    }

    /**
     *
     * @return formatted String in spannable format
     */
    public SpannableString getSpannableDys(){

        if(spanDys != null) return spanDys;

        String dys = String.valueOf(dysPressure);
        String s = dys+"\nmm Hg";

        spanDys = new SpannableString(s);

        spanDys.setSpan(new AbsoluteSizeSpan(18,true),0,dys.length(),0);
        spanDys.setSpan(new AbsoluteSizeSpan(14,true),dys.length(),s.length(),0);

        return spanDys;
    }

    /**
     *
     * @return formatted String in spannable format
     */
    public SpannableString getSpannableHeart(){

        if(spanHeart != null) return spanHeart;

        String sys = String.valueOf(heartRate);
        String s = sys+"\nBPM";

        spanHeart = new SpannableString(s);


        spanHeart.setSpan(new AbsoluteSizeSpan(18,true),sys.length(),s.length(),0);
        spanHeart.setSpan(new AbsoluteSizeSpan(14,true),sys.length(),s.length(),0);
        return spanHeart;
    }

    /**
     * formats Dys Pressure
     * @return Dys pressure with unit
     */
    public String getFormattedDysPressure(){
        return dysPressure+"mm Hg";
    }

    /**
     *
     * @return date, time together, ex: 03/04/2023\n10:10PM
     */
    public SpannableString getSpannableDateTime(){
        if(spanDateTime != null) return spanDateTime;

        String tAgo = getElapsedTime(timestamp,System.currentTimeMillis());

        String s = date+"\n"+tAgo;

        spanDateTime = new SpannableString(s);

        spanDateTime.setSpan(new AbsoluteSizeSpan(14,true),0,date.length(),0);
        spanDateTime.setSpan(new AbsoluteSizeSpan(12,true),date.length(),s.length(),0);

        return spanDateTime;
    }

    /**
     * dys getter
     * @return
     */
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

    /**
     * heart rate getter
     * @return
     */
    public int getHeartRate() {
        return heartRate;
    }

    /**
     * comment getter
     * @return
     */
    @Nullable
    public String getComment() {
        return comment;
    }

    /**
     * comment formatter
     * @return
     */
    public String getSafeComment() {
        return comment==null || comment.trim().isEmpty() ? "No comment" : comment;
    }

    /**
     * time checker and formatter
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getElapsedTime(long startTime, long endTime) {
        long duration = endTime - startTime;
        long days = TimeUnit.MILLISECONDS.toDays(duration);
        duration -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        duration -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);

        StringBuilder sb = new StringBuilder();
        if (days > 0) {
            sb.append(days);
            sb.append("d");
            sb.append(" ");
        }
        if (hours > 0) {
            sb.append(hours);
            sb.append("h");
            sb.append(" ");
        }
        if (minutes >= 0 && days <= 0) {
            sb.append(minutes);
            sb.append("m");
            sb.append(" ");
        }

        return sb.append("ago").toString();
    }

    /**
     * model getter
     * @return
     */
    public DataModel getModel(){
        return new DataModel(id,timestamp,date,time,epochDate,sysPressure,dysPressure,heartRate,comment);
    }

}
