package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class BakingIngredientsActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_NAME = "extra_recipe_name";
    private ArrayAdapter<String> ingredientsAdapter;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        TextView textViewTitle = findViewById(R.id.textViewTitle);
        ListView listViewIngredients = findViewById(R.id.listViewIngredients);
        ingredientsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listViewIngredients.setAdapter(ingredientsAdapter);
        String recipeName = getIntent().getStringExtra(EXTRA_RECIPE_NAME);

        if (getIntent().hasExtra(EXTRA_RECIPE_NAME)) {

            textViewTitle.setText(recipeName + " Zutaten und Zubereitung");

            displayIngredientsAndPreparation(recipeName);
        } else {
            finish();
        }

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Button buttonNext = findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BakingIngredientsActivity.this, BakingRecipeRatingActivity.class);

                intent.putExtra(BakingRecipeRatingActivity.EXTRA_RECIPE_NAME, recipeName);
                startActivity(intent);

            }
        });

        Button buttonDeleteRecipe = findViewById(R.id.buttonDeleteRecipe);
        buttonDeleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteBakingRecipe(recipeName);

                Intent intent = new Intent(BakingIngredientsActivity.this, BakingRecipesActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void displayIngredientsAndPreparation(String recipeName) {
        dbHelper = new DatabaseHelper(this);

        String recipe = dbHelper.getIngredientsAndPreparationForBakingRecipe(recipeName);
        String[] ingredients = recipe.split("\n");
        ingredientsAdapter.clear();
        ingredientsAdapter.addAll(ingredients);

        String rating = dbHelper.getRatingForBakingRecipe(recipeName);
        TextView textViewRating = findViewById(R.id.textViewRating);
        textViewRating.setText("Rating: " + dbHelper.getRatingForBakingRecipe(recipeName) + " / 5 ");
    }


}
