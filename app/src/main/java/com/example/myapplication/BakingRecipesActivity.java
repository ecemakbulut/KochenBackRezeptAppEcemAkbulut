package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
public class BakingRecipesActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        dbHelper = new DatabaseHelper(this);

        String[] recipes = dbHelper.getAllBakingRecipeNames();
        List<RecipeRating> recipeRatings = new ArrayList<>();
        for (String recipe : recipes) {
            float rating = Float.parseFloat(dbHelper.getRatingForBakingRecipe(recipe));
            recipeRatings.add(new RecipeRating(recipe, rating));
        }

        Collections.sort(recipeRatings, new Comparator<RecipeRating>() {
            @Override
            public int compare(RecipeRating r1, RecipeRating r2) {
                return Float.compare(r2.getRating(), r1.getRating());
            }
        });

        ArrayAdapter<RecipeRating> adapter = new ArrayAdapter<RecipeRating>(this, android.R.layout.simple_list_item_1, recipeRatings) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view.findViewById(android.R.id.text1);

                RecipeRating recipeRating = recipeRatings.get(position);
                textView.setText(recipeRating.getName() + " - " + recipeRating.getRating());
                return view;
            }
        };

        ListView listViewRecipes = findViewById(R.id.listViewRecipes);
        listViewRecipes.setAdapter(adapter);

        listViewRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedRecipe = recipeRatings.get(position).getName();
                openIngredientsActivity(selectedRecipe);
            }
        });

        Button buttonBack = findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BakingRecipesActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


        Button buttonAddRecipe = findViewById(R.id.buttonAddRecipe);
        buttonAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BakingRecipesActivity.this, AddBakingRecipeActivity.class);
                startActivity(intent);
            }
        });


    }

    private void openIngredientsActivity(String recipeName) {
        Log.d("RecipesActivity", "openIngredientsActivity: recipeName=" + recipeName);
        Intent intent = new Intent(this, BakingIngredientsActivity.class);
        intent.putExtra(IngredientsActivity.EXTRA_RECIPE_NAME, recipeName);
        startActivity(intent);
    }
}
