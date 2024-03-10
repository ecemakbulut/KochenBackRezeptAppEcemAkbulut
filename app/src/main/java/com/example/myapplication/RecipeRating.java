package com.example.myapplication;

public class RecipeRating {
    private String name;
    private float rating;

    public RecipeRating(String name, float rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }
}
