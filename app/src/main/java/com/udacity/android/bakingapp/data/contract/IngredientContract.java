package com.udacity.android.bakingapp.data.contract;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hadi on 09/08/17.
 */

public class IngredientContract implements BaseContract {
    public static final String PATH_INGREDIENT = "ingredient";

    public static class IngredientEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();

        public static final String TABLE_NAME = "ingredient";

        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_INGREDIENT = "ingredient";
        public static final String COLUMN_QUANTITY = "qty";
        public static final String COLUMN_MEASURE = "measure";
    }

}
