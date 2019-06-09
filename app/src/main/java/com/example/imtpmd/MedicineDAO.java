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

    // Selecteert medicijnen op basis van de tijd dat het genomen moet worden
    @Query("SELECT * FROM medicine WHERE time >= :timeStart AND time < :timeEnd")
    List<Medicine> loadByTime(int timeStart, int timeEnd);

    @Insert
    void insertAll(Medicine... medicines);

    @Delete
    void delete (Medicine medicine);
}
