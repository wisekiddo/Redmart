package com.wisekiddo.redmart.feature.items;

import android.os.Bundle;
import com.wisekiddo.redmart.R;
import com.wisekiddo.redmart.util.ActivityUtils;

import javax.inject.Inject;

import dagger.Lazy;
import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by ronald on 28/4/18.
 */

public class ItemsActivity extends DaggerAppCompatActivity {

    @Inject
    ItemsPresenter itemsPresenter;
    @Inject
    Lazy<ItemsFragment> itemFragmentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_activity);

       ItemsFragment itemsFragment = (ItemsFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (itemsFragment == null) {
            // Get the fragment from dagger
            itemsFragment = itemFragmentProvider.get();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), itemsFragment, R.id.contentFrame);
        }


    }
}
