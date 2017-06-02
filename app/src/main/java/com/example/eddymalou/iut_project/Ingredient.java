package com.example.eddymalou.iut_project;

/**
 * Created by EddyMalou on 01/05/2017.
 */
public class Ingredient {
    private long id;
    private String ingredient_name;
    public int quantite;
    public String mesure;

    public Ingredient()
    {

    }

    public Ingredient(int id, String ingredient_name)
    {
        this.id = id;
        this.ingredient_name = ingredient_name;
        this.quantite = 0;
        this.mesure = "";
    }

    public Ingredient(int id, String ingredient_name, int quantite, String mesure)
    {
        this.id = id;
        this.ingredient_name = ingredient_name;
        this.quantite = quantite;
        this.mesure = mesure;
    }

    public Ingredient(String ingredient_name, int quantite, String mesure)
    {
        this.ingredient_name = ingredient_name;
        this.quantite = quantite;
        this.mesure = mesure;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getIngredient_name() {
        return ingredient_name;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getMesure() {
        return mesure;
    }

    public void setMesure(String mesure) {
        this.mesure = mesure;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public void setIngredient_name(String ingredient_name) {
        this.ingredient_name = ingredient_name;
    }

    // Sera utilisée par ArrayAdapter dans la ListView
    @Override
    public String toString() {
        return ingredient_name;
    }
}
