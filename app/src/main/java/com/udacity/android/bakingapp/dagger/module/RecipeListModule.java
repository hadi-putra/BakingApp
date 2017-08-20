package com.udacity.android.bakingapp.dagger.module;

import com.udacity.android.bakingapp.ui.fragments.RecipeListFragment;
import com.udacity.android.bakingapp.ui.views.RecipeListView;

import dagger.Binds;
import dagger.Module;

/**
 * Created by hadi on 11/08/17.
 */
@Module
public abstract class RecipeListModule {

    @Binds
    abstract RecipeListView provideRecipeListView(RecipeListFragment fragment);
}
