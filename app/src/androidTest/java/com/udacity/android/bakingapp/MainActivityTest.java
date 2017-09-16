package com.udacity.android.bakingapp;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.android.bakingapp.ui.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by hadi on 15/09/17.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private IdlingResource mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        mIdlingResource = mActivityRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void componentMainTest(){
        onView(withId(R.id.container_recipe_list)).check(matches(isDisplayed()));
    }

    @Test
    public void checkData(){
        onView(withText("Nutella Pie")).check(matches(isDisplayed()));
        onView(withId(R.id.container_recipe_list)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));
        onView(withId(R.id.container_recipe_list)).perform(RecyclerViewActions.scrollToPosition(2));
        onView(withText("Yellow Cake")).check(matches(isDisplayed()));
        onView(withId(R.id.container_recipe_list)).perform(RecyclerViewActions.scrollToPosition(3));
        onView(withText("Cheesecake")).check(matches(isDisplayed()));
        onView(withId(R.id.container_recipe_list)).check(RecyclerViewAssertions.hasItemsCount(4));
    }

    @Test
    public void checkBrowniesClick(){
        onView(withId(R.id.container_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.container_recipe_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_recipe_detail)).check(matches(isDisplayed()));

        //check the step 0
        onView(withId(R.id.rv_recipe_detail)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Recipe Introduction")).check(matches(isDisplayed()));

        //click the step 0
        onView(withId(R.id.rv_recipe_detail))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(withId(R.id.container_recipe_step_detail)).check(matches(isDisplayed()));
        onView(withId(R.id.step_description)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }
}
