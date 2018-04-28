package com.wisekiddo.redmart.feature.items;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ronald on 28/4/18.
 */
public class ItemSwipeRefreshLayout extends SwipeRefreshLayout {

    private View mScrollUpChild;

    public ItemSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ItemSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollUpChild != null) {
            return mScrollUpChild.canScrollVertically(-1);
        }
        return super.canChildScrollUp();
    }

    public void setScrollUpChild(View view) {
        mScrollUpChild = view;
    }
}
