package com.wisekiddo.redmart.root;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by ronald on 28/4/18.
 */

public class ApplicationRoot extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
    }
}
