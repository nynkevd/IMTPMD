package com.example.imtpmd;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Medicine {
    // Het id wordt automatisch gegenereerd
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String time;
    private int milligram;
    private Long date;
    private Long dateFrom;
    private Long dateTo;
    private Boolean isChecked;

    public Medicine(String name, int milligram, Long date, Boolean isChecked, Long dateFrom, Long dateTo, String time){
        this.name = name;
        this.milligram = milligram;
        this.date = date;
        this.isChecked = isChecked;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.time = time;
    }

    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public int getMilligram(){return this.milligram;}
    public Long getDate(){return this.date;}
    public Long getDateFrom(){return this.dateFrom;}
    public Long getDateTo(){return this.dateTo;}
    public Boolean getChecked(){return this.isChecked;}
    public String getTime(){return this.time;}

    public void setId(int id){this.id = id;}
    public void setName(String name){this.name = name;}
    public void setMilligram(int milligram){this.milligram = milligram;}
    public void setDate(Long date){this.date = date;}
    public void setDateFrom(Long dateFrom){this.dateFrom = dateFrom;}
    public void setDateTo(Long dateTo){this.dateTo = dateTo;}
    public void setChecked(Boolean isChecked){this.isChecked = isChecked;}
    public void setTime(String time){this.time = time;}

}
