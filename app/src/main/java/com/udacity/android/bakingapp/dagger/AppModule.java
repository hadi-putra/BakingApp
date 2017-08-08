package com.udacity.android.bakingapp.dagger;

import android.content.Context;

import com.udacity.android.bakingapp.App;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by hadi on 09/08/17.
 */
@Module
public class AppModule {
    private static final String URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Singleton
    @Provides
    Context provideContext(App app){
        return app.getApplicationContext();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder(Context context){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);

        return builder;
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient.Builder builder){
        return new Retrofit.Builder()
                .baseUrl(URL)
                .client(builder.build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
