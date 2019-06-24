package com.example.imtpmd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

public class MedicationNameViewModel extends AndroidViewModel {
    private MedicationNameRepository medicationNameRepository;
    private LiveData<List<MedicationName>> allMedicationNames;
    private static LiveData<String> requestedMedicationName;
    private int id;
    //private static LiveData<Integer> firstId;

    public MedicationNameViewModel(Application application) {
        super(application);
        medicationNameRepository = new MedicationNameRepository(application);
        allMedicationNames = medicationNameRepository.getAllMedicationNames();
        requestedMedicationName = medicationNameRepository.getMedicationName(id);
       // firstId = medicationNameRepository.getFirstId();
    }

    LiveData<List<MedicationName>> getAllMedicationNames() {
        return this.allMedicationNames;
    }

    LiveData<String> getRequestedMedicationName(int givenId){
        id = givenId;
        return this.requestedMedicationName;
    }

    //LiveData<Integer> getFirstId() {
    //    return this.firstId;
    //}

    public void insert(MedicationName medicationName){
        Log.d("INSERT", "Insert in MedicationNameViewModel");
        medicationNameRepository.insert(medicationName);
    }

    public void deleteAll(){
        Log.d("DELETE", "Delete in MedicationNameViewModel");
        medicationNameRepository.deleteAll();
    }
}
