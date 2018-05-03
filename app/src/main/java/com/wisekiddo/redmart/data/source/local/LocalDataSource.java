package com.wisekiddo.redmart.data.source.local;

import android.support.annotation.NonNull;


import com.wisekiddo.redmart.data.model.Item;
import com.wisekiddo.redmart.data.source.DataSource;
import com.wisekiddo.redmart.data.source.local.dao.ItemsDao;
import com.wisekiddo.redmart.util.ApplicationExecutors;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    public LocalDataSource(@NonNull ApplicationExecutors executors, @NonNull ItemsDao itemDao) {
        mItemsDao = itemDao;
        mApplicationExecutors = executors;
    }


    @Override
    public void getItems(@NonNull final LoadItemsCallback callback) {
        mItemsDao.getItems().subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<List<Item>>() {
            @Override
            public void accept(@io.reactivex.annotations.NonNull List<Item> items) throws Exception {
                callback.onItemsLoaded(items);
                //callback.onDataNotAvailable();
            }
        });
    }



    /*
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
    */

    @Override
    public void getItem(@NonNull final Integer id, @NonNull final GetItemCallback callback) {
        mItemsDao.getItemById(id).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Consumer<Item>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull Item item) throws Exception {
                        callback.onItemLoaded(item);
                    }
                    //callback.onDataNotAvailable();
                });


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
