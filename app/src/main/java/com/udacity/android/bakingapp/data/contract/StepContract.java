package com.udacity.android.bakingapp.data.contract;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by hadi on 09/08/17.
 */

public class StepContract implements BaseContract {
    public static final String PATH_STEP = "step";

    public static final class StepEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_STEP).build();

        public static final String TABLE_NAME = "step";

        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_SHORT_DESCRIPTION = "short_description";
        public static final String COLUMN_VIDEO_URL = "video_url";
        public static final String COLUMN_THUMBNAIL_URL = "thumbnail_url";
        public static final String COLUMN_RECIPE_ID = "recipe_id";

        public static Uri buildStepWithId(int id){
            return CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
        }
    }
}
