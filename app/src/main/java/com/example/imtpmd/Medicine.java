package com.example.imtpmd;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Medicine {
    // Het id wordt automatisch gegenereerd
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int milligram;
    private int time;
    private Boolean isChecked;

    public Medicine(){};

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
