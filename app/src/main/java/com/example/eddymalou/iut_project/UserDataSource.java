package com.example.eddymalou.iut_project;

/**
 * Created by EddyMalou on 01/05/2017.
 */

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserDataSource {

    // Champs de la base de données
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID_USER,
            MySQLiteHelper.COLUMN_USER_NAME, MySQLiteHelper.COLUMN_PASSWORD };
    private String[] all = { MySQLiteHelper.COLUMN_ID_USER_RECIPE,
            MySQLiteHelper.COLUMN_ID_RECIPE, MySQLiteHelper.COLUMN_ID_USER };

    public UserDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public User createUser(String username, String password) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_USER_NAME, username);
        values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
        long insertId = database.insert(MySQLiteHelper.TABLE_USER, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER,
                allColumns, MySQLiteHelper.COLUMN_ID_USER + " = '" + insertId+"' ", null,
                null, null, null);
        cursor.moveToFirst();
        User newComment = cursorToUser(cursor);
        cursor.close();
        return newComment;
    }

    public List<Recette> getLike(int id_user)
    {
        List<Recette> res = new ArrayList<>();
        Cursor c = database.rawQuery("SELECT * FROM userRecipe usr INNER JOIN recipe rep ON usr._id_user = rep._id_recipe where usr._id_recipe="+String.valueOf(id_user), null);
        if (c != null)
        {
            Log.d("count quer :", String.valueOf(c.getCount()));
            c.moveToFirst();
            Log.d("lol","miam");

            while (!c.isAfterLast()) {
                Recette rec = cursorToRecipe(c);
                Log.d("like",rec.getRecette_name());
                res.add(rec);
                c.moveToNext();
            }
            c.close();

        }


        return res;
    }

    public void like_recette(int id_rec, int id_user)
    {
        ContentValues values = new ContentValues();
        Log.d("insert_like", String.valueOf(id_user));
        Log.d("insert_like", String.valueOf(id_rec));
        values.put(MySQLiteHelper.COLUMN_ID_USER, id_user);
        values.put(MySQLiteHelper.COLUMN_ID_RECIPE, id_rec);
        database.insert(MySQLiteHelper.TABLE_USER_RECIPE, null,
                values);
        Cursor c = database.rawQuery("SELECT * FROM userRecipe where _id_user=?", new String[] {String.valueOf(id_user)});
        c.moveToFirst();
        while (!c.isAfterLast()) {
            Log.d("like_recette", String.valueOf(c.getLong(0)));
            c.moveToNext();
        }
        c.close();

    }

    public void deleteUser(User user) {
        long id = user.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_USER, MySQLiteHelper.COLUMN_ID_USER
                + " = " + id, null);
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            User user = cursorToUser(cursor);
            users.add(user);
            cursor.moveToNext();
        }
        // assurez-vous de la fermeture du curseur
        cursor.close();
        return users;
    }

    public void deleteAll()
    {
        database.delete(MySQLiteHelper.TABLE_USER_RECIPE,null, null);
    }

    public int ifUserExists(String username)
    {
        int res =0;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER,
                allColumns, MySQLiteHelper.COLUMN_USER_NAME + " = '" + username+"'", null,
                null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            res = (int)cursor.getLong(0);
        }

        return res;
    }

    public boolean ifLikeExist(int id_user)
    {
        boolean res =false;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER_RECIPE,
                all, MySQLiteHelper.COLUMN_ID_RECIPE + " = '" + id_user+"'", null,
                null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            res = true;
        }

        return res;
    }

    public int ifUserExists(String username, String password)
    {
        int res =0;
        Cursor cursor = database.query(MySQLiteHelper.TABLE_USER,
                allColumns, MySQLiteHelper.COLUMN_USER_NAME + " =  '"+username+"' AND " +MySQLiteHelper.COLUMN_PASSWORD + " = '"+password+"'",null,
                null, null, null);

        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            res = (int)cursor.getLong(0);
        }

        return res;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setUsername(cursor.getString(1));
        user.setPassword(cursor.getString(2));
        return user;
    }

    private Recette cursorToRecipe(Cursor cursor) {
        Recette rec = new Recette();
        rec.setId(cursor.getInt(cursor.getColumnIndex("_id_recipe")));
        rec.setRecette_name(cursor.getString(cursor.getColumnIndex("recipeName")));
        rec.setImgsrc(cursor.getString(cursor.getColumnIndex("imgSrcRecipe")));
        return rec;
    }
}

