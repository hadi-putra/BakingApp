package com.udacity.android.bakingapp.ui.views;

import com.udacity.android.bakingapp.data.model.RecipeModel;

import java.util.List;

/**
 * Created by hadi on 11/08/17.
 */

public interface RecipeListView {
    void setRecipes(List<RecipeModel> recipeModels);
    void finishFirstTime();
}
