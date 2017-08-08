package com.udacity.android.bakingapp;

import android.app.Application;

import com.udacity.android.bakingapp.dagger.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by hadi on 09/08/17.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
    }

    //@Override
    protected AndroidInjector<App> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
