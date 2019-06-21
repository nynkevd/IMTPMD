package com.example.imtpmd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MedicineRepository {
    private MedicineDAO medicineDAO;
    private LiveData<List<Medicine>> allMedication;
    private LiveData<List<Medicine>> ochtendList;
    private LiveData<List<Medicine>> middagList;
    private LiveData<List<Medicine>> avondList;
    private LiveData<List<Medicine>> nachtList;
    private static Application app;

    MedicineRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        medicineDAO = db.medicineDAO();
        allMedication = medicineDAO.loadAll();
        ochtendList = medicineDAO.loadByTime(6, 12);
        middagList = medicineDAO.loadByTime(12, 18);
        avondList = medicineDAO.loadByTime(18, 24);
        nachtList = medicineDAO.loadByTime(0, 6);
        app = application;
    }

    LiveData<List<Medicine>> getAllMedication(){
        return allMedication;
    }

    LiveData<List<Medicine>> getOchtendList(){
        return ochtendList;
    }

    LiveData<List<Medicine>> getMiddagList(){
        return middagList;
    }

    LiveData<List<Medicine>> getAvondList(){
        return avondList;
    }

    LiveData<List<Medicine>> getNachtList(){
        return nachtList;
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

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(app.getApplicationContext(), "Medicijn toegevoegd aan database", Toast.LENGTH_LONG);
        }
    }

    public void update(Medicine medicine){new updateAsyncTask(medicineDAO).execute(medicine);}

    private static class updateAsyncTask extends AsyncTask<Medicine, Void, Void>{
        private MedicineDAO asyncTaskDAO;

        updateAsyncTask(MedicineDAO dao){asyncTaskDAO = dao;}

        @Override
        protected Void doInBackground(Medicine... medicines) {
            asyncTaskDAO.update(medicines);
            return null;
        }
    }

}
