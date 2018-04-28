package com.wisekiddo.redmart.root;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by ronald on 28/4/18.
 */

@Singleton
@Component(modules = {
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface ApplicationComponent extends AndroidInjector<ApplicationRoot> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        ApplicationComponent.Builder application(Application application);
        ApplicationComponent build();
    }
}