package com.vijay.jsonwizard.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.rey.material.util.ViewUtil;
import com.vijay.jsonwizard.R;
import com.vijay.jsonwizard.interfaces.CommonListener;
import com.vijay.jsonwizard.interfaces.FormWidgetFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by nipun on 30/05/15.
 */
public class SpinnerFactory implements FormWidgetFactory {

    @Override
    public List<View> getViewsFromJson(String stepName, Context context, JSONObject jsonObject, CommonListener listener) throws Exception {
        List<View> views = new ArrayList<>(1);
        MaterialSpinner spinner = (MaterialSpinner) LayoutInflater.from(context).inflate(R.layout.item_spinner, null);

        String hint = jsonObject.optString("hint");
        if (!TextUtils.isEmpty(hint)) {
            spinner.setHint(jsonObject.getString("hint"));
            spinner.setFloatingLabelText(jsonObject.getString("hint"));
        }

        spinner.setId(ViewUtil.generateViewId());

        spinner.setTag(R.id.key, jsonObject.getString("key"));
        spinner.setTag(R.id.type, jsonObject.getString("type"));

        String valueToSelect = "";
        int indexToSelect = -1;
        if (!TextUtils.isEmpty(jsonObject.optString("value"))) {
            valueToSelect = jsonObject.optString("value");
        }

        JSONArray valuesJson = jsonObject.optJSONArray("values");
        String[] values = null;
        if (valuesJson != null && valuesJson.length() > 0) {
            values = new String[valuesJson.length()];
            for (int i = 0; i < valuesJson.length(); i++) {
                values[i] = valuesJson.optString(i);
                if (valueToSelect.equals(values[i])) {
                    indexToSelect = i;
                }
            }
        }

        if (values != null) {
            spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, values));
            spinner.setSelection(indexToSelect + 1, true);
            spinner.setOnItemSelectedListener(listener);
        }
        views.add(spinner);
        return views;
    }
}
