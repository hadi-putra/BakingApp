package com.udacity.android.bakingapp.dagger.module;

import com.udacity.android.bakingapp.ui.fragments.RecipeDetailFragment;
import com.udacity.android.bakingapp.ui.views.RecipeDetailView;

import dagger.Binds;
import dagger.Module;

/**
 * Created by hadi on 10/09/17.
 */

@Module
public abstract class RecipeDetailModule {

    @Binds
    abstract RecipeDetailView provideRecipeDetailView(RecipeDetailFragment fragment);
}
