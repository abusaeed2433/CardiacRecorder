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

    /**
     * Read all data form Room database
     * @return All data objects
     */
    public LiveData<List<EachData>> getAllData() {
        return allData;
    }

    /**
     * Insert data into Room database
     * @param data object to be inserted
     */
    public void insert(EachData data){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> boardDao.insert(data));
    }

    /**
     * Update data into Room database
     * @param data object to be updated
     */
    public void update(EachData data){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> boardDao.update(data));
    }

    /**
     * Delete data from Room database
     * @param data object to be deleted
     */
    public void delete(EachData data){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> boardDao.delete(data));
    }

    public void deleteAll(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(boardDao::deleteAll);
    }

}
