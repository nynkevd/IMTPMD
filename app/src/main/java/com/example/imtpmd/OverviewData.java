package com.example.imtpmd;

public class OverviewData {
    private String name;
    private Long dateVan;
    private Long dateTo;
    private Long[] time;

    //, Long dateVan, Long dateTo, Long time
    public OverviewData(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getDateVan() {
        return dateVan;
    }

    public Long getDateTo() {
        return dateTo;
    }

    public Long[] getTime() {
        return time;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDateVan(Long dateVan) {
        this.dateVan = dateVan;
    }

    public void setDateTo(Long dateTo) {
        this.dateTo = dateTo;
    }

    public void setTime(Long[] time) {
        this.time = time;
    }




}
