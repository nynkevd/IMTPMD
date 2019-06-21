package com.example.imtpmd;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {Medicine.class, MedicationName.class, MedicationCount.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MedicineDAO medicineDAO();

    public static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "medicine")
                            .addCallback(appDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static AppDatabase.Callback appDatabaseCallback =
        new AppDatabase.Callback() {
            @Override
            //Dit wordt  uitgevoerd bij het opstarten van de app
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);

            }
        };


}
