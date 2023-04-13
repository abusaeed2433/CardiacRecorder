package com.example.cardiacrecorder.roomDb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cardiacrecorder.classes.EachData;

import java.util.List;

@Dao
public interface BoardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(EachData data);

    @Update
    void update(EachData data);

    @Delete
    void delete(EachData data);

    @Query("SELECT * FROM data_table ORDER BY id ASC")
    LiveData<List<EachData>> getAllData();

}
