package com.wisekiddo.redmart.data.source;

import android.support.annotation.NonNull;

import com.google.common.base.Optional;
import com.wisekiddo.redmart.data.model.Item;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

public interface DataSource {

    interface LoadItemsCallback {

        void onItemsLoaded(List<Item> items);

        void onDataNotAvailable();
    }

    interface GetItemCallback {

        void onItemLoaded(Item item);

        void onDataNotAvailable();
    }

    //Flowable<List<Item>> getItems(@NonNull LoadItemsCallback callback);
    //Maybe<Item> getItem(@NonNull Integer itemId, @NonNull GetItemCallback callback);

    void getItems(@NonNull LoadItemsCallback callback);
    void getItem(@NonNull Integer itemId, @NonNull GetItemCallback callback);

    void saveItem(@NonNull Item item);

    void refreshItems();

    void deleteAllItems();

    void deleteItem(@NonNull Integer itemId);

}
