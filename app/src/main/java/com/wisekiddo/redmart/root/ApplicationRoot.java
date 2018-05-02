package com.wisekiddo.redmart.root;

import android.support.annotation.VisibleForTesting;

import com.wisekiddo.redmart.data.source.ItemRepository;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by ronald on 28/4/18.
 */

public class ApplicationRoot extends DaggerApplication {

    @Inject
    ItemRepository itemRepository;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
    }


    /**
     * Our Espresso tests need to be able to get an instance of the {@link ItemRepository}
     * so that we can delete all tasks before running each test
     */
    @VisibleForTesting
    public ItemRepository getItemRepository() {
        return itemRepository;
    }
}
