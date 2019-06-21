package com.example.imtpmd;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity
public class Medicine {
    // Het id wordt automatisch gegenereerd
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int milligram;
    private int time;
    private Boolean isChecked;

    public Medicine(String name, int milligram, int time, Boolean isChecked){
        this.name = name;
        this.milligram = milligram;
        this.time = time;
        this.isChecked = isChecked;
    }

    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public int getMilligram(){return this.milligram;}
    public int getTime(){return this.time;}
    public Boolean getChecked(){return this.isChecked;}

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setMilligram(int milligram){this.milligram = milligram;}
    public void setTime(int time){this.time = time;}
    public void setChecked(Boolean isChecked){this.isChecked = isChecked;}

}
