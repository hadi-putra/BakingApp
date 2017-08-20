package com.udacity.android.bakingapp.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.android.bakingapp.data.contract.BaseContract;
import com.udacity.android.bakingapp.data.contract.IngredientContract;
import com.udacity.android.bakingapp.data.contract.RecipeContract;
import com.udacity.android.bakingapp.data.contract.StepContract;

/**
 * Created by hadi on 09/08/17.
 */

public class BakingProvider extends ContentProvider {
    public static final int CODE_RECIPE = 100;
    public static final int CODE_RECIPE_WITH_ID = 101;
    public static final int CODE_RECIPE_INGREDIENT = 200;
    public static final int CODE_RECIPE_STEP = 300;
    public static final int CODE_STEP_WITH_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = BaseContract.AUTHORITY;

        uriMatcher.addURI(authority, RecipeContract.PATH_RECIPE, CODE_RECIPE);
        uriMatcher.addURI(authority, RecipeContract.PATH_RECIPE+"/#", CODE_RECIPE_WITH_ID);
        uriMatcher.addURI(authority, RecipeContract.PATH_RECIPE+"/#/"
                        + IngredientContract.PATH_INGREDIENT, CODE_RECIPE_INGREDIENT);
        uriMatcher.addURI(authority, RecipeContract.PATH_RECIPE+"/#/"+ StepContract.PATH_STEP,
                CODE_RECIPE_STEP);
        uriMatcher.addURI(authority, StepContract.PATH_STEP+"/#", CODE_STEP_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s,
                        @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s,
                      @Nullable String[] strings) {
        return 0;
    }
}
