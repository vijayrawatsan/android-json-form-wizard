package com.vijay.jsonwizard.mvp;

import android.os.Bundle;

public abstract class MvpActivity<P extends MvpPresenter, VS extends ViewState> extends BaseActivity<VS> implements
        MvpView {

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView(false);
    }

    protected abstract P createPresenter();
}