package com.vijay.jsonwizard.demo.utils;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.util.Log;

/**
 * Created by vijay on 5/16/15.
 */
public class CommonUtils {

    private static final String TAG = "CommonUtils";

    private CommonUtils() {
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.d(TAG, "Exception Occurred : " + ex.getMessage());
            return null;
        }
        return json;

    }

}
