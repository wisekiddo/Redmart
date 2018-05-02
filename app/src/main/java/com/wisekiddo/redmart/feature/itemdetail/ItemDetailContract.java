package com.wisekiddo.redmart.feature.itemdetail;

import com.wisekiddo.redmart.root.BasePresenter;
import com.wisekiddo.redmart.root.BaseView;

/**
 * Created by ronald on 28/4/18.
 *
 * This specifies the contract between the view and the presenter.
 */

public interface ItemDetailContract {

    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void hideTitle();

        void showTitle(String title);

        void hideDescription();

        void showDescription(String description);

    }

    interface Presenter extends BasePresenter<View> {

        void takeView(ItemDetailContract.View itemDetailFragment);

        void dropView();
    }
}
