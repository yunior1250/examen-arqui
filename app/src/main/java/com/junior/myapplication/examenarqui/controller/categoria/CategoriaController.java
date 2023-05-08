package com.junior.myapplication.examenarqui.controller.categoria;

import android.content.Context;

import com.junior.myapplication.examenarqui.model.DbHelper;
import com.junior.myapplication.examenarqui.model.categoria.MCategoria;

import java.util.List;

public class CategoriaController {
    private DbHelper dbHelper;

    public CategoriaController(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void addCategory(MCategoria category) {
        dbHelper.addCategory(category);
    }

    public List<MCategoria> getAllCategories() {
        return dbHelper.getAllCategories();
    }

    public void updateCategory(MCategoria category) {
        dbHelper.updateCategory(category);
    }

    public void deleteCategory(MCategoria category) {
        dbHelper.deleteCategory(category);
    }
}
