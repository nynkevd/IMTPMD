package com.example.imtpmd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MedicationNameDAO {
    @Query("SELECT * FROM medicationName")
    List<MedicationName> loadAll();

    @Insert
    void insertAll(MedicationName... medicationNames);

    @Query("DELETE FROM medicationName")
    void deleteAll();
}
