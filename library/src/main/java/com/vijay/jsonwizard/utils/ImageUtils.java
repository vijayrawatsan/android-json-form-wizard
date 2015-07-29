package com.vijay.jsonwizard.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by vijay.rawat01 on 7/29/15.
 */
public class ImageUtils {

    private static LruCache<String, Bitmap> mBitmapLruCache = new LruCache<>(10000000);

    public static Bitmap loadBitmapFromFile(String path, int requiredWidth, int requiredHeight) {
        String key = path + ":" + requiredWidth + ":" + requiredHeight;
        Bitmap bitmap = mBitmapLruCache.get(key);
        if(bitmap != null) {
            Log.d("ImagePickerFactory", "Found in cache.");
            return bitmap;
        }
        // First decode with inJustDecodeBounds=true to check dimensions
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(path, options);
        mBitmapLruCache.put(key, bitmap);
        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }
}
