package com.example.imtpmd;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

public class MedicationNameRepository {
    private MedicationNameDAO medicationNameDAO;
    private LiveData<List<MedicationName>> allMedicationNames;
    private LiveData<String> requestedMedicationName;
    private int id;
    private LiveData<Integer> firstId;
    private String firstName;
    private static Application app;

    MedicationNameRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        medicationNameDAO = db.medicationNameDAO();
        allMedicationNames = medicationNameDAO.loadAll();
        requestedMedicationName = medicationNameDAO.getMedicationName(id);
        firstId = medicationNameDAO.getFirstId("first");

        app = application;
    }

    LiveData<List<MedicationName>> getAllMedicationName() {
        return allMedicationNames;
    }

    LiveData<String> getMedicationName(int givenId) {
        id = givenId;
        return requestedMedicationName;
    }

    LiveData<Integer> getFirstId() {
        return firstId;
    }

    public void insert(MedicationName medicationName){
        new insertAsyncTask(medicationNameDAO).execute(medicationName);
    }

    private static class insertAsyncTask extends AsyncTask<MedicationName, Void, Void> {
        private MedicationNameDAO asyncTaskDAO;

        insertAsyncTask(MedicationNameDAO dao){
            asyncTaskDAO = dao;
        }

        @Override
        protected Void doInBackground(MedicationName... medicationNames) {
            asyncTaskDAO.insertAll(medicationNames);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(app.getApplicationContext(), "Medicationnames toegevoegd aan database", Toast.LENGTH_LONG);
        }
    }
}
