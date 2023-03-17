package com.ni___ckel.ipapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface NotesDao {

    @Query("SELECT * FROM ip_information")
    LiveData<List<IPaddress>> getIPaddress();

    @Query("SELECT * FROM ip_information WHERE id =:id")
    IPaddress getOnlyIPaddress(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable add(IPaddress ipaddress);

    @Query("DELETE FROM ip_information WHERE id =:id")
    Completable remove(int id);


}
