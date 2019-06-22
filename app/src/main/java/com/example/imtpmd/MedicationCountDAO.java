package com.example.imtpmd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface MedicationCountDAO {
    @Query("SELECT count FROM MedicationCount")
    int getCount();

    @Insert
    void insertAll(MedicationCount... medicationCounts);

    @Query("DELETE FROM medicationCount")
    void deleteAll();
}
