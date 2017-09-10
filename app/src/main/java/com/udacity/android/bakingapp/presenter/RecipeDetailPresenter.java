package com.udacity.android.bakingapp.presenter;

import com.udacity.android.bakingapp.data.RecipeRepositoty;
import com.udacity.android.bakingapp.ui.views.RecipeDetailView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by hadi on 10/09/17.
 */

public class RecipeDetailPresenter {
    private RecipeDetailView view;
    private int recipeId;
    private RecipeRepositoty mRepository;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public RecipeDetailPresenter(RecipeDetailView view, RecipeRepositoty repository) {
        this.view = view;
        this.mRepository = repository;
        compositeDisposable = new CompositeDisposable();
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void getIngredients() {
        compositeDisposable.add(mRepository.getIngredients(recipeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(ingredientModels -> view.setIngredient(ingredientModels),
                        throwable -> throwable.printStackTrace()));
    }

    public void getSteps() {
        compositeDisposable.add(mRepository.getSteps(recipeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stepModels -> view.setStep(stepModels),
                        throwable -> throwable.printStackTrace()));
    }
}
