package com.example.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import androidx.appcompat.app.AppCompatActivity;

public class BakingRecipeRatingActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_NAME = "extra_recipe_name";
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_rating);

        String recipeName = getIntent().getStringExtra(EXTRA_RECIPE_NAME);

        setTitle("Bewerten Sie " + recipeName);

        ratingBar = findViewById(R.id.ratingBar);

        Button buttonSubmitRating = findViewById(R.id.buttonSubmitRating);
        buttonSubmitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitRating(recipeName);
            }
        });

        // Logout-Button hinzufügen
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void submitRating(String recipeName) {
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        float rating = ratingBar.getRating();
        long recipeId = dbHelper.getBakingRecipeIdByName(recipeName);


        dbHelper.rateBakingRecipe(recipeName, rating);

        Intent intent = new Intent(this, BakingRecipesActivity.class);
        startActivity(intent);
    }

    private void logout() {
        SharedPreferences preferences = getSharedPreferences("MY_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("CURRENT_USER", null);
        editor.apply();

        Intent intent = new Intent(BakingRecipeRatingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}