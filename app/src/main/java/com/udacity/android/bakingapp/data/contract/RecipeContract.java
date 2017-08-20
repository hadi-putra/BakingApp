package com.udacity.android.bakingapp.data.contract;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hadi on 09/08/17.
 */

public class RecipeContract implements BaseContract {
    public static final String PATH_RECIPE = "recipe";

    public static final class RecipeEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        public static final String TABLE_NAME = "recipe";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SERVING = "serving";
        public static final String COLUMN_IMAGE = "image";

        public static Uri buildRecipeWithId(int id){
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
        }

        public static Uri buildIngredientsUri(int id){
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(id))
                    .appendPath(IngredientContract.PATH_INGREDIENT).build();
        }

        public static Uri buildStepsUri(int id){
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(id))
                    .appendPath(StepContract.PATH_STEP).build();
        }
    }
}
