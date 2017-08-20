package com.udacity.android.bakingapp.dagger;

import com.udacity.android.bakingapp.App;
import com.udacity.android.bakingapp.dagger.module.ActivityBuilder;
import com.udacity.android.bakingapp.dagger.module.AppModule;
import com.udacity.android.bakingapp.dagger.module.ContentProviderBuilder;
import com.udacity.android.bakingapp.dagger.module.DataModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * Created by hadi on 09/08/17.
 */
@Singleton
@Component(modules = {AppModule.class, AndroidInjectionModule.class, ActivityBuilder.class,
        ContentProviderBuilder.class, DataModule.class})
public interface AppComponent extends AndroidInjector<App> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App>{}
}
