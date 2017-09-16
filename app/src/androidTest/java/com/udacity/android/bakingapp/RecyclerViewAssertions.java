package com.udacity.android.bakingapp;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.common.truth.Truth;

/**
 * Created by hadi on 16/09/17.
 */

public final class RecyclerViewAssertions {
    public static ViewAssertion hasItemsCount(final int count){
        return (View view, NoMatchingViewException noViewFoundException) -> {
                if (!(view instanceof RecyclerView))
                    throw noViewFoundException;

                RecyclerView rv = (RecyclerView) view;
                Truth.assertThat(rv.getAdapter().getItemCount()).isEqualTo(count);
            };

    }
}
