package com.example.cardiacrecorder.roomDb;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cardiacrecorder.classes.EachData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BoardRepository {//Repository Link Notice

    private final BoardDao boardDao;
    private final LiveData<List<EachData>> allData;

    public BoardRepository(Application application){//application is a subclass of context
        BoardDatabase boardDatabase = BoardDatabase.getInstance(application);
        boardDao = boardDatabase.boardDao();
        allData = boardDao.getAllData();
    }

    public LiveData<List<EachData>> getAllData() {
        return allData;
    }

    public void insert(EachData data){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> boardDao.insert(data));
    }

    public void update(EachData data){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> boardDao.update(data));
    }

    public void delete(EachData data){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> boardDao.delete(data));
    }



}
