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

    /**
     * setter for all data
     * @param fromDate
     * @param toDate
     * @param sortBy
     * @param sysBy
     * @param dysBy
     * @param heartBy
     */
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

    /**
     * from date getter
     * @return
     */
    public String getFromDate() {
        return fromDate;
    }

    /**
     * to date getter
     * @return
     */
    public String getToDate() {
        return toDate;
    }

    /**
     * sorting getter
     * @return
     */
    public int getSortBy() {
        return sortBy;
    }

    /**
     * sys sort getter
     * @return
     */
    public int getSysBy() {
        return sysBy;
    }

    /**
     * dys sort getter
     * @return
     */
    public int getDysBy() {
        return dysBy;
    }

    /**
     * heart rate sort getter
     * @return
     */
    public int getHeartBy() {
        return heartBy;
    }


    /**
     * shower getter with value
     * @param showOrHide
     */
    public void setShowOrHide(boolean showOrHide){
        this.showOrHide.setValue(showOrHide);
    }

    /**
     * shower getter
     * @return
     */
    public MutableLiveData<Boolean> getShowOrHide() {
        return showOrHide;
    }

    /**
     * trigger getter
     * @return
     */
    public MutableLiveData<Boolean> getTrigger() {
        return trigger;
    }

}
