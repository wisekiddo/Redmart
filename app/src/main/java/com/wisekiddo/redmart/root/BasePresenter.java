package com.wisekiddo.redmart.root;

/**
 * Created by ronald on 28/4/18.
 */

public interface BasePresenter<T> {
    void takeView(T view);
    void dropView();
}
