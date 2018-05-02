package com.wisekiddo.redmart.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.wisekiddo.redmart.data.model.Item;
import com.wisekiddo.redmart.data.source.local.dao.ItemsDao;

/**
 * Created by ronald on 13/3/18.
 *
 * The Room ApplicationDatabase that contains the Item table.
 */

@Database(entities = {Item.class}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {

    public abstract ItemsDao itemsDao();
}
