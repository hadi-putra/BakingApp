package com.udacity.android.bakingapp.util;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by hadi on 15/09/17.
 */

public class SimpleIdlingResource implements IdlingResource {
    @Nullable
    private volatile ResourceCallback resourceCallback;

    private AtomicBoolean isIdle = new AtomicBoolean(true);
    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdle.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        resourceCallback = callback;
    }

    public void setIdleState(boolean isIdle){
        this.isIdle.set(isIdle);
        if (isIdle && resourceCallback != null)
            resourceCallback.onTransitionToIdle();
    }
}
