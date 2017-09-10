package com.udacity.android.bakingapp.dagger.module;

import com.udacity.android.bakingapp.ui.fragments.RecipeDetailFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by hadi on 10/09/17.
 */
@Module
public abstract class RecipeDetailFragmentProvider {


    @ContributesAndroidInjector(modules = RecipeDetailModule.class)
    abstract RecipeDetailFragment bindRecipeDetailFragment();
}
