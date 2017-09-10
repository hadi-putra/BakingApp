package com.udacity.android.bakingapp.presenter;

import android.os.Parcelable;

import com.udacity.android.bakingapp.data.model.StepModel;
import com.udacity.android.bakingapp.ui.views.RecipeStepDetailView;

import javax.inject.Inject;

/**
 * Created by hadi on 10/09/17.
 */

public class RecipeStepDetailPresenter {

    private RecipeStepDetailView view;
    private StepModel mStep;

    @Inject
    public RecipeStepDetailPresenter(RecipeStepDetailView view) {
        this.view = view;
    }

    public void setStep(StepModel step) {
        this.mStep = step;
    }

    public void initData() {
        view.initData(mStep);
    }
}
