package com.example.imtpmd;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MedicationNameDAO {
    @Query("SELECT * FROM medicationName")
    LiveData<List<MedicationName>> loadAll();

    @Query("SELECT name FROM MedicationName WHERE id = :givenID")
    LiveData<String> getMedicationName(int givenID);

    @Query("SELECT id FROM medicationName WHERE name = :firstName")
    LiveData<Integer> getFirstId(String firstName);

    @Insert
    void insertAll(MedicationName... medicationNames);

    @Query("DELETE FROM medicationName")
    void deleteAll();
}
