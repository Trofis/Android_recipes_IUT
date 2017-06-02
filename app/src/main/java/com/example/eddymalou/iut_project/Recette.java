package com.example.eddymalou.iut_project;

import java.util.List;

/**
 * Created by EddyMalou on 01/05/2017.
 */
public class Recette {
    private long id;
    private String recette_name;
    private String imgsrc;
    private List<Ingredient> ingredients;

    public Recette()
    {

    }

    public Recette(int id, String recette_name, String imgsrc)
    {
        this.id = id;
        this.recette_name = recette_name;
        this.imgsrc = imgsrc;
    }

    public Recette(String recette_name, String imgsrc)
    {
        this.recette_name = recette_name;
        this.imgsrc = imgsrc;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public String getRecette_name() {
        return recette_name;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public void setRecette_name(String recette_name) {
        this.recette_name = recette_name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return recette_name;
    }
}
