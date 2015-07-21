package com.vijay.jsonwizard.views;

import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.vijay.jsonwizard.fragments.JsonFormFragment;
import com.vijay.jsonwizard.interfaces.CommonListener;
import com.vijay.jsonwizard.mvp.MvpView;
import com.vijay.jsonwizard.mvp.ViewState;

/**
 * Created by vijay on 5/14/15.
 */
public interface JsonFormFragmentView<VS extends ViewState> extends MvpView {
    Bundle getArguments();

    void setActionBarTitle(String title);

    Context getContext();

    void showToast(String message);

    CommonListener getCommonListener();

    void addFormElements(List<View> views);

    ActionBar getSupportActionBar();

    Toolbar getToolbar();

    void setToolbarTitleColor(int white);

    void updateVisibilityOfNextAndSave(boolean next, boolean save);

    void hideKeyBoard();

    void transactThis(JsonFormFragment next);

    void startActivityForResult(Intent intent, int requestCode);

    void updateRelevantImageView(Bitmap bitmap, String imagePath, String currentKey);

    void writeValue(String stepName, String key, String value);

    void writeValue(String stepName, String prentKey, String childObjectKey, String childKey, String value);

    JSONObject getStep(String stepName);

    String getCurrentJsonState();

    void finishWithResult(Intent returnIntent);

    void setUpBackButton();

    void backClick();

    void unCheckAllExcept(String parentKey, String childKey);

    String getCount();
}
