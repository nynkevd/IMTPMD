package com.example.imtpmd;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface MedicineDAO {
    @Query("SELECT * FROM medicine")
    LiveData<List<Medicine>> loadAll();

    // Selecteert medicijnen op basis van de tijd dat het genomen moet worden
    @Query("SELECT * FROM medicine WHERE date >= :timeStart AND date < :timeEnd")
    LiveData<List<Medicine>> loadByTime(Long timeStart, Long timeEnd);

    // Selecteer alle verschillende namen
    @Query("SELECT DISTINCT name FROM medicine")
    LiveData<List<String>> loadDistinctNames(); //uit list halen

    // Selecteer de medicijnen met verschillende data of tijd, waarbij de datum tot niet lager is dan vandaag
    @Query("SELECT * FROM medicine WHERE dateTo > :currentTime GROUP BY name, dateFrom, dateTo, time")
    LiveData<List<Medicine>> loadDistinctData(Long currentTime); //where datum > datum vandaag

    // Selecteer de startdatum van een medicijn
    @Query("SELECT MIN(date) FROM medicine WHERE name = :name")
    LiveData<Long> loadDateVan(String name);

    // selecteer de einddatum van een medicijn
    @Query("SELECT MAX(date) FROM medicine WHERE name = :name")
    LiveData<Long> loadDateTot(String name);

    // selecteer de einddatum van een medicijn
    @Query("SELECT med.* FROM medicine med INNER JOIN (SELECT name, MAX(date) AS dateTot FROM medicine GROUP BY name) groupMed ON med.name = groupMed.name AND med.date = groupMed.dateTot")
    LiveData<List<Medicine>> loadOverviewData();

    // Delete alle medicijnen met een bepaalde naam
    @Query("DELETE FROM medicine WHERE name = :name AND dateFrom = :dateFrom AND dateTo = :dateTo AND time = :time")
    void deleteByName(String name, Long dateFrom, Long dateTo, String time); //Tijdstip toevoegen!!!

    // Verander de waardes van een bepaald medicijn (bijvoorbeeld checked)
    @Update
    void update(Medicine... medicines);


    @Insert
    void insertAll(Medicine... medicines);

    @Delete
    void delete (Medicine medicine);
}
