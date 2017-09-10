package com.udacity.android.bakingapp.dagger.module;

import com.udacity.android.bakingapp.ui.fragments.RecipeStepDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by hadi on 10/09/17.
 */
@Module
public abstract class RecipeStepDetailFragmentProvider {

    @ContributesAndroidInjector(modules = RecipeStepDetailModule.class)
    abstract RecipeStepDetailFragment bindRecipeStepDetailFragment();
}
