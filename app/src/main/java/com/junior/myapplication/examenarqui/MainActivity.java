package com.junior.myapplication.examenarqui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.junior.myapplication.examenarqui.controller.categoria.CategoriaController;
import com.junior.myapplication.examenarqui.model.DbHelper;
import com.junior.myapplication.examenarqui.model.categoria.MCategoria;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnCreate;
     EditText categoryNameEditText;
     EditText categoryDescriptionEditText;
     ListView categoriesListView;
     CategoriaController categoryController;
     List<MCategoria> categoriesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        categoryNameEditText = findViewById(R.id.categoryNameEditText);
        categoryDescriptionEditText = findViewById(R.id.categoryDescriptionEditText);
        categoriesListView = findViewById(R.id.categoriesListView);
        categoryController = new CategoriaController(this);
        refreshCategoriesList();
        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MCategoria selectedCategory = (MCategoria) adapterView.getItemAtPosition(i);
                showCategoryDialog(selectedCategory);
            }
        });
    }
    public void addCategory(View view) {
        String categoryName = categoryNameEditText.getText().toString();
        String categoryDescription = categoryDescriptionEditText.getText().toString();
        MCategoria category = new MCategoria(0, categoryName, categoryDescription);
        categoryController.addCategory(category);
        refreshCategoriesList();
    }
    public void showCategoryDialog(final MCategoria category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar categoría");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText nameEditText = new EditText(this);
        nameEditText.setText(category.getName());
        layout.addView(nameEditText);
        final EditText descriptionEditText = new EditText(this);
        descriptionEditText.setText(category.getDescription());
        layout.addView(descriptionEditText);
        builder.setView(layout);
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = nameEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                category.setName(name);
                category.setDescription(description);
                categoryController.updateCategory(category);
                refreshCategoriesList();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                categoryController.deleteCategory(category);
                refreshCategoriesList();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    private void refreshCategoriesList() {
        categoriesList = categoryController.getAllCategories();
        ArrayAdapter<MCategoria> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, categoriesList);
        categoriesListView.setAdapter(adapter);
    }
}
