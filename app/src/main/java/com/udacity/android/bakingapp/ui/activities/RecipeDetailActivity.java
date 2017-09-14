package com.udacity.android.bakingapp.ui.activities;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.data.model.StepModel;
import com.udacity.android.bakingapp.ui.fragments.RecipeDetailFragment;
import com.udacity.android.bakingapp.ui.fragments.RecipeListFragment;
import com.udacity.android.bakingapp.ui.fragments.RecipeStepDetailFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeDetailActivity extends AppCompatActivity implements HasSupportFragmentInjector,
        RecipeDetailFragment.RecipeStepsListener {
    private final static String FRAGMENT_DETAIL_TAG = "com.udacity.android.bakingapp.fragment_detail_tag";
    private final static String FRAGMENT_STEP_DETAIL_TAG =
            "com.udacity.android.bakingapp.fragment_step_detail_tag";

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    private boolean isTwoPane;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        isTwoPane = findViewById(R.id.container_recipe_step_detail) != null;


        if (!getIntent().hasExtra(RecipeListFragment.RECIPE_KEY))
            throw new IllegalArgumentException(getString(R.string.recipe_invalid));


        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_DETAIL_TAG);
        if (fragment == null) {
            fragment = RecipeDetailFragment.newInstance(getIntent()
                    .getParcelableExtra(RecipeListFragment.RECIPE_KEY));
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_recipe_detail, fragment, FRAGMENT_DETAIL_TAG)
                    .commit();
        }

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

    @Override
    public boolean isTwoPaneMode() {
        return isTwoPane;
    }

    @Override
    public void setStepSelection(StepModel step) {
        handler.post(()->{
            if (step!= null){
                Fragment fragment = RecipeStepDetailFragment.newInstance(step);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_recipe_step_detail, fragment, FRAGMENT_STEP_DETAIL_TAG)
                        .commit();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
