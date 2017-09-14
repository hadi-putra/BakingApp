package com.udacity.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.data.model.RecipeModel;
import com.udacity.android.bakingapp.services.IngredientWidgetRemoteVewsService;
import com.udacity.android.bakingapp.services.UpdateWidgetService;
import com.udacity.android.bakingapp.ui.activities.MainActivity;
import com.udacity.android.bakingapp.ui.activities.RecipeDetailActivity;
import com.udacity.android.bakingapp.util.BakingUtil;

import static com.udacity.android.bakingapp.ui.fragments.RecipeListFragment.RECIPE_KEY;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                RecipeModel recipe, int appWidgetId) {


        CharSequence widgetText;
        Intent intent ;
        if (recipe == null){
            intent = new Intent(context, MainActivity.class);
            widgetText = context.getString(R.string.no_recipe_added);
        } else {
            widgetText = String.format("Ingredients of %s", recipe.getName());
            intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra(RECIPE_KEY, recipe);
        }

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.appwidget_recipe_title, widgetText);
        views.setRemoteAdapter(R.id.appwidget_ingredient_list,
                new Intent(context, IngredientWidgetRemoteVewsService.class));



        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.base_widget, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        UpdateWidgetService.startActionUpdateWidget(context);
    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager,
                                          RecipeModel recipe, int[] appWidgetIds){
        for (int appWidgetId : appWidgetIds){
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

