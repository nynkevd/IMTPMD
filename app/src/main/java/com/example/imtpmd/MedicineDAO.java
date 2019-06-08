package com.example.imtpmd;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MedicineDAO {
    @Query("SELECT * FROM medicine")
    List<Medicine> loadAll();

    @Query("SELECT * FROM medicine WHERE time = :time ")
    List<Medicine> loadByTime(String time);

    @Insert
    void insertAll(Medicine... medicines);

    @Delete
    void delete (Medicine medicine);
}
