package com.example.cardiacrecorder.roomDb;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cardiacrecorder.classes.EachData;

import java.util.List;

public class BoardViewModel extends AndroidViewModel {

    private final BoardRepository repository;
    private final LiveData<List<EachData>> allData;

    public BoardViewModel(@NonNull Application application) {
        super(application);
        repository = new BoardRepository(application);
        allData = repository.getAllData();
    }

    public void insert(EachData data){
        repository.insert(data);
    }

    public void update(EachData data){
        repository.update(data);
    }

    public void delete(EachData data){
        repository.delete(data);
    }

    public LiveData<List<EachData>> getAllData() {
        return allData;
    }

}
