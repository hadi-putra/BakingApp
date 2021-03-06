package com.udacity.android.bakingapp.presenter;

import com.udacity.android.bakingapp.data.RecipeRepository;
import com.udacity.android.bakingapp.ui.views.RecipeListView;
import com.udacity.android.bakingapp.util.SimpleIdlingResource;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by hadi on 11/08/17.
 */

public class RecipeListPresenter {

    private RecipeListView view;
    private RecipeRepository recipeRepository;
    private final CompositeDisposable compositeDisposable;

    @Inject
    public RecipeListPresenter(RecipeListView view, RecipeRepository recipeRepository) {
        this.view = view;
        this.recipeRepository = recipeRepository;
        compositeDisposable = new CompositeDisposable();
    }

    public void loadRecipe() {
        compositeDisposable.clear();
        compositeDisposable.add(recipeRepository.getRecipeLocal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipeModels -> {
                    view.setRecipes(recipeModels);
                }, throwable -> {
                    throwable.printStackTrace();
                }));
    }


    public void getRemoteRecipe(SimpleIdlingResource idlingResource) {
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }
        compositeDisposable.add(recipeRepository.getRecipeRemote()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(recipeModels -> {
                            view.finishFirstTime();
                            if (idlingResource != null) {
                                idlingResource.setIdleState(true);
                            }
                        },
                        throwable -> throwable.printStackTrace()));
    }

    public void disposeSubscription() {
        compositeDisposable.dispose();
    }
}
