package com.example.eddymalou.iut_project;

/**
 * Created by EddyMalou on 01/05/2017.
 */

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class RecetteDataSource {

    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID_RECIPE,
            MySQLiteHelper.COLUMN_RECIPE_NAME, MySQLiteHelper.COLUMN_IMG_SRC_RECIPE };

    public RecetteDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Recette createRecipe(String RecipeName, List<Ingredient> lIngs) {
        long insertId = ifRecipeExists(RecipeName);
        if (insertId == 0)
        {
            String imgSrc = android.os.Environment.DIRECTORY_DCIM+"/Facebook/FB_IMG_1494753109529.jpg";
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_IMG_SRC_RECIPE, imgSrc);
            values.put(MySQLiteHelper.COLUMN_RECIPE_NAME, RecipeName);
            insertId = database.insert(MySQLiteHelper.TABLE_RECIPE, null,
                    values);


            for(Ingredient ing : lIngs)
            {
                ContentValues val = new ContentValues();
                val.put(MySQLiteHelper.COLUMN_ID_RECIPE, insertId);
                val.put(MySQLiteHelper.COLUMN_ID_INGREDIENT, ing.getId());
                val.put(MySQLiteHelper.COLUMN_RECIPE_INGREDIENT_QUANTITE, ing.getQuantite());
                val.put(MySQLiteHelper.COLUMN_RECIPE_INGREDIENT_MESURE,ing.getMesure());
                database.insert(MySQLiteHelper.TABLE_RECIPE_INGREDIENT, null,
                        val);
            }
        }



        Cursor cursor = database.query(MySQLiteHelper.TABLE_RECIPE,
                allColumns, MySQLiteHelper.COLUMN_ID_RECIPE + " = '" + insertId+"' ", null,
                null, null, null);
        cursor.moveToFirst();
        Recette newRecipe = cursorToRecipe(cursor);
        newRecipe.setIngredients(lIngs);
        cursor.close();
        return newRecipe;
    }

    public Recette createRecipe(String RecipeName, List<Ingredient> lIngs, String imgSrc) {
        long insertId = ifRecipeExists(RecipeName);
        if (insertId == 0)
        {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_IMG_SRC_RECIPE, imgSrc);
            values.put(MySQLiteHelper.COLUMN_RECIPE_NAME, RecipeName);
            insertId = database.insert(MySQLiteHelper.TABLE_RECIPE, null,
                    values);


            for(Ingredient ing : lIngs)
            {
                Log.e("createRecipe", ing.getIngredient_name());
                Log.e("createRecipe", String.valueOf(ing.getId()));
                Log.e("createRecipe", String.valueOf(ing.getQuantite()));
                Log.e("createRecipe", ing.getMesure());
                ContentValues val = new ContentValues();
                val.put(MySQLiteHelper.COLUMN_ID_RECIPE, insertId);
                val.put(MySQLiteHelper.COLUMN_ID_INGREDIENT, ing.getId());
                val.put(MySQLiteHelper.COLUMN_RECIPE_INGREDIENT_QUANTITE, ing.getQuantite());
                val.put(MySQLiteHelper.COLUMN_RECIPE_INGREDIENT_MESURE,ing.getMesure());
                database.insert(MySQLiteHelper.TABLE_RECIPE_INGREDIENT, null,
                        val);
            }
        }



        Cursor cursor = database.query(MySQLiteHelper.TABLE_RECIPE,
                allColumns, MySQLiteHelper.COLUMN_ID_RECIPE + " = '" + insertId+"' ", null,
                null, null, null);
        cursor.moveToFirst();
        Recette newRecipe = cursorToRecipe(cursor);
        newRecipe.setIngredients(lIngs);
        cursor.close();
        return newRecipe;
    }

    public void deleteRecipe(int id_rec) {
        long id = id_rec;
        System.out.println("Comment deleted with id: " + id);

        database.delete(MySQLiteHelper.TABLE_RECIPE_INGREDIENT, MySQLiteHelper.COLUMN_ID_RECIPE
                + " = " + id, null);
        database.delete(MySQLiteHelper.TABLE_USER, MySQLiteHelper.COLUMN_ID_USER
                + " = " + id, null);
        database.delete(MySQLiteHelper.TABLE_RECIPE,MySQLiteHelper.COLUMN_ID_RECIPE
                + " = " + id, null );
    }

    public void deleteRecipe(String rec_name) {

    int id = database.delete(MySQLiteHelper.TABLE_RECIPE, MySQLiteHelper.COLUMN_RECIPE_NAME
                + " = '" + rec_name+"'", null);
        database.delete(MySQLiteHelper.TABLE_RECIPE_INGREDIENT, MySQLiteHelper.COLUMN_ID_RECIPE
                + " = " + id, null);

        database.delete(MySQLiteHelper.TABLE_USER_RECIPE, MySQLiteHelper.COLUMN_ID_RECIPE
                + " = " + id, null);
    }

    public List<Recette> getRecipeByIngs(List<String> l_ings)
    {
        String MY_QUERY = "SELECT DISTINCT rec._id_recipe,rec.recipeName,rec.imgSrcRecipe FROM recipe rec INNER JOIN recipeIngredient repIng ON rec._id_recipe=repIng._id_recipe INNER JOIN ingredient ing on repIng._id_ingredient=ing._id_ingredient WHERE ing.ingredientName=?";
        HashMap<String, Recette>  AllRecipeRes = new HashMap<>();
        List<String> remove_l = new ArrayList<>();
        List<Recette> res = new ArrayList<>();
        int i = 0;
        for(String s : l_ings)
        {
            Cursor c = database.rawQuery(MY_QUERY, new String[]{s});
            List<String> rec_int = new ArrayList<>();
            c.moveToFirst();
            while (!c.isAfterLast()) {

                Recette rec = cursorToRecipe(c);
                rec_int.add(rec.getRecette_name());
                Log.d("query_getRecipe", rec.getRecette_name());
                if (!AllRecipeRes.containsKey(rec.getRecette_name()) && i== 0)
                    AllRecipeRes.put(rec.getRecette_name(), rec);
                c.moveToNext();
            }

            for (String key : AllRecipeRes.keySet())
            {
                if (!rec_int.contains(key))
                    remove_l.add(key);

            }

            for (String str : remove_l)
            {
                AllRecipeRes.remove(str);
            }

            i++;

        }

        for (String key : AllRecipeRes.keySet())
        {

            res.add(AllRecipeRes.get(key));
        }



        return res;
    }

    public List<Recette> getRecipeByIngs(List<String> l_ings, String et_ou)
    {
        if (et_ou == "ou")
        {
            String MY_QUERY = "SELECT DISTINCT rec._id_recipe,rec.recipeName,rec.imgSrcRecipe FROM recipe rec INNER JOIN recipeIngredient repIng ON rec._id_recipe=repIng._id_recipe INNER JOIN ingredient ing on repIng._id_ingredient=ing._id_ingredient WHERE ing.ingredientName IN (";
            int ind = 0;
            String[] l_array = new String[l_ings.size()];
            for (String s : l_ings)
            {
                if (ind == l_ings.size()-1)
                    MY_QUERY+="?)";
                else
                    MY_QUERY+="?,";
                l_array[ind] = s;
                ind++;
            }
            List<Recette> arr_res = new ArrayList<>();
            Cursor c = database.rawQuery(MY_QUERY, l_array);
            c.moveToFirst();

            while (!c.isAfterLast()) {

                Recette rec = cursorToRecipe(c);
                arr_res.add(rec);
                c.moveToNext();
            }
            return arr_res;
        }
        else
        {
            String MY_QUERY = "SELECT DISTINCT rec._id_recipe,rec.recipeName,rec.imgSrcRecipe FROM recipe rec INNER JOIN recipeIngredient repIng ON rec._id_recipe=repIng._id_recipe INNER JOIN ingredient ing on repIng._id_ingredient=ing._id_ingredient WHERE ing.ingredientName=?";
            HashMap<String, Recette>  AllRecipeRes = new HashMap<>();
            List<String> remove_l = new ArrayList<>();
            List<Recette> res = new ArrayList<>();
            int i = 0;
            for(String s : l_ings)
            {
                Cursor c = database.rawQuery(MY_QUERY, new String[]{s});
                List<String> rec_int = new ArrayList<>();
                c.moveToFirst();
                while (!c.isAfterLast()) {

                    Recette rec = cursorToRecipe(c);
                    rec_int.add(rec.getRecette_name());
                    Log.d("query_getRecipe", rec.getRecette_name());
                    if (!AllRecipeRes.containsKey(rec.getRecette_name()) && i== 0)
                        AllRecipeRes.put(rec.getRecette_name(), rec);
                    c.moveToNext();
                }

                for (String key : AllRecipeRes.keySet())
                {
                    if (!rec_int.contains(key))
                        remove_l.add(key);

                }

                for (String str : remove_l)
                {
                    AllRecipeRes.remove(str);
                }

                i++;

            }

            for (String key : AllRecipeRes.keySet())
            {

                res.add(AllRecipeRes.get(key));
            }



            return res;
        }

    }


    public List<Recette> getAllRecipe() {
        List<Recette> recipes = new ArrayList<Recette>();

        //String MY_QUERY = "SELECT * FROM recipe INNER JOIN recipeIngredient ON recipe._id_recipe=recipeIngredient._id_recipe INNER JOIN ingredient on recipeIngredient._id_ingredient = ingredient._id_ingredient";

        //Cursor cursor = database.rawQuery(MY_QUERY, null);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RECIPE,
                allColumns, null, null,
                null, null, null);

        cursor.moveToFirst();
        int i = 0;
        while (!cursor.isAfterLast()) {
            Recette rec = cursorToRecipe(cursor);
            Log.d("rec", rec.toString());
            recipes.add(rec);
            cursor.moveToNext();
            i++;
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return recipes;
    }

    public int ifRecipeExists(String recipeName)
    {
        int res = 0;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RECIPE,
                allColumns, MySQLiteHelper.COLUMN_RECIPE_NAME + " = '" + recipeName+"'", null,
                null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            res = cursor.getInt(0);
        }

        return res;
    }



    private Recette cursorToRecipe(Cursor cursor) {
        Recette rec = new Recette();
        rec.setId(cursor.getInt(cursor.getColumnIndex("_id_recipe")));
        rec.setRecette_name(cursor.getString(cursor.getColumnIndex("recipeName")));
        rec.setImgsrc(cursor.getString(cursor.getColumnIndex("imgSrcRecipe")));
        return rec;
    }
}

