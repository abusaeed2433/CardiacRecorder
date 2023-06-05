package com.example.cardiacrecorder.classes;


public class DataModel {
    
    public int id;
    public long timestamp;
    public String date;
    public String time;
    public long epochDate;
    public int sysPressure;
    public int dysPressure;
    public int heartRate;
    public String comment;

    public DataModel() {}

    public DataModel(int id, long timestamp, String date, String time, long epochDate,
                     int sysPressure, int dysPressure, int heartRate, String comment) {
        this.id = id;
        this.timestamp = timestamp;
        this.date = date;
        this.time = time;
        this.epochDate = epochDate;
        this.sysPressure = sysPressure;
        this.dysPressure = dysPressure;
        this.heartRate = heartRate;
        this.comment = comment;
    }
}
