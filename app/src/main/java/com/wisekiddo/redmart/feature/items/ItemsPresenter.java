package com.wisekiddo.redmart.feature.items;

import android.support.annotation.NonNull;
import android.util.Log;

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

    private final ItemRepository itemRepository;
    @Nullable
    private ItemsContract.View itemsView;

    private boolean firstLoad = true;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    ItemsPresenter(ItemRepository itemsRepository) {
        itemRepository = itemsRepository;
    }

    @Override
    public void loadItems(boolean forceUpdate) {
        // Simplification for sample: a network reload will be forced on first load.
        loadItems(forceUpdate || firstLoad, true);
        firstLoad = false;
    }

    private void loadItems(boolean forceUpdate, final boolean showLoadingUI) {
        if (showLoadingUI) {
            if (itemsView != null) {
                itemsView.setLoadingIndicator(true);
            }
        }
        if (forceUpdate) {
            itemRepository.refreshItems();
        }

        itemRepository.getItems(new DataSource.LoadItemsCallback() {
            @Override
            public void onItemsLoaded(List<Item> items) {
                List<Item> itemsToShow = new ArrayList<>();
                for (Item item : items) {
                    itemsToShow.add(item);
                }
                // The view may not be able to handle UI updates anymore
                if (itemsView == null) {
                    return;
                }

                if (showLoadingUI) {
                    itemsView.setLoadingIndicator(false);
                }

                processItems(itemsToShow);
            }

            @Override
            public void onDataNotAvailable() {
                itemsView.showLoadingItemsError();
            }
        });
    }

    private void processItems(List<Item> items) {
        if (items.isEmpty()) {
            // Show a message indicating there are no items for that filter type.
            processEmptyItems();
        } else {
            // Show the list of items
            if (itemsView != null) {
                itemsView.showItems(items);
            }
        }
    }


    private void processEmptyItems() {
        itemsView.showNoItems();
    }

    @Override
    public void openItemDetails(@NonNull Item requestedItem) {
        checkNotNull(requestedItem, "requestedItem cannot be null!");
        if (itemsView != null) {
           itemsView.showItemDetailsUi(requestedItem.getId().toString());
        }
    }


    @Override
    public void takeView(ItemsContract.View view) {
        this.itemsView = view;
        loadItems(false);
    }

    @Override
    public void dropView() {
        itemsView = null;
    }
}
