package com.wisekiddo.redmart.feature.items;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.wisekiddo.redmart.data.model.Item;
import com.wisekiddo.redmart.data.source.DataSource;
import com.wisekiddo.redmart.data.source.ItemRepository;
import com.wisekiddo.redmart.root.ActivityScoped;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by ronald on 28/4/18.
 */

@ActivityScoped
final class ItemsPresenter implements ItemsContract.Presenter {

    private final ItemRepository mItemsRepository;
    @Nullable
    private ItemsContract.View mItemsView;

    private boolean mFirstLoad = true;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    ItemsPresenter(ItemRepository itemsRepository) {
        mItemsRepository = itemsRepository;
    }

    @Override
    public void loadItems(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadItems(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void loadItems(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            if (mItemsView != null) {
                mItemsView.setLoadingIndicator(true);
            }
        }
        if (forceUpdate) {
            mItemsRepository.refreshItems();
        }

        mItemsRepository.getItems(new DataSource.LoadItemsCallback() {
            @Override
            public void onItemsLoaded(List<Item> items) {
                List<Item> itemsToShow = new ArrayList<>();

                // We filter the items based on the requestType
                for (Item item : items) {
                    itemsToShow.add(item);
                }
                // The view may not be able to handle UI updates anymore
                if (mItemsView == null) {
                    return;
                }

                if (showLoadingUI) {
                    mItemsView.setLoadingIndicator(false);
                }

                processItems(itemsToShow);
            }

            @Override
            public void onDataNotAvailable() {
                mItemsView.showLoadingItemsError();
            }
        });
    }

    private void processItems(List<Item> items) {
        if (items.isEmpty()) {
            // Show a message indicating there are no items for that filter type.
            processEmptyItems();
        } else {
            // Show the list of items
            if (mItemsView != null) {
                mItemsView.showItems(items);
            }
        }
    }


    private void processEmptyItems() {
        mItemsView.showNoItems();
    }

    @Override
    public void openItemDetails(@NonNull Item requestedItem) {
        checkNotNull(requestedItem, "requestedItem cannot be null!");
        if (mItemsView != null) {
            //mItemsView.showItemDetailsUi(requestedItem.getId());
        }
    }


    @Override
    public void takeView(ItemsContract.View view) {
        this.mItemsView = view;
        loadItems(false);
    }

    @Override
    public void dropView() {
        mItemsView = null;
    }
}
