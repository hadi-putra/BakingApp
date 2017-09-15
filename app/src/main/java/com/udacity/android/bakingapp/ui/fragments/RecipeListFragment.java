package com.udacity.android.bakingapp.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.data.model.RecipeModel;
import com.udacity.android.bakingapp.presenter.RecipeListPresenter;
import com.udacity.android.bakingapp.ui.activities.MainActivity;
import com.udacity.android.bakingapp.ui.activities.RecipeDetailActivity;
import com.udacity.android.bakingapp.ui.adapter.RecipeItemAdapter;
import com.udacity.android.bakingapp.ui.views.RecipeListView;
import com.udacity.android.bakingapp.util.SimpleIdlingResource;


import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeListFragment extends Fragment implements RecipeListView, RecipeItemAdapter.OnItemClickListener {
    private static final String GRID_STATE_KEY = "com.udacity.android.bakingapp.grid_state";
    private static final String FIRST_TIME_KEY = "com.udacity.android.bakingapp.first_time";
    public static final String RECIPE_KEY = "com.udacity.android.bakingapp.recipe";

    @BindView(R.id.rv_recipes) RecyclerView mGridRecipe;
    private RecipeItemAdapter mRecipeItemAdapter;
    @Inject RecipeListPresenter mPresenter;
    private GridLayoutManager mGridLayoutManager;
    private Parcelable mStateGrid;
    private boolean isAlreadyDownloaded;
    private Unbinder unbinder;

    public RecipeListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        mGridLayoutManager = new GridLayoutManager(getActivity(), getResources()
                .getInteger(R.integer.grid_col),
                GridLayoutManager.VERTICAL, false);

        mGridRecipe.setLayoutManager(mGridLayoutManager);
        mGridRecipe.setHasFixedSize(true);
        mRecipeItemAdapter = new RecipeItemAdapter(this);
        mGridRecipe.setAdapter(mRecipeItemAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            mStateGrid = savedInstanceState.getParcelable(GRID_STATE_KEY);
        }

        mPresenter.loadRecipe();

        isAlreadyDownloaded = getActivity().getPreferences(Context.MODE_PRIVATE)
                .getBoolean(FIRST_TIME_KEY, false);
        if (!isAlreadyDownloaded) {
            SimpleIdlingResource idlingResource = (
                    SimpleIdlingResource)((MainActivity)getActivity()).getIdlingResource();
            mPresenter.getRemoteRecipe(idlingResource);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mStateGrid = mGridLayoutManager.onSaveInstanceState();
        outState.putParcelable(GRID_STATE_KEY, mStateGrid);
    }

    @Override
    public void onDestroyView() {
        mPresenter.disposeSubscription();
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void setRecipes(List<RecipeModel> recipeModels) {
        mRecipeItemAdapter.setRecipes(recipeModels);
        if (mStateGrid != null){
            mGridLayoutManager.onRestoreInstanceState(mStateGrid);
        }
    }

    @Override
    public void finishFirstTime() {
        isAlreadyDownloaded = true;
        SharedPreferences.Editor editor = getActivity().getPreferences(Context.MODE_PRIVATE).edit();
        editor.putBoolean(FIRST_TIME_KEY, isAlreadyDownloaded);
        editor.apply();
    }

    @Override
    public void onItemClick(RecipeModel recipe) {
        Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
        intent.putExtra(RECIPE_KEY, recipe);

        startActivity(intent);
    }
}
