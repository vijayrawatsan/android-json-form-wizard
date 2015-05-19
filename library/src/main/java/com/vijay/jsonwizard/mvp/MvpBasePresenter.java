package com.vijay.jsonwizard.mvp;

import java.lang.ref.WeakReference;

public class MvpBasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private WeakReference<V> viewRef;

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    /**
     * Get the attached view. You should always call {@link #isViewAttached()}
     * to check if the view is attached to avoid NullPointerExceptions
     */
    protected V getView() {
        return viewRef.get();
    }

    /**
     * Checks if a view is attached to this presenter. You should always call
     * this method before calling {@link #getView()} to get the view instance.
     */
    protected boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void detachView(boolean retainInstance) {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }
}