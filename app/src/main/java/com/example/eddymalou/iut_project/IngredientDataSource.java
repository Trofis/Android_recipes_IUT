package com.example.eddymalou.iut_project;

/**
 * Created by EddyMalou on 01/05/2017.
 */

import java.util.ArrayList;
import java.util.List;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class IngredientDataSource {

    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID_INGREDIENT,
            MySQLiteHelper.COLUMN_INGREDIENT_NAME };

    public IngredientDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Ingredient createIngredient(String ingredientName) {
        long insertId = ifIngExists(ingredientName);
        if (insertId == 0)
        {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_INGREDIENT_NAME, ingredientName);
            insertId = database.insert(MySQLiteHelper.TABLE_INGREDIENT, null,
                    values);
        }
        Cursor cursor = database.query(MySQLiteHelper.TABLE_INGREDIENT,
                allColumns, MySQLiteHelper.COLUMN_ID_INGREDIENT + " = '" + insertId+"' ", null,
                null, null, null);
        cursor.moveToFirst();
        Ingredient newIngredient = cursorToIngredient(cursor);
        cursor.close();
        return newIngredient;
    }

    public void deleteIngredient(Ingredient ing) {
        long id = ing.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_INGREDIENT, MySQLiteHelper.COLUMN_ID_INGREDIENT
                + " = " + id, null);
    }


    public List<Ingredient> getAllIngredient() {
        List<Ingredient> ings = new ArrayList<Ingredient>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_INGREDIENT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ingredient ing = cursorToIngredient(cursor);
            ings.add(ing);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return ings;
    }

    public int ifIngExists(String ingredient_name)
    {
        int res = 0;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_INGREDIENT,
                allColumns, MySQLiteHelper.COLUMN_INGREDIENT_NAME + " = '" + ingredient_name+"'", null,
                null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            res = cursor.getInt(0);
        }

        return res;
    }

    public int getIdIng(String name_ing)
    {
        int res = 0;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_INGREDIENT,
                allColumns, MySQLiteHelper.COLUMN_INGREDIENT_NAME + " = '" + name_ing+"'", null,
                null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            res = cursor.getInt(0);
        }

        return res;
    }

    public List<Ingredient> getIngredients(int idRecipe)
    {

        //new String[]{"ingredient."+MySQLiteHelper.COLUMN_ID_INGREDIENT, "ingredient.ingredientName", MySQLiteHelper.COLUMN_RECIPE_INGREDIENT_QUANTITE, "recipeIngredient."+MySQLiteHelper.COLUMN_RECIPE_INGREDIENT_MESURE}
        //Cursor cursor = database.query(MySQLiteHelper.TABLE_INGREDIENT + " , "+MySQLiteHelper.TABLE_RECIPE_INGREDIENT,
        //        allColumns, MySQLiteHelper.TABLE_INGREDIENT+"."+MySQLiteHelper.COLUMN_ID_INGREDIENT+" = "+MySQLiteHelper.TABLE_RECIPE_INGREDIENT+"."+MySQLiteHelper.COLUMN_ID_INGREDIENT+" AND " +MySQLiteHelper.COLUMN_ID_RECIPE + " = " + idRecipe+"", null,
        //        null, null, null);
        Log.d("id recipe", String.valueOf(idRecipe));
        final String MY_QUERY = "SELECT * FROM ingredient ing INNER JOIN recipeIngredient repIng ON ing._id_ingredient=repIng._id_ingredient WHERE repIng._id_recipe=?";
        final String Query2 ="SELECT * from ingredient";
        Cursor cursor = database.rawQuery(MY_QUERY, new String[]{String.valueOf(idRecipe)});
        Cursor c2 = database.rawQuery(Query2, new String[] {});
        cursor.moveToFirst();
        ArrayList<Ingredient> l_ing = new ArrayList<Ingredient>();
        while (!cursor.isAfterLast()) {

            Ingredient ing = cursorToIngredientFull(cursor);
            Log.d("query quant",String.valueOf(ing.getQuantite()));
            Log.d("query mes",ing.getMesure());
            l_ing.add(ing);
            cursor.moveToNext();
        }
        c2.moveToFirst();
        while(!c2.isAfterLast())
        {
            Log.e("ing",String.valueOf(c2.getLong(0)) );
            Log.e("ing",c2.getString(1) );
            c2.moveToNext();
        }
        Log.i("liste", String.valueOf(l_ing.size()));
        return l_ing;

    }



    private Ingredient cursorToIngredient(Cursor cursor) {
        Ingredient ing = new Ingredient();
        ing.setId(cursor.getLong(0));
        ing.setIngredient_name(cursor.getString(1));
        return ing;
    }

    private Ingredient cursorToIngredientFull(Cursor cursor) {
        Ingredient ing = new Ingredient();
        ing.setId(cursor.getLong(0));
        ing.setIngredient_name(cursor.getString(1));
        ing.setQuantite(cursor.getInt(cursor.getColumnIndex("quantite")));
        ing.setMesure(cursor.getString(cursor.getColumnIndex("mesure")));
        return ing;
    }
}

