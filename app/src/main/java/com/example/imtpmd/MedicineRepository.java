package com.example.imtpmd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MedicineRepository {
    private MedicineDAO medicineDAO;
    private LiveData<List<Medicine>> allMedication;

    MedicineRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        medicineDAO = db.medicineDAO();
        allMedication = medicineDAO.loadAll();
    }

    LiveData<List<Medicine>> getAllMedication(){
        return allMedication;
    }

    public void insert(Medicine medicine){
        new insertAsyncTask(medicineDAO).execute(medicine);
    }

    private static class insertAsyncTask extends AsyncTask<Medicine, Void, Void> {
        private MedicineDAO asyncTaskDAO;

        insertAsyncTask(MedicineDAO dao){
            asyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(Medicine... medicines) {
            asyncTaskDAO.insertAll(medicines);
            return null;
        }
    }
}
