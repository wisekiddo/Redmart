package com.wisekiddo.redmart.feature.items;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wisekiddo.redmart.R;
import com.wisekiddo.redmart.data.model.Item;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import com.wisekiddo.redmart.root.ActivityScoped;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ronald on 28/4/18.
 */

@ActivityScoped
public class ItemsFragment extends DaggerFragment implements ItemsContract.View {

    @Inject
    ItemsContract.Presenter presenter;
    /**
     * Listener for clicks on items in the ListView.
     */


    private ItemsAdapter listAdapter;
    private View noItemsView;
    private ImageView noItemIcon;
    private TextView mNoItemMainView;
    private LinearLayout mItemsView;
    private TextView mFilteringLabelView;

    @Inject
    public ItemsFragment() {
        // Requires empty public constructor
    }

    public interface ItemListener {
        void onItemClick(Item clickedItem);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listAdapter = new ItemsAdapter(new ArrayList<Item>(0), itemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();  //prevent leaking activity in
        // case presenter is orchestrating a long running item
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //presenter.result(requestCode, resultCode);
    }

    ItemListener itemListener = new ItemListener() {
        @Override
        public void onItemClick(Item clickedItem) {
            presenter.openItemDetails(clickedItem);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.items_fragment, container, false);

        // Set up items view
        ListView listView = root.findViewById(R.id.items_list);
        listView.setAdapter(listAdapter);
        //mFilteringLabelView = root.findViewById(R.id.filteringLabel);
        mItemsView = root.findViewById(R.id.itemsLayout);

        // Set up  no items view
        noItemsView = root.findViewById(R.id.noItems);
        noItemIcon = root.findViewById(R.id.noItemsIcon);
        mNoItemMainView = root.findViewById(R.id.noItemsMain);

        // Set up floating action button
       // FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_item);
/*
        fab.setImageResource(R.drawable.ic_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //presenter.addNewItem();
            }
        });*/

        // Set up progress indicator
        final ItemsSwipeRefreshLayout swipeRefreshLayout = root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(listView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadItems(false);
            }
        });

        setHasOptionsMenu(true);

        return root;
    }



    @Override
    public void setLoadingIndicator(final boolean active) {

        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl = getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(active);
            }
        });
    }

    @Override
    public void showItems(List<Item> items) {
        listAdapter.replaceData(items);

        mItemsView.setVisibility(View.VISIBLE);
        noItemsView.setVisibility(View.GONE);
    }

    @Override
    public void showNoItems() {
        showNoItemsViews(
                getResources().getString(R.string.no_items_all),
                R.drawable.ic_assignment_turned_in_24dp,
                false
        );
    }

    private void showNoItemsViews(String mainText, int iconRes, boolean showAddView) {
        mItemsView.setVisibility(View.GONE);
        noItemsView.setVisibility(View.VISIBLE);

        mNoItemMainView.setText(mainText);
        //noinspection deprecation
        noItemIcon.setImageDrawable(getResources().getDrawable(iconRes));
    }

    @Override
    public void showLoadingItemsError() {
        //showMessage(getString(R.string.loading_items_error));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }




    private static class ItemsAdapter extends BaseAdapter {

        private List<Item> mItems;
        private ItemListener mItemListener;

        public ItemsAdapter(List<Item> items, ItemListener itemListener) {
            setList(items);
            mItemListener = itemListener;
        }

        public void replaceData(List<Item> items) {
            setList(items);
            notifyDataSetChanged();
        }

        private void setList(List<Item> items) {
            mItems = checkNotNull(items);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Item getItem(int i) {
            return mItems.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View rowView = view;
            if (rowView == null) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                rowView = inflater.inflate(R.layout.item, viewGroup, false);
            }

            final Item item = getItem(i);

            TextView titleTV = rowView.findViewById(R.id.title);
            titleTV.setText(item.getTitle());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemListener.onItemClick(item);
                }
            });

            return rowView;
        }
    }

}
