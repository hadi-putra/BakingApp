package com.udacity.android.bakingapp;

import com.udacity.android.bakingapp.dagger.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by hadi on 09/08/17.
 */

public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<App> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
