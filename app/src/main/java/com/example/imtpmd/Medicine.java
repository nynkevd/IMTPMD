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
    private String time; // Misschien moeten we hier per dagdeel een boolean van maken?

    public Medicine(){};

    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public int getMilligram(){return this.milligram;}
    public String getTime(){return this.time;}

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setMilligram(int milligram){this.milligram = milligram;}
    public void setTime(String time){this.time = time;}

}
