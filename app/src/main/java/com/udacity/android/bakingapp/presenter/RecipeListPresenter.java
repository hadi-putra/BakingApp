package com.udacity.android.bakingapp.presenter;

import android.util.Log;

import com.udacity.android.bakingapp.data.RecipeRepositoty;
import com.udacity.android.bakingapp.ui.views.RecipeListView;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by hadi on 11/08/17.
 */

public class RecipeListPresenter {

    private RecipeListView view;
    private RecipeRepositoty recipeRepositoty;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public RecipeListPresenter(RecipeListView view, RecipeRepositoty recipeRepositoty) {
        this.view = view;
        this.recipeRepositoty = recipeRepositoty;
        compositeDisposable = new CompositeDisposable();
    }

    public void loadRecipe() {
        compositeDisposable.clear();
        compositeDisposable.add(recipeRepositoty.getRecipeLocal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipeModels -> {
                    Log.e(getClass().getSimpleName(), recipeModels.size()+"");
                    view.setRecipes(recipeModels);
                }, throwable -> {
                    throwable.printStackTrace();
                }));
    }


    public void getRemoteRecipe() {
        compositeDisposable.add(recipeRepositoty.getRecipeRemote()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipeModels -> view.finishFirstTime(),
                        throwable -> throwable.printStackTrace()));
    }
}
