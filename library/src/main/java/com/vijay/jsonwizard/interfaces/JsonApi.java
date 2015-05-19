package com.vijay.jsonwizard.interfaces;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vijay on 5/16/15.
 */
public interface JsonApi {
    JSONObject getStep(String stepName);

    void writeValue(String stepName, String key, String value) throws JSONException;

    void writeValue(String stepName, String prentKey, String childObjectKey, String childKey, String value)
            throws JSONException;

    String currentJsonState();

    String getCount();
}
