package com.udacity.android.bakingapp.dagger.module;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

import com.squareup.sqlbrite2.BriteContentResolver;
import com.squareup.sqlbrite2.SqlBrite;
import com.udacity.android.bakingapp.App;
import com.udacity.android.bakingapp.data.BakingApi;
import com.udacity.android.bakingapp.data.BakingDbHelper;
import com.udacity.android.bakingapp.data.RecipeRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by hadi on 11/08/17.
 */
@Module
public class DataModule {

    @Singleton
    @Provides
    BakingDbHelper provideBakingDbHelper(Context context){
        return new BakingDbHelper(context);
    }

    @Singleton
    @Provides
    SqlBrite provideSqlBrite(Context context){
        return new SqlBrite.Builder().build();
    }

    @Singleton
    @Provides
    BriteContentResolver provideBriteContentResolver(SqlBrite sqlBrite, ContentResolver contentResolver) {
        return sqlBrite.wrapContentProvider(contentResolver, Schedulers.io());
    }

    @Singleton
    @Provides
    ContentResolver provideContentResolver(Context context){
        return context.getContentResolver();
    }

    @Singleton
    @Provides
    RecipeRepository provideRecipeRepository(BakingApi bakingApi, BriteContentResolver briteContentResolver,
                                             ContentResolver contentResolver, SharedPreferences preferences){
        return new RecipeRepository(bakingApi, briteContentResolver, contentResolver, preferences);
    }

    @Singleton
    @Provides
    SharedPreferences provideSharedPreferences(App app){
        return app.getSharedPreferences(RecipeRepository.PREF_NAME, Context.MODE_PRIVATE);
    }
}
