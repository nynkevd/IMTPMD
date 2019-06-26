package com.example.imtpmd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MedicineRepository {
    private MedicineDAO medicineDAO;
    private LiveData<List<Medicine>> allMedication;
    private LiveData<List<Medicine>> ochtendList;
    private LiveData<List<Medicine>> middagList;
    private LiveData<List<Medicine>> avondList;
    private LiveData<List<Medicine>> nachtList;
    private LiveData<List<String>> distinctNames;
    private LiveData<Long> dateVan;
    private LiveData<Long> dateTot;
    private LiveData<List<Medicine>> overviewData;
    private static Application app;

    MedicineRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        medicineDAO = db.medicineDAO();
        allMedication = medicineDAO.loadAll();

        ochtendList = medicineDAO.loadByTime(getDateTime(6), getDateTime(12));
        middagList = medicineDAO.loadByTime(getDateTime(12), getDateTime(18));
        avondList = medicineDAO.loadByTime(getDateTime(18), getDateTime(24));
        nachtList = medicineDAO.loadByTime(getDateTime(0), getDateTime(6));

        distinctNames = medicineDAO.loadDistinctNames();
        dateVan = medicineDAO.loadDateVan("testOchtend9"); //aanpassen!!
        dateTot = medicineDAO.loadDateTot("testOchtend9"); //aanapssen!!

        overviewData = medicineDAO.loadOverviewData();
        app = application;
    }

    private Long getDateTime(int hour){
        Calendar currentTime = Calendar.getInstance();
        currentTime.set(Calendar.HOUR_OF_DAY, hour);
        currentTime.set(Calendar.MINUTE, 0);
        currentTime.set(Calendar.MILLISECOND, 0);

        return currentTime.getTimeInMillis();
    }

    LiveData<List<Medicine>> getAllMedication() {
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

    LiveData<List<String>> getDistinctNames() { return distinctNames; }

    LiveData<Long> getDateVan() { return dateVan; }

    LiveData<Long> getDateTot() {return dateTot; }

    LiveData<List<Medicine>> getOverviewData() {return overviewData; }

//    LiveData<List<OverviewData>> getOverviewData(){
////        for(String name : distinctNames){
////
////        }
//    }

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

    public void deleteByName(String name){new deleteAsyncTask(medicineDAO).execute(name);}

    private static class deleteAsyncTask extends AsyncTask<String, Void, Void>{
        private MedicineDAO asyncTaskDAO;

        deleteAsyncTask(MedicineDAO dao){asyncTaskDAO = dao;}

        @Override
        protected Void doInBackground(String... strings) {
            for(String name : strings ){
                asyncTaskDAO.deleteByName(name);
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void result){
            Log.d("teeeeest", "postexecute");
        }
    }

}
