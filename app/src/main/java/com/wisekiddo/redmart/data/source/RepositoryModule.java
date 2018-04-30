package com.wisekiddo.redmart.data.source;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.wisekiddo.redmart.data.source.local.ApplicationDatabase;
import com.wisekiddo.redmart.data.source.local.ItemsDao;
import com.wisekiddo.redmart.data.source.local.Local;
import com.wisekiddo.redmart.data.source.local.LocalDataSource;
import com.wisekiddo.redmart.data.source.remote.Remote;
import com.wisekiddo.redmart.data.source.remote.RemoteDataSource;
import com.wisekiddo.redmart.util.ApplicationExecutors;
import com.wisekiddo.redmart.util.DiskIOThreadExecutor;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public class RepositoryModule {

    private static final int THREAD_COUNT = 3;

    @Singleton
    @Provides
    @Local
    DataSource provideItemsLocalDataSource(ItemsDao dao, ApplicationExecutors executors) {
        return new LocalDataSource(executors, dao);
    }

    @Singleton
    @Provides
    @Remote
    DataSource provideItemsRemoteDataSource() {
        return new RemoteDataSource();
    }

    @Singleton
    @Provides
    ApplicationDatabase provideDb(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), ApplicationDatabase.class, "Redmart.db")
                .build();
    }

    @Singleton
    @Provides
    ItemsDao provideItemsDao(ApplicationDatabase db) {
        return db.itemsDao();
    }

    @Singleton
    @Provides
    ApplicationExecutors provideAppExecutors() {
        return new ApplicationExecutors(new DiskIOThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new ApplicationExecutors.MainThreadExecutor());
    }
}
