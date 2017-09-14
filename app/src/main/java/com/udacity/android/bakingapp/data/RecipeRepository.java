package com.udacity.android.bakingapp.data;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.squareup.sqlbrite2.BriteContentResolver;
import com.squareup.sqlbrite2.SqlBrite;
import com.udacity.android.bakingapp.data.contract.IngredientContract;
import com.udacity.android.bakingapp.data.contract.RecipeContract;
import com.udacity.android.bakingapp.data.contract.StepContract;
import com.udacity.android.bakingapp.data.model.IngredientModel;
import com.udacity.android.bakingapp.data.model.RecipeModel;
import com.udacity.android.bakingapp.data.model.StepModel;
import com.udacity.android.bakingapp.util.BakingUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hadi on 10/09/17.
 */

public class RecipeRepository {
    public static final String PREF_NAME = "RecipeWidget";
    public static final String PREF_KEY = "RecipeInfo";

    private final BakingApi bakingApi;
    private final ContentResolver contentResolver;
    private final BriteContentResolver briteContentResolver;
    private final SharedPreferences mPreference;

    public static final String[] RECIPE_PROJECTION = new String[] {
            RecipeContract.RecipeEntry._ID,
            RecipeContract.RecipeEntry.COLUMN_NAME,
            RecipeContract.RecipeEntry.COLUMN_SERVING,
            RecipeContract.RecipeEntry.COLUMN_IMAGE
    };

    public static final String[] INGREDIENT_PROJECTION = new String[] {
            IngredientContract.IngredientEntry.COLUMN_INGREDIENT,
            IngredientContract.IngredientEntry.COLUMN_QUANTITY,
            IngredientContract.IngredientEntry.COLUMN_MEASURE
    };

    public static final String[] STEP_PROJECTION = new String[] {
            StepContract.StepEntry._ID,
            StepContract.StepEntry.COLUMN_SHORT_DESCRIPTION,
            StepContract.StepEntry.COLUMN_DESCRIPTION,
            StepContract.StepEntry.COLUMN_THUMBNAIL_URL,
            StepContract.StepEntry.COLUMN_VIDEO_URL,
    };

    public static final int INDEX_RECIPE_ID = 0;
    public static final int INDEX_RECIPE_NAME = 1;
    public static final int INDEX_RECIPE_SERVING = 2;
    public static final int INDEX_RECIPE_IMAGE = 3;

    public static final int INDEX_INGREDIENT_NAME = 0;
    public static final int INDEX_INGREDIENT_QUANTITY = 1;
    public static final int INDEX_INGREDIENT_MEASURE = 2;

    public static final int INDEX_STEP_ID = 0;
    public static final int INDEX_STEP_SHORT_DESCR = 1;
    public static final int INDEX_STEP_DESCR = 2;
    public static final int INDEX_STEP_THUMB_URL = 3;
    public static final int INDEX_STEP_VID_URL = 4;


    public RecipeRepository(BakingApi bakingApi, BriteContentResolver briteContentResolver,
                            ContentResolver contentResolver, SharedPreferences preferences) {
        this.bakingApi = bakingApi;
        this.briteContentResolver = briteContentResolver;
        this.contentResolver = contentResolver;
        this.mPreference = preferences;
    }

    public Observable<List<RecipeModel>> getRecipeRemote() {
        return bakingApi.getRecipeList()
                .observeOn(Schedulers.computation())
                .doOnNext(this::saveRecipe)
                .subscribeOn(Schedulers.io());
    }

    private void saveRecipe(List<RecipeModel> recipeModels) {

        List<ContentValues> recipeCv = new ArrayList<>();
        List<ContentValues> stepCv = new ArrayList<>();
        List<ContentValues> ingredientCv = new ArrayList<>();
        for (RecipeModel recipe : recipeModels){
            recipeCv.add(BakingUtil.recipeToContentValues(recipe));
            for (StepModel step : recipe.getSteps()){
                stepCv.add(BakingUtil.stepToContentValues(step, recipe.getId()));
            }
            for (IngredientModel ingredient : recipe.getIngredients()){
                ingredientCv.add(BakingUtil.ingredientToContentValues(ingredient, recipe.getId()));
            }
        }


        contentResolver.bulkInsert(RecipeContract.RecipeEntry.CONTENT_URI,
                recipeCv.toArray(new ContentValues[recipeCv.size()]));
        contentResolver.bulkInsert(StepContract.StepEntry.CONTENT_URI,
                stepCv.toArray(new ContentValues[stepCv.size()]));
        contentResolver.bulkInsert(IngredientContract.IngredientEntry.CONTENT_URI,
                ingredientCv.toArray(new ContentValues[ingredientCv.size()]));
    }

    public Observable<List<RecipeModel>> getRecipeLocal() {
        return briteContentResolver.createQuery(RecipeContract.RecipeEntry.CONTENT_URI,
                RECIPE_PROJECTION, null, null, null, true)
                .map(PROJECTION_RECIPE)
                .subscribeOn(Schedulers.io());
    }

    private Function<SqlBrite.Query, List<RecipeModel>> PROJECTION_RECIPE = query -> {
        List<RecipeModel> recipes = new ArrayList<>();
        Cursor cursor = query.run();
        if (cursor != null){
            while (cursor.moveToNext()){
                RecipeModel recipe = new RecipeModel();
                recipe.setId(cursor.getInt(INDEX_RECIPE_ID));
                recipe.setName(cursor.getString(INDEX_RECIPE_NAME));
                recipe.setServings(cursor.getInt(INDEX_RECIPE_SERVING));
                recipe.setImage(cursor.getString(INDEX_RECIPE_IMAGE));

                recipes.add(recipe);
            }
            cursor.close();
        }

        return recipes;
    };

    public Observable<List<IngredientModel>> getIngredients(int recipeId) {
        return briteContentResolver.createQuery(RecipeContract.RecipeEntry.buildIngredientsUri(recipeId),
                INGREDIENT_PROJECTION, null, null, null, true)
                .map(PROJECTION_INGREDIENT)
                .subscribeOn(Schedulers.io());
    }

    private Function<SqlBrite.Query, List<IngredientModel>> PROJECTION_INGREDIENT = query -> {
        List<IngredientModel> ingredients = new ArrayList<>();
        Cursor cursor = query.run();
        if (cursor != null){
            while (cursor.moveToNext()){
                IngredientModel ingredient = new IngredientModel();
                ingredient.setIngredient(cursor.getString(INDEX_INGREDIENT_NAME));
                ingredient.setQuantity(cursor.getFloat(INDEX_INGREDIENT_QUANTITY));
                ingredient.setMeasure(cursor.getString(INDEX_INGREDIENT_MEASURE));

                ingredients.add(ingredient);
            }
            cursor.close();
        }

        return ingredients;
    };

    public Observable<List<StepModel>> getSteps(int recipeId) {
        return briteContentResolver.createQuery(RecipeContract.RecipeEntry.buildStepsUri(recipeId),
                STEP_PROJECTION, null, null, null, true)
                .map(PROJECTION_STEP)
                .subscribeOn(Schedulers.io());
    }

    private Function<SqlBrite.Query, List<StepModel>> PROJECTION_STEP = query -> {
        List<StepModel> steps = new ArrayList<>();
        Cursor cursor = query.run();
        if (cursor != null){
            while (cursor.moveToNext()){
                StepModel step = new StepModel();
                step.setId(cursor.getInt(INDEX_STEP_ID));
                step.setShortDescription(cursor.getString(INDEX_STEP_SHORT_DESCR));
                step.setDescription(cursor.getString(INDEX_STEP_DESCR));
                step.setThumbnailURL(cursor.getString(INDEX_STEP_THUMB_URL));
                step.setVideoURL(cursor.getString(INDEX_STEP_VID_URL));

                steps.add(step);
            }
            cursor.close();
        }

        return steps;
    };

    public void addRecipeToWidget(RecipeModel recipeModel) {
        SharedPreferences.Editor editor = mPreference.edit();
        editor.putString(PREF_KEY, BakingUtil.recipeToJson(recipeModel));
        editor.apply();
    }

    public RecipeModel getRecipeForWidget() throws IOException {
        return BakingUtil.recipeFromJson(mPreference.getString(PREF_KEY, null));
    }
}
