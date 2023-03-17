package com.ni___ckel.ipapp;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {IPaddress.class}, version = 1, exportSchema = false)
public abstract class ipDataBase extends RoomDatabase {

    private static ipDataBase instance = null;
    private static final String DB_NAME = "ip_information.db";

    public static ipDataBase getInstance(Application application){
        if (instance == null){
            instance = Room.databaseBuilder(application, ipDataBase.class, DB_NAME).build();
        }
        return instance;
    }

    public abstract NotesDao notesDao();


}
