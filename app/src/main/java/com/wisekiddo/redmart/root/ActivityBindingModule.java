package com.wisekiddo.redmart.root;

import com.wisekiddo.redmart.feature.items.ItemsActivity;
import com.wisekiddo.redmart.feature.items.ItemsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by ronald on 28/4/18.
 */

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = ItemsModule.class)
    abstract ItemsActivity itemsActivity();

}