package com.example.cardiacrecorder.others;

import com.example.cardiacrecorder.classes.EachData;

public interface AdapterListener {
    void onDeleteRequest(EachData data);
    void onEditRequest(EachData data);
    void onShowRequest(EachData data);
}
