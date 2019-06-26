package com.example.imtpmd;

import android.arch.persistence.room.PrimaryKey;

public class MedicationInfo {
    @PrimaryKey
    private int id;

    private String name;
    private String use;
    private String sort;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
