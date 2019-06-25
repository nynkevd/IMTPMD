package com.example.imtpmd;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import java.util.List;

public class MedicineViewModel extends AndroidViewModel {
    private MedicineRepository medicineRepository;
    private LiveData<List<Medicine>> allMedication;
    private static LiveData<List<Medicine>> ochtendList;
    private static LiveData<List<Medicine>> middagList;
    private static LiveData<List<Medicine>> avondList;
    private static LiveData<List<Medicine>> nachtList;
    private LiveData<List<String>> distinctNames;
    private LiveData<Long> dateVan;
    private LiveData<Long> dateTot;
    private LiveData<List<Medicine>> overviewData;

    public MedicineViewModel(Application application){
        super (application);
        medicineRepository = new MedicineRepository(application);
        allMedication = medicineRepository.getAllMedication();
        ochtendList = medicineRepository.getOchtendList();
        middagList = medicineRepository.getMiddagList();
        avondList = medicineRepository.getAvondList();
        nachtList = medicineRepository.getNachtList();
        distinctNames = medicineRepository.getDistinctNames();
        dateVan = medicineRepository.getDateVan();
        dateTot = medicineRepository.getDateTot();
        overviewData = medicineRepository.getOverviewData();
    }

    LiveData<List<Medicine>> getAllMedication() {
        return this.allMedication;
    }

    LiveData<List<Medicine>> getOchtendList() {return this.ochtendList;}

    LiveData<List<Medicine>> getMiddagList() {return this.middagList;}

    LiveData<List<Medicine>> getAvondList() {return this.avondList;}

    LiveData<List<Medicine>> getNachtList() {return this.nachtList;}

    LiveData<List<String>> getDistinctNames() {return this.distinctNames;}

    LiveData<Long> getDateVan() {return this.dateVan;}

    LiveData<Long> getDateTot() {return this.dateTot;}

    LiveData<List<Medicine>> getOverviewData() {return this.overviewData; }

    public void insert(Medicine medicine){
        Log.d("tessst", "Insert in MedicineViewModel");
        medicineRepository.insert(medicine);
    }

    public void update(Medicine medicine){
        Log.d("tessst", "Update in medicineViewModel");
        medicineRepository.update(medicine);
    }

}
