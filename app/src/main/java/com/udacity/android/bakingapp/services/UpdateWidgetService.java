package com.udacity.android.bakingapp.services;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.data.RecipeRepository;
import com.udacity.android.bakingapp.data.model.RecipeModel;
import com.udacity.android.bakingapp.widget.BakingAppWidget;

import java.io.IOException;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 *
 * helper methods.
 */
public class UpdateWidgetService extends IntentService {
    public static final String ACTION_UPDATE_RECIPE =
            "com.udacity.android.bakingapp.update_recipe";

    public static final String ACTION_UPDATE_WIDGET =
            "com.udacity.android.bakingapp.update_widget";

    public static final String RECIPE_KEY = "com.udacity.android.bakingapp.recipe_key";

    @Inject RecipeRepository recipeRepository;

    public UpdateWidgetService() {
        super("UpdateWidgetService");
    }

    @Override
    public void onCreate() {
        AndroidInjection.inject(this);
        super.onCreate();
    }

    /**
     * Starts this service to perform action Update Recipe Widget with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionUpdateRecipe(Context context, RecipeModel recipeModel) {
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE);
        intent.putExtra(RECIPE_KEY, recipeModel);
        context.startService(intent);
    }

    public static void startActionUpdateWidget(Context context) {
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPE.equals(action)) {
                RecipeModel recipe = intent.getParcelableExtra(RECIPE_KEY);
                handleActionUpdateRecipe(recipe);
            } else if (ACTION_UPDATE_WIDGET.equals(action)){
                handleActionUpdateWidget();
            }
        }
    }

    private void handleActionUpdateWidget() {
        RecipeModel recipe = null;
        try {
            recipe = recipeRepository.getRecipeForWidget();
        } catch (IOException e) {
            e.printStackTrace();
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, BakingAppWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.appwidget_ingredient_list);

        BakingAppWidget.updateRecipeWidget(this, appWidgetManager, recipe, appWidgetIds);
    }


    private void handleActionUpdateRecipe(RecipeModel recipe) {
        recipeRepository.addRecipeToWidget(recipe);
        startActionUpdateWidget(this);
    }
}
