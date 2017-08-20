package com.udacity.android.bakingapp.dagger.module;

import com.udacity.android.bakingapp.ui.fragments.RecipeListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by hadi on 11/08/17.
 */
@Module
public abstract class RecipeListFragmentProvider {

    @ContributesAndroidInjector(modules = RecipeListModule.class)
    abstract RecipeListFragment bindRecipeListFragment();
}
