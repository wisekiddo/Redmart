package com.wisekiddo.redmart.feature.itemdetail;

import com.wisekiddo.redmart.root.ActivityScoped;
import com.wisekiddo.redmart.root.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

import static com.wisekiddo.redmart.feature.itemdetail.ItemDetailActivity.EXTRA_ITEM_ID;

/**
 * Created by ronald on 16/3/18.
 *
 * This is a Dagger module. We use this to pass in the View dependency to the
 * {@link ItemDetailPresenter}.
 */

@Module
public abstract class ItemDetailModule {


    @FragmentScoped
    @ContributesAndroidInjector
    abstract ItemDetailFragment itemDetailFragment();

    @ActivityScoped
    @Binds
    abstract ItemDetailContract.Presenter itemDetailPresenter(ItemDetailPresenter presenter);

    @Provides
    @ActivityScoped
    static String provideItemId(ItemDetailActivity activity) {
        return activity.getIntent().getStringExtra(EXTRA_ITEM_ID);
    }
}
