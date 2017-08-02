package com.udacity.android.bakingapp.data;

/**
 * Created by msk-1196 on 7/29/17.
 */

public class IngredientModel {
    private float quatity;
    private String measure;
    private String ingredient;

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
