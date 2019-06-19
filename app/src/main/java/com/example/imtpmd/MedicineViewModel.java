package com.example.imtpmd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class MedicineViewModel extends AndroidViewModel {
    private MedicineRepository medicineRepository;
    private LiveData<List<Medicine>>
            allMedication;

    public MedicineViewModel(Application application){
        super (application);
        medicineRepository = new MedicineRepository(application);
        allMedication = medicineRepository.getAllMedication();
    }

    LiveData<List<Medicine>> getAllMedication() {
        return this.allMedication;
    }

    public void insert(Medicine medicine){
        medicineRepository.insert(medicine);
    }
}
