package com.udacity.android.bakingapp.dagger.module;

import com.udacity.android.bakingapp.ui.fragments.RecipeStepDetailFragment;
import com.udacity.android.bakingapp.ui.views.RecipeStepDetailView;

import dagger.Binds;
import dagger.Module;

/**
 * Created by hadi on 10/09/17.
 */
@Module
public abstract class RecipeStepDetailModule {

    @Binds
    abstract RecipeStepDetailView provideRecipeStepDetailView(RecipeStepDetailFragment fragment);
}
