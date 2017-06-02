package com.example.eddymalou.iut_project;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID_USER = "_id_user";
    public static final String COLUMN_USER_NAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_RECIPE = "recipe";
    public static final String COLUMN_ID_RECIPE = "_id_recipe";
    public static final String COLUMN_RECIPE_NAME = "recipeName";
    public static final String COLUMN_IMG_SRC_RECIPE = "imgSrcRecipe";

    public static final String TABLE_INGREDIENT = "ingredient";
    public static final String COLUMN_ID_INGREDIENT = "_id_ingredient";
    public static final String COLUMN_INGREDIENT_NAME = "ingredientName";

    public static final String TABLE_USER_RECIPE= "userRecipe";
    public static final String COLUMN_ID_USER_RECIPE= "_id_user_recipe";

    public static final String TABLE_RECIPE_INGREDIENT= "recipeIngredient";
    public static final String COLUMN_ID_RECIPE_INGREDIENT= "_id_recipe_ingredient";
    public static final String COLUMN_RECIPE_INGREDIENT_QUANTITE= "quantite";
    public static final String COLUMN_RECIPE_INGREDIENT_MESURE= "mesure";

    private static final String DATABASE_NAME = "cookApp.db";
    private static final int DATABASE_VERSION = 1;

    // Commande sql pour la création de la base de données
    private static final String DATABASE_CREATE_USER = "create table "
            + TABLE_USER + "(" + COLUMN_ID_USER
            + " integer primary key autoincrement, " + COLUMN_USER_NAME
            + " text not null, " +
            COLUMN_PASSWORD+" text not null" + ");";

    private static final String DATABASE_CREATE_RECIPE = "create table "
            + TABLE_RECIPE + "(" + COLUMN_ID_RECIPE
            + " integer primary key autoincrement, " + COLUMN_RECIPE_NAME
            + " text not null, " +
            COLUMN_IMG_SRC_RECIPE+" text" + ");";

    private static final String DATABASE_CREATE_INGREDIENT = "create table "
            + TABLE_INGREDIENT + "(" + COLUMN_ID_INGREDIENT
            + " integer primary key autoincrement, " + COLUMN_INGREDIENT_NAME
            + " text not null );";

    private static final String DATABASE_CREATE_USER_RECIPE= "create table "
            + TABLE_USER_RECIPE + "(" + COLUMN_ID_USER_RECIPE
            + " integer primary key autoincrement, " + COLUMN_ID_USER
            + " integer, "+COLUMN_ID_RECIPE+" integer, FOREIGN KEY ("+COLUMN_ID_USER+") REFERENCES "+TABLE_USER+"("+COLUMN_ID_USER+"), FOREIGN KEY ("+COLUMN_ID_RECIPE+") REFERENCES "+TABLE_RECIPE+"("+COLUMN_ID_RECIPE+")  );";

    private static final String DATABASE_CREATE_RECIPE_INGREDIENT= "create table "
            + TABLE_RECIPE_INGREDIENT + "(" + COLUMN_ID_RECIPE_INGREDIENT
            + " integer primary key autoincrement, " + COLUMN_ID_RECIPE
            + " integer, "+COLUMN_ID_INGREDIENT+" integer,  "+COLUMN_RECIPE_INGREDIENT_MESURE+" text, "+COLUMN_RECIPE_INGREDIENT_QUANTITE+" integer, FOREIGN KEY ("+COLUMN_ID_RECIPE+") REFERENCES "+TABLE_RECIPE+"("+COLUMN_ID_RECIPE+"), FOREIGN KEY ("+COLUMN_ID_INGREDIENT+") REFERENCES "+TABLE_INGREDIENT+"("+COLUMN_ID_INGREDIENT+")  );";

    public MySQLiteHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase database) {
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_RECIPE_INGREDIENT+"");
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_INGREDIENT+"");
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_USER_RECIPE+"");
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_USER+"");
        database.execSQL("DROP TABLE IF EXISTS "+TABLE_RECIPE+"");
        database.execSQL(DATABASE_CREATE_USER);
        database.execSQL(DATABASE_CREATE_RECIPE);
        database.execSQL(DATABASE_CREATE_INGREDIENT);
        database.execSQL(DATABASE_CREATE_USER_RECIPE);
        database.execSQL(DATABASE_CREATE_RECIPE_INGREDIENT);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}