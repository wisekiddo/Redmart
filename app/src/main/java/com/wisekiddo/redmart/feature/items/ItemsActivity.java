package com.wisekiddo.redmart.feature.items;

import android.os.Bundle;
import com.wisekiddo.redmart.R;

import dagger.android.support.DaggerAppCompatActivity;

public class ItemActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
    }
}
