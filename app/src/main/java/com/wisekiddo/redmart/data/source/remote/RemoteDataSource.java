package com.wisekiddo.redmart.data.source.remote;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.google.common.collect.Lists;
import com.wisekiddo.redmart.data.model.Item;
import com.wisekiddo.redmart.data.source.DataSource;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Created by ronald on 13/3/18.
 * Implementation of the data source that adds a latency simulating network.
 */

@Singleton
public class RemoteDataSource implements DataSource {

    private static final int SERVICE_LATENCY_IN_MILLIS = 5000;

    private final static Map<String, Item> ITEMS_SERVICE_DATA;

    static {

        final int RANGE = 100000;
        ITEMS_SERVICE_DATA = new LinkedHashMap<>(RANGE);
        for (int i=RANGE;i<RANGE+RANGE;i++) {
            addItem("Title:"+(i+1), "Ground looks good,"+ (i+1)+" no foundation work required.");

        }
    }

    public RemoteDataSource() {}

    private static void addItem(String title, String description) {
        Item newItem = new Item(title, description);
        ITEMS_SERVICE_DATA.put(newItem.getId(), newItem);
    }

    @Override
    public void getItems(final @NonNull LoadItemsCallback callback) {
        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onItemsLoaded(Lists.newArrayList(ITEMS_SERVICE_DATA.values()));
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void getItem(@NonNull String itemId, final @NonNull GetItemCallback callback) {
        final Item item = ITEMS_SERVICE_DATA.get(itemId);

        // Simulate network by delaying the execution.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onItemLoaded(item);
            }
        }, SERVICE_LATENCY_IN_MILLIS);
    }

    @Override
    public void saveItem(@NonNull Item item) {
        ITEMS_SERVICE_DATA.put(item.getId(), item);
    }


    @Override
    public void refreshItems() {
        // Not required because the {@link ItemRepository} handles the logic of refreshing the
        // items from all the available data sources.
    }

    @Override
    public void deleteAllItems() {
        ITEMS_SERVICE_DATA.clear();
    }

    @Override
    public void deleteItem(@NonNull String itemId) {
        ITEMS_SERVICE_DATA.remove(itemId);
    }
}
