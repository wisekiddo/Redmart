package com.wisekiddo.redmart.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wisekiddo.redmart.data.model.Item;
import com.wisekiddo.redmart.data.source.local.Local;
import com.wisekiddo.redmart.data.source.remote.Remote;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class ItemRepository implements DataSource {

    private final DataSource itemsRemoteDataSource;
    private final DataSource itemsLocalDataSource;

    Map<Integer, Item> mCachedItems;

    boolean mCacheIsDirty = false;

    @Inject
    ItemRepository(@Remote DataSource itemsRemoteDataSource,
                   @Local DataSource itemsLocalDataSource) {
        this.itemsRemoteDataSource = itemsRemoteDataSource;
        this.itemsLocalDataSource = itemsLocalDataSource;
    }

    @Override
    public void getItems(@NonNull final LoadItemsCallback callback) {
        checkNotNull(callback);

        // Respond immediately with cache if available and not dirty
        if (mCachedItems != null && !mCacheIsDirty) {
            callback.onItemsLoaded(new ArrayList<>(mCachedItems.values()));
            return;
        }

        if (mCacheIsDirty) {
            // If the cache is dirty we need to fetch new data from the network.
            getItemsFromRemoteDataSource(callback);
        } else {
            // Query the local storage if available. If not, query the network.
            itemsLocalDataSource.getItems(new LoadItemsCallback() {
                @Override
                public void onItemsLoaded(List<Item> items) {
                    refreshCache(items);
                    callback.onItemsLoaded(new ArrayList<>(mCachedItems.values()));
                }

                @Override
                public void onDataNotAvailable() {
                    getItemsFromRemoteDataSource(callback);
                }
            });
        }
    }

    @Override
    public void saveItem(@NonNull Item item) {
        checkNotNull(item);
        itemsRemoteDataSource.saveItem(item);
        itemsLocalDataSource.saveItem(item);

        // Do in memory cache update to keep the app UI up to date
        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }
        mCachedItems.put(item.getId(), item);
    }

    @Override
    public void getItem(@NonNull final Integer itemId, @NonNull final GetItemCallback callback) {
        checkNotNull(itemId);
        checkNotNull(callback);

        Item cachedItem = getItemWithId(itemId);

        // Respond immediately with cache if available
        if (cachedItem != null) {
            callback.onItemLoaded(cachedItem);
            return;
        }

        // Load from server/persisted if needed.

        // Is the item in the local data source? If not, query the network.
        itemsLocalDataSource.getItem(itemId, new GetItemCallback() {
            @Override
            public void onItemLoaded(Item item) {
                // Do in memory cache update to keep the app UI up to date
                if (mCachedItems == null) {
                    mCachedItems = new LinkedHashMap<>();
                }
                mCachedItems.put(item.getId(), item);
                callback.onItemLoaded(item);
            }

            @Override
            public void onDataNotAvailable() {
                itemsRemoteDataSource.getItem(itemId, new GetItemCallback() {
                    @Override
                    public void onItemLoaded(Item item) {
                        // Do in memory cache update to keep the app UI up to date
                        if (mCachedItems == null) {
                            mCachedItems = new LinkedHashMap<>();
                        }
                        mCachedItems.put(item.getId(), item);
                        callback.onItemLoaded(item);
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void refreshItems() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllItems() {
        itemsRemoteDataSource.deleteAllItems();
        itemsLocalDataSource.deleteAllItems();

        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }
        mCachedItems.clear();
    }

    @Override
    public void deleteItem(@NonNull Integer itemId) {
        itemsRemoteDataSource.deleteItem(checkNotNull(itemId));
        itemsLocalDataSource.deleteItem(checkNotNull(itemId));

        mCachedItems.remove(itemId);
    }

    private void getItemsFromRemoteDataSource(@NonNull final LoadItemsCallback callback) {
        itemsRemoteDataSource.getItems(new LoadItemsCallback() {
            @Override
            public void onItemsLoaded(List<Item> items) {
                refreshCache(items);
                refreshLocalDataSource(items);
                callback.onItemsLoaded(new ArrayList<>(mCachedItems.values()));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    private void refreshCache(List<Item> items) {
        if (mCachedItems == null) {
            mCachedItems = new LinkedHashMap<>();
        }
        mCachedItems.clear();
        for (Item item : items) {
            mCachedItems.put(item.getId(), item);
        }
        mCacheIsDirty = false;
    }

    private void refreshLocalDataSource(List<Item> items) {
        itemsLocalDataSource.deleteAllItems();
        for (Item item : items) {
            itemsLocalDataSource.saveItem(item);
        }
    }

    @Nullable
    private Item getItemWithId(@NonNull Integer id) {
        checkNotNull(id);
        if (mCachedItems == null || mCachedItems.isEmpty()) {
            return null;
        } else {
            return mCachedItems.get(id);
        }
    }
}
