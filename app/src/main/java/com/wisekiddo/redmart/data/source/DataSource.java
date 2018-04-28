package com.wisekiddo.redmart.data.source;

import android.support.annotation.NonNull;

import com.wisekiddo.redmart.data.model.Item;

import java.util.List;

public interface DataSource {

    interface LoadItemsCallback {

        void onItemsLoaded(List<Item> items);

        void onDataNotAvailable();
    }

    interface GetItemCallback {

        void onItemLoaded(Item item);

        void onDataNotAvailable();
    }

    void getItems(@NonNull LoadItemsCallback callback);

    void getItem(@NonNull String itemId, @NonNull GetItemCallback callback);

    void saveItem(@NonNull Item item);

    void refreshItems();

    void deleteAllItems();

    void deleteItem(@NonNull String itemId);

}
