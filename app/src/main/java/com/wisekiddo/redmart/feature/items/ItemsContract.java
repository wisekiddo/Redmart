package com.wisekiddo.redmart.feature.items;

import android.support.annotation.NonNull;

import com.wisekiddo.redmart.data.model.Item;
import com.wisekiddo.redmart.root.BasePresenter;
import com.wisekiddo.redmart.root.BaseView;

import java.util.List;


/**
 * Created by ronald on 28/4/18.
 *
 * This specifies the contract between the view and the presenter.
 */

public interface ItemsContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showItems(List<Item> items);

        void showItemDetailsUi(String itemId);

        void showLoadingItemsError();

        void showNoItems();
    }

    interface Presenter extends BasePresenter<View> {

        void loadItems(boolean forceUpdate);

        void openItemDetails(@NonNull Item requestedItem);

        void takeView(ItemsContract.View view);

        void dropView();
    }
}
