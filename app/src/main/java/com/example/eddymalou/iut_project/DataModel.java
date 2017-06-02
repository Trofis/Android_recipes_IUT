package com.example.eddymalou.iut_project;

/**
 * Created by EddyMalou on 09/05/2017.
 */
public class DataModel {
    String name;
    String imgsrc;
    String id;

    public DataModel(String name, String id, String imgsrc) {
        this.name=name;
        this.id=id;
        this.imgsrc = imgsrc;

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getImgsrc() {return imgsrc;}

}
