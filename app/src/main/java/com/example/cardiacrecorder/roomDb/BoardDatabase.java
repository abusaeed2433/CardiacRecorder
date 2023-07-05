package com.example.cardiacrecorder.roomDb;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cardiacrecorder.classes.EachData;

@Database(entities = {EachData.class},version = 5)
public abstract class BoardDatabase extends RoomDatabase {

    private static BoardDatabase instance;

    /**
     * constructor
     * @return
     */
    public abstract BoardDao boardDao();

    /**
     * create instance of board database
     * @param context object
     * @return Single-tone instance of board database class
     */
    public static BoardDatabase getInstance(Context context){

        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),BoardDatabase.class,"data_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
