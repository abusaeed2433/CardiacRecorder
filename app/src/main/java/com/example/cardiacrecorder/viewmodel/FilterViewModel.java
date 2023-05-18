package com.example.cardiacrecorder.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class FilterViewModel extends ViewModel {
    public static final int LOW = 0, NORMAL = 1, HIGH = 2, ALL = 3;
    public static final int DATE = 0, SYS = 1, DYS = 2, HEART = 3, ID = 4;

    private String fromDate = null, toDate = null;
    private int sortBy = ID, sysBy = ALL, dysBy = ALL, heartBy = ALL;

    private final MutableLiveData<Boolean> showOrHide = new MutableLiveData<>();
    private final MutableLiveData<Boolean> trigger = new MutableLiveData<>(false);
    private boolean localTrigger = false;

    public void setAll(String fromDate, String toDate, int sortBy, int sysBy, int dysBy, int heartBy){
        this.fromDate = fromDate;
        this.toDate = toDate;

        this.sortBy = sortBy == -1 ? ID : sortBy;
        this.sysBy = sysBy == -1 ? ALL : sysBy;
        this.dysBy = dysBy == -1 ? ALL : dysBy;
        this.heartBy = heartBy == -1 ? ALL : heartBy;

        localTrigger = !localTrigger;
        trigger.setValue(localTrigger);
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public int getSortBy() {
        return sortBy;
    }

    public int getSysBy() {
        return sysBy;
    }

    public int getDysBy() {
        return dysBy;
    }

    public int getHeartBy() {
        return heartBy;
    }


    public void setShowOrHide(boolean showOrHide){
        this.showOrHide.setValue(showOrHide);
    }

    public MutableLiveData<Boolean> getShowOrHide() {
        return showOrHide;
    }

    public MutableLiveData<Boolean> getTrigger() {
        return trigger;
    }

}
