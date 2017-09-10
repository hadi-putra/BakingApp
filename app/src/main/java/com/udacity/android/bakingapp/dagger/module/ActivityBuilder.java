package com.udacity.android.bakingapp.dagger.module;

import com.udacity.android.bakingapp.ui.activities.MainActivity;
import com.udacity.android.bakingapp.ui.activities.RecipeDetailActivity;
import com.udacity.android.bakingapp.ui.activities.RecipeStepDetailActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by hadi on 09/08/17.
 */
@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = RecipeListFragmentProvider.class)
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {RecipeDetailFragmentProvider.class,
            RecipeStepDetailFragmentProvider.class})
    abstract RecipeDetailActivity bindRecipeDetailActivity();

    @ContributesAndroidInjector(modules = RecipeStepDetailFragmentProvider.class)
    abstract RecipeStepDetailActivity bindRecipeStepDetailActivity();
}
