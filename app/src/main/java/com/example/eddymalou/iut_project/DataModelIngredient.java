package com.example.eddymalou.iut_project;

/**
 * Created by EddyMalou on 23/05/2017.
 */
public class DataModelIngredient {
    String name;
    String id;

    public DataModelIngredient(String name, String id) {
        this.name=name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
