package com.example.imtpmd;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MedicationCount {
    public MedicationCount(int count) {
        this.count = count;
    }

    @PrimaryKey (autoGenerate = true)
    private int id;
    private int count;

    public int getCount() {
        return count;
    }
    public int getId() {
        return id;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public void setId(int id) {
        this.id = id;
    }
}
