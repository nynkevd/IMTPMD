package com.example.imtpmd;

public class OverviewData {
    private String name;
    private Long dateVan;
    private Long dateTot;
    //private Long[] time;

    //, Long dateVan, Long dateTo, Long time
    public OverviewData(String name, Long dateVan, Long dateTot){
        this.name = name;
        this.dateVan = dateVan;
        this.dateTot = dateTot;
        //,
    }

    public String getName() {
        return name;
    }

    public Long getDateVan() {
        return dateVan;
    }

    public Long getDateTot() {
        return dateTot;
    }

//    public Long[] getTime() {
//        return time;
//    }


    public void setName(String name) {
        this.name = name;
    }

    public void setDateVan(Long dateVan) {
        this.dateVan = dateVan;
    }

    public void setDateTot(Long dateTot) {
        this.dateTot = dateTot;
    }

//    public void setTime(Long[] time) {
//        this.time = time;
//    }




}
