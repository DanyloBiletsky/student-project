package edu.javaproject.studentorder.domain;

public class CountryArea {
    private String areaId;
    private String areaName;

    public CountryArea(){}
    public CountryArea(String areaId, String areaName){
        this.areaId = areaId;
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }
    public String getAreaName() {
        return areaName;
    }
    public void setAreaId(String areaId){
        this.areaId = areaId;
    }
    public void setAreaName(String areaName){
        this.areaName = areaName;
    }



}
