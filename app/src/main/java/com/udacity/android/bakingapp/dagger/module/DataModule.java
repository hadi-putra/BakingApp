package com.udacity.android.bakingapp.dagger.module;

import android.content.ContentResolver;
import android.content.Context;

import com.udacity.android.bakingapp.data.BakingDbHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hadi on 11/08/17.
 */
@Module
public class DataModule {

    @Singleton
    @Provides
    BakingDbHelper provideBakingDbHelper(Context context){
        return new BakingDbHelper(context);
    }

    @Singleton
    @Provides
    ContentResolver provideContentResolver(Context context){
        return context.getContentResolver();
    }
}
