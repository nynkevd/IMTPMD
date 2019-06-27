package com.example.imtpmd;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(indices = {@Index(value = "name", unique = true)})
public class MedicationName {
    public MedicationName(String name) {
        this.name = name;
    }

    @PrimaryKey (autoGenerate = true)
    private int id;
    @NonNull
    private String name;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }






}
