package com.wisekiddo.redmart.feature.itemdetail;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.wisekiddo.redmart.R;
import com.wisekiddo.redmart.util.ActivityUtils;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by ronald on 28/4/18.
 *
 * Displays item details screen.
 */

public class ItemDetailActivity extends DaggerAppCompatActivity {
    public static final String EXTRA_ITEM_ID = "ITEM_ID";
    @Inject
    ItemDetailFragment injectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.itemdetail_activity);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

        ItemDetailFragment itemDetailFragment = (ItemDetailFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        if (itemDetailFragment == null) {
            itemDetailFragment = injectedFragment;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    itemDetailFragment, R.id.contentFrame);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
