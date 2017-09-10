package com.udacity.android.bakingapp.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.data.model.StepModel;
import com.udacity.android.bakingapp.ui.fragments.RecipeStepDetailFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class RecipeStepDetailActivity extends AppCompatActivity implements HasSupportFragmentInjector {
    private final static String FRAGMENT_STEP_DETAIL_TAG =
            "com.udacity.android.bakingapp.fragment_step_detail_tag";
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_detail);

        StepModel step = getIntent().getParcelableExtra(RecipeStepDetailFragment.STEP_ARGS);
        if (step == null)
            throw new IllegalArgumentException(getString(R.string.step_invalid));

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_STEP_DETAIL_TAG);
        if (fragment == null) {
            fragment = RecipeStepDetailFragment.newInstance(step);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_recipe_step_detail, fragment, FRAGMENT_STEP_DETAIL_TAG)
                    .commit();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
