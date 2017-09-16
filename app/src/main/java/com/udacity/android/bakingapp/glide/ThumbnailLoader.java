package com.udacity.android.bakingapp.glide;

import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.signature.ObjectKey;

import java.io.InputStream;

/**
 * Created by hadi on 17/09/17.
 */

public class ThumbnailLoader implements ModelLoader<ThumbnailUrl, InputStream> {
    @Nullable
    @Override
    public LoadData<InputStream> buildLoadData(ThumbnailUrl thumbnailUrl, int width, int height, Options options) {
        return new ModelLoader.LoadData<InputStream>(new ObjectKey(thumbnailUrl), new ThumbnailFetcher(thumbnailUrl));
    }

    @Override
    public boolean handles(ThumbnailUrl thumbnailUrl) {
        return true;
    }
}
