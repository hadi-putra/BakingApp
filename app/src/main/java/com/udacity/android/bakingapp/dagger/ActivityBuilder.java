package com.udacity.android.bakingapp.dagger;

import com.udacity.android.bakingapp.ui.MainActivity;
import com.udacity.android.bakingapp.ui.RecipeDetailActivity;
import com.udacity.android.bakingapp.ui.RecipeStepDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by hadi on 09/08/17.
 */
@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract RecipeDetailActivity bindRecipeDetailActivity();

    @ContributesAndroidInjector
    abstract RecipeStepDetailActivity bindRecipeStepDetailActivity();
}
