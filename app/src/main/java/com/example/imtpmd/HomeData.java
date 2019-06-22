package com.example.imtpmd;

import java.util.ArrayList;

public class HomeData {
    public String title;
    public ArrayList<Medicine> medicineList;

    public HomeData(String title, ArrayList<Medicine> list) {
        this.title = title;
        this.medicineList = list;
    }

    public String getTitle(){
        return this.title;
    }

    public ArrayList getMedicineList(){
        return this.medicineList;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setMedicineList(ArrayList medicineList){
        this.medicineList = medicineList;
    }
}
