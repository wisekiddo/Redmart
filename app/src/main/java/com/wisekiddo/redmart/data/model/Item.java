package com.wisekiddo.redmart.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

/**
 * Created by ronald on 28/4/18.
 *
 * Immutable model class for a Item.
 */

@Entity(tableName = "items")
public final class Item {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private final String id;

    @Nullable
    @ColumnInfo(name = "title")
    @SerializedName("title")
    private final String title;

    @Nullable
    @ColumnInfo(name = "desc")
    @SerializedName("desc")
    private final String description;

    @Ignore
    public Item(@Nullable String title, @Nullable String description) {
        this(title, description, UUID.randomUUID().toString());
    }

    public Item(@Nullable String title, @Nullable String description,
                @NonNull String id) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public boolean isEmpty() {
        return Strings.isNullOrEmpty(title) &&
               Strings.isNullOrEmpty(description);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equal(id, item.id) &&
               Objects.equal(title, item.title) &&
               Objects.equal(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, title, description);
    }

    @Override
    public String toString() {
        return "Item with title " + title;
    }
}
