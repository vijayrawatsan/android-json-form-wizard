package com.vijay.jsonwizard.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by vijay on 4/19/15.
 */
public abstract class BaseFragment<VS extends ViewState> extends Fragment {

    // @Icicle
    VS mViewState;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Icepick.restoreInstanceState(this, savedInstanceState);
        if (savedInstanceState == null) {
            mViewState = createViewState();
            mViewState.setSavedInstance(false);
        } else {
            mViewState.setSavedInstance(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Icepick.saveInstanceState(this, outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutRes = getLayoutRes();
        if (layoutRes == 0) {
            throw new IllegalArgumentException("getLayoutRes() returned 0, which is not allowed. "
                    + "If you don't want to use getLayoutRes() but implement your own view for this "
                    + "fragment manually, then you have to override onCreateView();");
        } else {
            View v = inflater.inflate(layoutRes, container, false);
            return v;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * Return the layout resource like R.layout.my_layout
     *
     * @return the layout resource or null, if you don't want to have an UI
     */
    protected int getLayoutRes() {
        return 0;
    }

    protected abstract VS createViewState();

    public VS getViewState() {
        return mViewState;
    }
}
