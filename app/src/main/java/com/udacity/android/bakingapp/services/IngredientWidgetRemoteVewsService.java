package com.udacity.android.bakingapp.services;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.android.bakingapp.R;
import com.udacity.android.bakingapp.data.RecipeRepository;
import com.udacity.android.bakingapp.data.model.IngredientModel;
import com.udacity.android.bakingapp.data.model.RecipeModel;
import com.udacity.android.bakingapp.util.BakingUtil;

import java.io.IOException;

/**
 * Created by hadi on 14/09/17.
 */

public class IngredientWidgetRemoteVewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory(getApplicationContext());
    }

    class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory{
        private Context mContext;
        private RecipeModel recipe;

        public ListRemoteViewFactory(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            SharedPreferences preferences = mContext
                    .getSharedPreferences(RecipeRepository.PREF_NAME, Context.MODE_PRIVATE);
            try {
                recipe = BakingUtil
                        .recipeFromJson(preferences.getString(RecipeRepository.PREF_KEY, null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (recipe == null) return 0;
            else if (recipe.getIngredients() == null) return 0;
            return recipe.getIngredients().size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            IngredientModel ingredient = recipe.getIngredients().get(i);
            RemoteViews view = new RemoteViews(mContext.getPackageName(),
                    R.layout.ingredient_item_widget);
            view.setTextViewText(R.id.ingredient_name, ingredient.getIngredient());
            view.setTextViewText(R.id.ingredient_qty,
                    String.format("%.2f %s", ingredient.getQuantity(), ingredient.getMeasure()));
            return view;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
