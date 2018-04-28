package com.wisekiddo.redmart.feature.items;

import com.wisekiddo.redmart.root.ActivityScoped;
import com.wisekiddo.redmart.root.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by ronald on 28/4/18.
 */


@Module
public abstract class ItemsModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract ItemsFragment itemsFragment();

    @ActivityScoped
    @Binds
    abstract ItemsContract.Presenter itemsPresenter(ItemsPresenter presenter);
}