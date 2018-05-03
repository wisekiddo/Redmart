package com.wisekiddo.redmart.data.source.local;

import android.support.annotation.NonNull;

import com.google.common.base.Optional;
import com.wisekiddo.redmart.data.model.Item;
import com.wisekiddo.redmart.data.source.DataSource;
import com.wisekiddo.redmart.data.source.local.dao.ItemsDao;
import com.wisekiddo.redmart.util.ApplicationExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by ronald on 28/4/18.
 *
 * Concrete implementation of a data source as a db.
 */

@Singleton
public class LocalDataSource implements DataSource {

    private final ItemsDao mItemsDao;

    private final ApplicationExecutors mApplicationExecutors;

    @Inject
    public LocalDataSource(@NonNull ApplicationExecutors executors, @NonNull ItemsDao tasksDao) {
        mItemsDao = tasksDao;
        mApplicationExecutors = executors;
    }

    @Override
    public Flowable<List<Item>> getItems() {
        return null;
    }

    @Override
    public Flowable<Optional<Item>> getItem(@NonNull Integer taskId) {
        return null;
    }

    @Override
    public void getItems(@NonNull final LoadItemsCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Item> items = mItemsDao.getItems();
                mApplicationExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (items.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable();
                        } else {
                            callback.onItemsLoaded(items);
                        }
                    }
                });
            }
        };

        mApplicationExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getItem(@NonNull final Integer id, @NonNull final GetItemCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Item item = mItemsDao.getItemById(id);

                mApplicationExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (item != null) {
                            callback.onItemLoaded(item);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mApplicationExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveItem(@NonNull final Item item) {
        checkNotNull(item);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mItemsDao.insertItem(item);
            }
        };
        mApplicationExecutors.diskIO().execute(saveRunnable);
    }


    @Override
    public void refreshItems() {
        // Not required because the {@link ItemRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteAllItems() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
               // mItemsDao.deleteItems();
            }
        };

        mApplicationExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteItem(@NonNull final Integer taskId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
               // mItemsDao.deleteItemById(taskId);
            }
        };

        mApplicationExecutors.diskIO().execute(deleteRunnable);
    }
}
