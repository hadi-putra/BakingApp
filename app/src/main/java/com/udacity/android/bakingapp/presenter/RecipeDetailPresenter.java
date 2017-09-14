package com.udacity.android.bakingapp.presenter;

import com.udacity.android.bakingapp.data.RecipeRepository;
import com.udacity.android.bakingapp.data.model.RecipeModel;
import com.udacity.android.bakingapp.ui.views.RecipeDetailView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by hadi on 10/09/17.
 */

public class RecipeDetailPresenter {
    private RecipeDetailView view;
    private RecipeModel recipe;
    private RecipeRepository mRepository;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public RecipeDetailPresenter(RecipeDetailView view, RecipeRepository repository) {
        this.view = view;
        this.mRepository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    public void setRecipe(RecipeModel recipe) {
        this.recipe = recipe;
    }

    public void getIngredients() {
        compositeDisposable.add(mRepository.getIngredients(recipe.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientModels -> {
                    recipe.setIngredients(ingredientModels);
                    view.setIngredient(ingredientModels);
                }, throwable -> throwable.printStackTrace()));
    }

    public void getSteps() {
        compositeDisposable.add(mRepository.getSteps(recipe.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stepModels -> {
                            recipe.setSteps(stepModels);
                            view.setStep(stepModels);
                        },
                        throwable -> throwable.printStackTrace()));
    }

    public void disposeSubscription() {
        compositeDisposable.dispose();
    }

    public void setTitle() {
        view.setRecipeTitle(recipe.getName());
    }

    public void addRecipeToWidget() {
        view.addToWidget(recipe);
    }
}
