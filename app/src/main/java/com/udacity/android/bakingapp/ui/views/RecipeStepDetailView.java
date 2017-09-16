package com.udacity.android.bakingapp.ui.views;

import com.udacity.android.bakingapp.data.model.StepModel;

/**
 * Created by hadi on 10/09/17.
 */

public interface RecipeStepDetailView {
    void initData(StepModel step);
    void initPlayer(StepModel mStep);
}
