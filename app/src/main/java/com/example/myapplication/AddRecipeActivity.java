package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private EditText editTextRecipeName, editTextIngredients, editTextPreparation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        dbHelper = new DatabaseHelper(this);

        editTextRecipeName = findViewById(R.id.editTextRecipeName);
        editTextIngredients = findViewById(R.id.editTextIngredients);
        editTextPreparation = findViewById(R.id.editTextPreparation);

        Button buttonAddRecipe = findViewById(R.id.buttonAddRecipe);
        buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRecipeToDatabase();
            }
        });

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRecipeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addRecipeToDatabase() {
        String recipeName = editTextRecipeName.getText().toString().trim();
        String ingredients = editTextIngredients.getText().toString().trim();
        String preparation = editTextPreparation.getText().toString().trim();

        if (!recipeName.isEmpty() && !ingredients.isEmpty() && !preparation.isEmpty()) {
            long result = dbHelper.addRecipe(recipeName, ingredients, preparation, "0");

            if (result != -1) {
                Toast.makeText(this, "Rezept erfolgreich hinzugefügt", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddRecipeActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Fehler beim Hinzufügen des Rezepts", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Bitte füllen Sie alle Felder aus", Toast.LENGTH_SHORT).show();
        }
    }
}
