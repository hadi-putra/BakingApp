package com.udacity.android.bakingapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.udacity.android.bakingapp.data.contract.IngredientContract;
import com.udacity.android.bakingapp.data.contract.RecipeContract;
import com.udacity.android.bakingapp.data.contract.StepContract;

/**
 * Created by hadi on 09/08/17.
 */

public class BakingDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "baking_app.db";
    private static final int DATABASE_VERSION = 1;

    public BakingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE_RECIPE =
                "CREATE TABLE IF NOT EXISTS "+ RecipeContract.RecipeEntry.TABLE_NAME +" ("+
                        RecipeContract.RecipeEntry._ID                  +" INTEGER PRIMARY KEY, "+
                        RecipeContract.RecipeEntry.COLUMN_NAME          +" TEXT NOT NULL, "+
                        RecipeContract.RecipeEntry.COLUMN_SERVING       +" INTEGER DEFAULT 0, "+
                        RecipeContract.RecipeEntry.COLUMN_IMAGE         +" TEXT, "+
                        "UNIQUE ("+ RecipeContract.RecipeEntry._ID+") ON CONFLICT REPLACE)";

        final String SQL_CREATE_TABLE_INGREDIENT =
                "CREATE TABLE IF NOT EXISTS "+ IngredientContract.IngredientEntry.TABLE_NAME +" ("+
                        IngredientContract.IngredientEntry._ID                  +" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                        IngredientContract.IngredientEntry.COLUMN_INGREDIENT    +" TEXT NOT NULL, "+
                        IngredientContract.IngredientEntry.COLUMN_RECIPE_ID     +" INTEGER NOT NULL, "+
                        IngredientContract.IngredientEntry.COLUMN_QUANTITY      +" DECIMAL DEFAULT 0, "+
                        IngredientContract.IngredientEntry.COLUMN_MEASURE       +" TEXT)";

        final String SQL_CREATE_TABLE_STEP =
                "CREATE TABLE IF NOT EXISTS "+ StepContract.StepEntry.TABLE_NAME +" ("+
                        StepContract.StepEntry._ID                         +" INTEGER NOT NULL, "+
                        StepContract.StepEntry.COLUMN_DESCRIPTION          +" TEXT, "+
                        StepContract.StepEntry.COLUMN_SHORT_DESCRIPTION    +" TEXT, "+
                        StepContract.StepEntry.COLUMN_THUMBNAIL_URL        +" TEXT, "+
                        StepContract.StepEntry.COLUMN_VIDEO_URL            +" TEXT, "+
                        StepContract.StepEntry.COLUMN_RECIPE_ID            +" INTEGER NOT NULL, "+
                        "UNIQUE ("+ StepContract.StepEntry._ID+", "+ StepContract.StepEntry.COLUMN_RECIPE_ID
                        +") ON CONFLICT REPLACE)";

        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_RECIPE);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_INGREDIENT);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_STEP);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
