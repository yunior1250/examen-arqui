package com.junior.myapplication.examenarqui.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.junior.myapplication.examenarqui.model.categoria.MCategoria;

import java.util.ArrayList;
import java.util.List;


public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "arquitectura.db";
    private static final int DATABASE_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + "categories" + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + "categories");
        onCreate(sqLiteDatabase);

    }

    public void addCategory(MCategoria category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        values.put("description", category.getDescription());
        db.insert("categories", null, values);
        db.close();
    }

    public List<MCategoria> getAllCategories() {
        List<MCategoria> categories = new ArrayList<MCategoria>();
        String query = "SELECT * FROM categories";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                MCategoria category = new MCategoria(id, name, description);
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }

    public void updateCategory(MCategoria category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", category.getName());
        values.put("description", category.getDescription());
        db.update("categories", values, "id = ?", new String[]{String.valueOf(category.getId())});
        db.close();
    }

    public void deleteCategory(MCategoria category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("categories", "id = ?", new String[]{String.valueOf(category.getId())});
        db.close();
    }
}
