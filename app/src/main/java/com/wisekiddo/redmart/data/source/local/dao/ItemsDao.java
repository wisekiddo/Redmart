package com.wisekiddo.redmart.data.source.local.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.wisekiddo.redmart.data.model.Item;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;

/**
 * Created by ronald on 28/4/18.
 *
 * Data Access Object for the items table.
 */

@Dao
public interface ItemsDao {

    /**
     * Select all items from the items table.
     *
     * @return all items.
     */
    @Query("SELECT * FROM Item")
    Flowable<List<Item>> getItems();
    //    List<Item> getItems();

    /**
     * Select a item by id.
     *
     * @param itemId the item id.
     * @return the item with itemId.
     */
   // @Query("SELECT * FROM Item WHERE id = :itemId")
   // Item getItemById(Integer itemId);

    @Query("SELECT * FROM Item WHERE id = :itemId")
    Maybe<Item> getItemById(Integer itemId);

    /**
     * Insert a item in the database. If the item already exists, replace it.
     *
     * @param item the item to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Item item);


}
