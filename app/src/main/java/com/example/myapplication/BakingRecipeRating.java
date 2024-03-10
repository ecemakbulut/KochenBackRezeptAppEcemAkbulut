package com.example.myapplication;

public class BakingRecipeRating {
    private String name;
    private float rating;

    public BakingRecipeRating(String name, float rating) {
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
