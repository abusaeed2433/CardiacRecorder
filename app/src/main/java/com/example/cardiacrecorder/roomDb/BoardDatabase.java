package com.example.cardiacrecorder.roomDb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cardiacrecorder.classes.EachData;

@Database(entities = {EachData.class},version = 1)
public abstract class BoardDatabase extends RoomDatabase {

    private static BoardDatabase instance;
    public abstract BoardDao boardDao();

    public static BoardDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),BoardDatabase.class,"data_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
