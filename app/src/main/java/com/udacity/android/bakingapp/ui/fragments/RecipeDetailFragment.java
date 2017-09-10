package com.udacity.android.bakingapp.ui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.data.model.IngredientModel;
import com.udacity.android.bakingapp.data.model.StepModel;
import com.udacity.android.bakingapp.presenter.RecipeDetailPresenter;
import com.udacity.android.bakingapp.ui.activities.RecipeStepDetailActivity;
import com.udacity.android.bakingapp.ui.adapter.RecipeDetailAdapter;
import com.udacity.android.bakingapp.ui.views.RecipeDetailView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailFragment extends Fragment implements RecipeDetailView,
        RecipeDetailAdapter.OnStepItemClickListener {
    private static final String LIST_STATE_KEY = "com.udacity.android.bakingapp.list_state";

    @Inject RecipeDetailPresenter mPresenter;
    @BindView(R.id.rv_recipe_detail) RecyclerView mRVRecipeDetail;
    private RecipeDetailAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private Parcelable mListState;
    private RecipeStepsListener mStepClickCallback;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }

    public static RecipeDetailFragment newInstance(int recipeId){
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        Bundle args = new Bundle();
        args.putInt(RecipeListFragment.RECIPE_ID_KEY, recipeId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRVRecipeDetail.setLayoutManager(mLinearLayoutManager);
        mRVRecipeDetail.setHasFixedSize(true);

        mAdapter = new RecipeDetailAdapter(this);
        mRVRecipeDetail.setAdapter(mAdapter);

        Bundle args = getArguments();
        if (args != null) {
            mPresenter.setRecipeId(args.getInt(RecipeListFragment.RECIPE_ID_KEY));
            mPresenter.getIngredients();
            mPresenter.getSteps();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null){
            mListState = savedInstanceState.getParcelable(LIST_STATE_KEY);
        }
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
        if (context instanceof RecipeStepsListener){
            mStepClickCallback = (RecipeStepsListener) context;
        } else {
            throw new IllegalArgumentException("Activity must implement RecipeStepListener");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mListState = mLinearLayoutManager.onSaveInstanceState();
        outState.putParcelable(LIST_STATE_KEY, mListState);
    }

    @Override
    public void setIngredient(List<IngredientModel> ingredientModels) {
        mAdapter.setIngredients(ingredientModels);
    }

    @Override
    public void setStep(List<StepModel> stepModels) {
        mAdapter.setSteps(stepModels);
        if (mListState != null)
            mLinearLayoutManager.onRestoreInstanceState(mListState);
    }

    @Override
    public void onStepClick(StepModel step) {
        if (mStepClickCallback.isTwoPaneMode()){
            mStepClickCallback.setStepSelection(step);
        } else {
            Intent destination = new Intent(getContext(), RecipeStepDetailActivity.class);
            destination.putExtra(RecipeStepDetailFragment.STEP_ARGS, step);
            startActivity(destination);
        }
    }

    public interface RecipeStepsListener{
        boolean isTwoPaneMode();
        void setStepSelection(StepModel step);
    }
}
