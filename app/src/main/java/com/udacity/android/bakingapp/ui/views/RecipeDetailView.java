package com.udacity.android.bakingapp.ui.views;

import com.udacity.android.bakingapp.data.model.IngredientModel;
import com.udacity.android.bakingapp.data.model.RecipeModel;
import com.udacity.android.bakingapp.data.model.StepModel;

import java.util.List;

/**
 * Created by hadi on 10/09/17.
 */

public interface RecipeDetailView {
    void setIngredient(List<IngredientModel> ingredientModels);

    void setStep(List<StepModel> stepModels);

    void setRecipeTitle(String name);

    void addToWidget(RecipeModel recipe);
}
