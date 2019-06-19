package com.example.imtpmd;

import android.os.AsyncTask;
import android.util.Log;

public class PopulateDBAsync extends AsyncTask<Void, Void, Void> {
    private final MedicineDAO medicineDAO;

    PopulateDBAsync(AppDatabase db){
        medicineDAO = db.medicineDAO();
    }

    @Override
    protected Void doInBackground(final Void... params){
        // Dit wordt nu uitgevoerd bij het opstarten, maar het doet eigenlijk niks
        Log.d("DBBBB", "populateDBAsync");
        Medicine medicine = new Medicine();
        medicine.setName("tesssst");
        medicine.setChecked(false);
        medicine.setMilligram(20);
        medicine.setTime(8);

        //medicineDAO.insertAll(medicine);

        return null;
    }
}
