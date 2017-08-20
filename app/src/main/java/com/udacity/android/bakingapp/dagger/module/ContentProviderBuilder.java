package com.udacity.android.bakingapp.dagger.module;

import com.udacity.android.bakingapp.data.BakingProvider;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by hadi on 11/08/17.
 */
@Module
public abstract class ContentProviderBuilder {

    @ContributesAndroidInjector
    abstract BakingProvider bindBakingProvider();
}
