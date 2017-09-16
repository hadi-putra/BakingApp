package com.udacity.android.bakingapp.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import dagger.android.AndroidInjection;

/**
 * Created by hadi on 17/09/17.
 */
@GlideModule
public class BakingGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());
        registry.replace(ThumbnailUrl.class, InputStream.class, new ThumbnailFactory());
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
