package com.wisekiddo.redmart.feature.itemdetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wisekiddo.redmart.R;
import com.wisekiddo.redmart.root.ActivityScoped;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * Created by ronald on 28/4/18.
 *
 * Main UI for the item detail screen.
 */

@ActivityScoped
public class ItemDetailFragment extends DaggerFragment implements ItemDetailContract.View {

    @NonNull
    private static final String ARGUMENT_ITEM_ID = "ITEM_ID";


    @Inject
    String itemId;

    @Inject
    ItemDetailContract.Presenter mPresenter;
    private TextView mDetailTitle;
    private TextView mDetailDescription;

    @Inject
    public ItemDetailFragment() {
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        mPresenter.dropView();
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.itemdetail_fragment, container, false);
        setHasOptionsMenu(true);
        mDetailTitle = root.findViewById(R.id.item_detail_title);
        mDetailDescription = root.findViewById(R.id.item_detail_description);


        return root;
    }


    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            mDetailTitle.setText("");
            //mDetailDescription.setText(getString(R.string.loading));
        }
    }

    @Override
    public void hideDescription() {
        mDetailDescription.setVisibility(View.GONE);
    }

    @Override
    public void hideTitle() {
        mDetailTitle.setVisibility(View.GONE);
    }

    @Override
    public void showDescription(@NonNull String description) {
        mDetailDescription.setVisibility(View.VISIBLE);
        mDetailDescription.setText(description);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if (requestCode == REQUEST_EDIT_ITEM) {
            // If the item was edited successfully, go back to the list.
            if (resultCode == Activity.RESULT_OK) {
                getActivity().finish();
            }
        }*/
    }

    @Override
    public void showTitle(@NonNull String title) {
        mDetailTitle.setVisibility(View.VISIBLE);
        mDetailTitle.setText(title);
    }


}
