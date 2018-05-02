package com.wisekiddo.redmart.feature.itemdetail;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.common.base.Strings;
import com.wisekiddo.redmart.data.model.Item;
import com.wisekiddo.redmart.data.source.DataSource;
import com.wisekiddo.redmart.data.source.ItemRepository;

import javax.inject.Inject;

/**
 * Created by ronald on 16/3/18.
 *
 * Listens to user actions from the UI ({@link ItemDetailFragment}), retrieves the data and updates
 * the UI as required.
 * <p>
 * By marking the constructor with {@code @Inject}, Dagger injects the dependencies required to
 * create an instance of the ItemDetailPresenter (if it fails, it emits a compiler error). It uses
 * {@link ItemDetailModule} to do so.
 * <p>
 * Dagger generated code doesn't require public access to the constructor or class, and
 * therefore, to ensure the developer doesn't instantiate the class manually and bypasses Dagger,
 * it's good practice minimise the visibility of the class/constructor as much as possible.
 */

final class ItemDetailPresenter implements ItemDetailContract.Presenter {

    private ItemRepository mItemsRepository;
    @Nullable
    private ItemDetailContract.View mItemDetailView;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Nullable
    private String mItemId;

    /**
     * Dagger strictly enforces that arguments not marked with {@code @Nullable} are not injected
     * with {@code @Nullable} values.
     */
    @Inject
    ItemDetailPresenter(@Nullable String itemId,
                        ItemRepository itemsRepository) {
        mItemsRepository = itemsRepository;
        mItemId = itemId;
    }


    private void openItem() {
        if (mItemDetailView != null) {
            mItemDetailView.setLoadingIndicator(true);
        }
        Log.i("------->", mItemId + "<-----");
        mItemsRepository.getItem(Integer.parseInt(mItemId), new DataSource.GetItemCallback() {
            @Override
            public void onItemLoaded(Item item) {
                // The view may not be able to handle UI updates anymore

                mItemDetailView.setLoadingIndicator(false);
                if (null == item) {
                   // mItemDetailView.showMissingItem();
                } else {
                    showItem(item);
                }
            }

            @Override
            public void onDataNotAvailable() {
                // The view may not be able to handle UI updates anymore

                //mItemDetailView.showMissingItem();
            }
        });
    }





    @Override
    public void takeView(ItemDetailContract.View itemDetailView) {
        mItemDetailView = itemDetailView;
        openItem();
    }

    @Override
    public void dropView() {
        mItemDetailView = null;
    }

    private void showItem(@NonNull Item item) {
        String title = item.getTitle();
        String description = item.getDesc();

        if (Strings.isNullOrEmpty(title)) {
            if (mItemDetailView != null) {
                mItemDetailView.hideTitle();
            }
        } else {
            if (mItemDetailView != null) {
                mItemDetailView.showTitle(title);
            }
        }

        if (Strings.isNullOrEmpty(description)) {
            if (mItemDetailView != null) {
                mItemDetailView.hideDescription();
            }
        } else {
            if (mItemDetailView != null) {
                mItemDetailView.showDescription(description);
            }
        }

    }
}
