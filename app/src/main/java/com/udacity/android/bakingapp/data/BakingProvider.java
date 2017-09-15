package com.udacity.android.bakingapp.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.udacity.android.bakingapp.data.contract.BaseContract;
import com.udacity.android.bakingapp.data.contract.IngredientContract;
import com.udacity.android.bakingapp.data.contract.RecipeContract;
import com.udacity.android.bakingapp.data.contract.StepContract;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * Created by hadi on 09/08/17.
 */

public class BakingProvider extends ContentProvider {
    public static final int CODE_RECIPE = 100;
    public static final int CODE_RECIPE_WITH_ID = 101;
    public static final int CODE_RECIPE_INGREDIENT = 200;
    public static final int CODE_INGREDIENT = 250;
    public static final int CODE_RECIPE_STEP = 300;
    public static final int CODE_STEP = 350;
    public static final int CODE_STEP_WITH_ID = 301;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = BaseContract.AUTHORITY;

        uriMatcher.addURI(authority, RecipeContract.PATH_RECIPE, CODE_RECIPE);
        uriMatcher.addURI(authority, RecipeContract.PATH_RECIPE+"/#", CODE_RECIPE_WITH_ID);
        uriMatcher.addURI(authority, RecipeContract.PATH_RECIPE+"/#/"
                        + IngredientContract.PATH_INGREDIENT, CODE_RECIPE_INGREDIENT);
        uriMatcher.addURI(authority, IngredientContract.PATH_INGREDIENT, CODE_INGREDIENT);
        uriMatcher.addURI(authority, RecipeContract.PATH_RECIPE+"/#/"+ StepContract.PATH_STEP,
                CODE_RECIPE_STEP);
        uriMatcher.addURI(authority, StepContract.PATH_STEP, CODE_STEP);
        uriMatcher.addURI(authority, StepContract.PATH_STEP+"/#", CODE_STEP_WITH_ID);
        return uriMatcher;
    }

    @Inject BakingDbHelper bakingDbHelper;

    @Override
    public boolean onCreate() {
        AndroidInjection.inject(this);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selections,
                        @Nullable String[] selectionArguments, @Nullable String sortOrder) {

        Cursor cursor;
        SQLiteDatabase sqLiteDatabase = bakingDbHelper.getReadableDatabase();

        switch (sUriMatcher.match(uri)) {

            case CODE_RECIPE: {

                cursor = sqLiteDatabase.query(
                        RecipeContract.RecipeEntry.TABLE_NAME,
                        projection,
                        selections,
                        selectionArguments,
                        null,
                        null,
                        sortOrder);

                break;
            }
            case CODE_RECIPE_INGREDIENT: {
                String recipeIdStr = uri.getPathSegments().get(1);
                try {
                    int recipeId = Integer.parseInt(recipeIdStr);
                    cursor = sqLiteDatabase.query(
                            IngredientContract.IngredientEntry.TABLE_NAME,
                            projection,
                            IngredientContract.IngredientEntry.COLUMN_RECIPE_ID + " = ? ",
                            new String[]{String.valueOf(recipeId)},
                            null, null, sortOrder);
                } catch (NumberFormatException nfe){
                    throw nfe;
                }


                break;
            }
            case CODE_RECIPE_STEP: {
                String recipeIdStr = uri.getPathSegments().get(1);
                try {
                    int recipeId = Integer.parseInt(recipeIdStr);
                    cursor = sqLiteDatabase.query(
                            StepContract.StepEntry.TABLE_NAME,
                            projection,
                            StepContract.StepEntry.COLUMN_RECIPE_ID + " = ? ",
                            new String[]{String.valueOf(recipeId)},
                            null, null, sortOrder);
                } catch (NumberFormatException nfe){
                    throw nfe;
                }


                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
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
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;

        /*
         * If we pass null as the selection to SQLiteDatabase#delete, our entire table will be
         * deleted. However, if we do pass null and delete all of the rows in the table, we won't
         * know how many rows were deleted. According to the documentation for SQLiteDatabase,
         * passing "1" for the selection will delete all rows and return the number of rows
         * deleted, which is what the caller of this method expects.
         */
        if (null == selection) selection = "1";

        switch (sUriMatcher.match(uri)) {

            case CODE_RECIPE:
                numRowsDeleted = bakingDbHelper.getWritableDatabase().delete(
                        RecipeContract.RecipeEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            case CODE_INGREDIENT:
                numRowsDeleted = bakingDbHelper.getWritableDatabase().delete(
                        IngredientContract.IngredientEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            case CODE_STEP:
                numRowsDeleted = bakingDbHelper.getWritableDatabase().delete(
                        StepContract.StepEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /* If we actually deleted any rows, notify that a change has occurred to this URI */
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = bakingDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {

            case CODE_RECIPE:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(RecipeContract.RecipeEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            case CODE_INGREDIENT:
                db.beginTransaction();
                rowsInserted = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(IngredientContract.IngredientEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            case CODE_STEP:
                db.beginTransaction();
                rowsInserted = 0;
                try {
                    for (ContentValues value : values) {

                        long _id = db.insert(StepContract.StepEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }
}
