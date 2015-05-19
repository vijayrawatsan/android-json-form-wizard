package com.vijay.jsonwizard.interactors;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.util.ViewUtil;
import com.vijay.jsonwizard.R;
import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.customviews.CheckBox;
import com.vijay.jsonwizard.customviews.GenericTextWatcher;
import com.vijay.jsonwizard.customviews.RadioButton;
import com.vijay.jsonwizard.interfaces.CommonListener;

/**
 * Created by vijay on 5/19/15.
 */
public class JsonFormInteractor {

    private static final String             TAG               = "JsonFormInteractor";
    private static final String             FONT_BOLD_PATH    = "font/Roboto-Bold.ttf";
    private static final String             FONT_REGULAR_PATH = "font/Roboto-Regular.ttf";
    private static final int                MATCH_PARENT      = -1;
    private static final int                WRAP_CONTENT      = -2;

    private static final JsonFormInteractor INSTANCE          = new JsonFormInteractor();

    private JsonFormInteractor() {
    }

    public List<View> fetchFormElements(String stepName, Context context, JSONObject parentJson, CommonListener listener) {
        Log.d(TAG, "fetchFormElements called");
        List<View> viewsFromJson = new ArrayList<>(5);
        try {
            JSONArray fields = parentJson.getJSONArray("fields");
            for (int i = 0; i < fields.length(); i++) {
                JSONObject childJson = fields.getJSONObject(i);
                try {
                    List<View> views = getViewsFromJson(stepName, context, childJson, listener);
                    if (views.size() > 0) {
                        viewsFromJson.addAll(views);
                    }
                } catch (Exception e) {
                    Log.d(TAG,
                            "Exception occurred in making child view at index : " + i + " : Exception is : "
                                    + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            Log.d(TAG, "Json exception occurred : " + e.getMessage());
            e.printStackTrace();
        }
        return viewsFromJson;
    }

    public List<View> getViewsFromJson(String stepName, Context context, JSONObject jsonObject, CommonListener listener)
            throws Exception {
        List<View> views = new ArrayList<>(1);
        switch (jsonObject.getString("type")) {
        case JsonFormConstants.LABEL: {
            LinearLayout.LayoutParams layoutParams = getLayoutParams(WRAP_CONTENT, WRAP_CONTENT, 0, 0, 0, (int) context
                    .getResources().getDimension(R.dimen.default_bottom_margin));
            views.add(getTextViewWith(context, 16, jsonObject.getString("text"), jsonObject.getString("key"),
                    jsonObject.getString("type"), layoutParams, FONT_BOLD_PATH));
            break;
        }
        case JsonFormConstants.CHECK_BOX: {
            views.add(getTextViewWith(context, 16, jsonObject.getString("label"), jsonObject.getString("key"),
                    jsonObject.getString("type"), getLayoutParams(MATCH_PARENT, WRAP_CONTENT, 0, 0, 0, 0),
                    FONT_BOLD_PATH));
            JSONArray options = jsonObject.getJSONArray(JsonFormConstants.OPTIONS_FIELD_NAME);
            for (int i = 0; i < options.length(); i++) {
                JSONObject item = options.getJSONObject(i);
                CheckBox checkBox = (CheckBox) LayoutInflater.from(context).inflate(R.layout.item_checkbox, null);
                checkBox.setText(item.getString("text"));
                checkBox.setTag(R.id.key, jsonObject.getString("key"));
                checkBox.setTag(R.id.type, jsonObject.getString("type"));
                checkBox.setTag(R.id.childKey, item.getString("key"));
                checkBox.setGravity(Gravity.CENTER_VERTICAL);
                checkBox.setTextSize(16);
                checkBox.setTypeface(Typeface.createFromAsset(context.getAssets(), FONT_REGULAR_PATH));
                checkBox.setOnCheckedChangeListener(listener);
                if (!TextUtils.isEmpty(item.optString("value"))) {
                    checkBox.setChecked(Boolean.valueOf(item.optString("value")));
                }
                if (i == options.length() - 1) {
                    checkBox.setLayoutParams(getLayoutParams(MATCH_PARENT, WRAP_CONTENT, 0, 0, 0, (int) context
                            .getResources().getDimension(R.dimen.extra_bottom_margin)));
                }
                views.add(checkBox);
            }
            break;
        }
        case JsonFormConstants.RADIO_BUTTON: {
            views.add(getTextViewWith(context, 16, jsonObject.getString("label"), jsonObject.getString("key"),
                    jsonObject.getString("type"), getLayoutParams(MATCH_PARENT, WRAP_CONTENT, 0, 0, 0, 0),
                    FONT_BOLD_PATH));
            JSONArray options = jsonObject.getJSONArray(JsonFormConstants.OPTIONS_FIELD_NAME);
            for (int i = 0; i < options.length(); i++) {
                JSONObject item = options.getJSONObject(i);
                RadioButton radioButton = (RadioButton) LayoutInflater.from(context).inflate(R.layout.item_radiobutton,
                        null);
                radioButton.setText(item.getString("text"));
                radioButton.setTag(R.id.key, jsonObject.getString("key"));
                radioButton.setTag(R.id.type, jsonObject.getString("type"));
                radioButton.setTag(R.id.childKey, item.getString("key"));
                radioButton.setGravity(Gravity.CENTER_VERTICAL);
                radioButton.setTextSize(16);
                radioButton.setTypeface(Typeface.createFromAsset(context.getAssets(), FONT_REGULAR_PATH));
                radioButton.setOnCheckedChangeListener(listener);
                if (!TextUtils.isEmpty(jsonObject.optString("value"))
                        && jsonObject.optString("value").equals(item.getString("key"))) {
                    radioButton.setChecked(true);
                }
                if (i == options.length() - 1) {
                    radioButton.setLayoutParams(getLayoutParams(MATCH_PARENT, WRAP_CONTENT, 0, 0, 0, (int) context
                            .getResources().getDimension(R.dimen.extra_bottom_margin)));
                }
                views.add(radioButton);
            }
            break;
        }
        case JsonFormConstants.EDIT_TEXT: {
            MaterialEditText editText = (MaterialEditText) LayoutInflater.from(context).inflate(
                    R.layout.item_edit_text, null);
            editText.setHint(jsonObject.getString("hint"));
            editText.setFloatingLabelText(jsonObject.getString("hint"));
            editText.setId(ViewUtil.generateViewId());
            editText.setTag(R.id.key, jsonObject.getString("key"));
            editText.setTag(R.id.type, jsonObject.getString("type"));
            if (!TextUtils.isEmpty(jsonObject.optString("value"))) {
                editText.setText(jsonObject.optString("value"));
            }
            editText.addTextChangedListener(new GenericTextWatcher(stepName, editText));
            views.add(editText);
            break;
        }
        case JsonFormConstants.CHOOSE_IMAGE: {
            ImageView imageView = new ImageView(context);
            imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.grey_bg));
            imageView.setTag(R.id.key, jsonObject.getString("key"));
            imageView.setTag(R.id.type, jsonObject.getString("type"));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(getLayoutParams(MATCH_PARENT, dpToPixels(context, 200), 0, 0, 0, (int) context
                    .getResources().getDimension(R.dimen.default_bottom_margin)));
            if (!TextUtils.isEmpty(jsonObject.optString("value"))) {
                imageView.setImageBitmap(BitmapFactory.decodeFile(jsonObject.optString("value")));
            }
            views.add(imageView);
            Button uploadButton = new Button(context);
            uploadButton.setText(jsonObject.getString("uploadButtonText"));
            uploadButton.setLayoutParams(getLayoutParams(WRAP_CONTENT, WRAP_CONTENT, 0, 0, 0, (int) context
                    .getResources().getDimension(R.dimen.default_bottom_margin)));
            uploadButton.setOnClickListener(listener);
            uploadButton.setTag(R.id.key, jsonObject.getString("key"));
            uploadButton.setTag(R.id.type, jsonObject.getString("type"));
            views.add(uploadButton);
            break;
        }
        }
        return views;
    }

    public int dpToPixels(Context context, float dps) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public LinearLayout.LayoutParams getLayoutParams(int width, int height, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        layoutParams.setMargins(left, top, right, bottom);
        return layoutParams;
    }

    private TextView getTextViewWith(Context context, int textSizeInSp, String text, String key, String type,
            LinearLayout.LayoutParams layoutParams, String fontPath) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTag(R.id.key, key);
        textView.setTag(R.id.type, type);
        textView.setId(ViewUtil.generateViewId());
        textView.setTextSize(textSizeInSp);
        textView.setLayoutParams(layoutParams);
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), fontPath));
        return textView;
    }

    public static JsonFormInteractor getInstance() {
        return INSTANCE;
    }
}
