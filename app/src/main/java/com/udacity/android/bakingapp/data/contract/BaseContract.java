package com.udacity.android.bakingapp.data.contract;

import android.net.Uri;

/**
 * Created by hadi on 09/08/17.
 */

public interface BaseContract {
    public static final String AUTHORITY = "com.udacity.android.bakingapp";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
}
