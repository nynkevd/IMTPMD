package com.example.imtpmd;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Medicine.class, MedicationName.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MedicineDAO medicineDAO();

    public abstract MedicationNameDAO medicationNameDAO();
}
