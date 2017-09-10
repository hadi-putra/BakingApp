package com.udacity.android.bakingapp.util;

import android.content.ContentValues;

import com.udacity.android.bakingapp.data.contract.IngredientContract;
import com.udacity.android.bakingapp.data.contract.RecipeContract;
import com.udacity.android.bakingapp.data.contract.StepContract;
import com.udacity.android.bakingapp.data.model.IngredientModel;
import com.udacity.android.bakingapp.data.model.RecipeModel;
import com.udacity.android.bakingapp.data.model.StepModel;

/**
 * Created by hadi on 10/09/17.
 */

public class BakingUtil {

    public static ContentValues recipeToContentValues(RecipeModel recipe){
        ContentValues cv = new ContentValues();
        cv.put(RecipeContract.RecipeEntry._ID, recipe.getId());
        cv.put(RecipeContract.RecipeEntry.COLUMN_NAME, recipe.getName());
        cv.put(RecipeContract.RecipeEntry.COLUMN_IMAGE, recipe.getImage());
        cv.put(RecipeContract.RecipeEntry.COLUMN_SERVING, recipe.getServings());

        return cv;
    }

    public static ContentValues stepToContentValues(StepModel step, int recipeId){
        ContentValues cv = new ContentValues();
        cv.put(StepContract.StepEntry.COLUMN_RECIPE_ID, recipeId);
        cv.put(StepContract.StepEntry._ID, step.getId());
        cv.put(StepContract.StepEntry.COLUMN_DESCRIPTION, step.getDescription());
        cv.put(StepContract.StepEntry.COLUMN_SHORT_DESCRIPTION, step.getShortDescription());
        cv.put(StepContract.StepEntry.COLUMN_THUMBNAIL_URL, step.getThumbnailURL());
        cv.put(StepContract.StepEntry.COLUMN_VIDEO_URL, step.getVideoURL());

        return cv;
    }

    public static ContentValues ingredientToContentValues(IngredientModel ingredient, int recipeId){
        ContentValues cv = new ContentValues();
        cv.put(IngredientContract.IngredientEntry.COLUMN_RECIPE_ID, recipeId);
        cv.put(IngredientContract.IngredientEntry.COLUMN_INGREDIENT, ingredient.getIngredient());
        cv.put(IngredientContract.IngredientEntry.COLUMN_QUANTITY, ingredient.getQuantity());
        cv.put(IngredientContract.IngredientEntry.COLUMN_MEASURE, ingredient.getMeasure());

        return cv;
    }
}
