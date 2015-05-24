package com.vijay.jsonwizard.widgets;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.vijay.jsonwizard.R;
import com.vijay.jsonwizard.interfaces.CommonListener;
import com.vijay.jsonwizard.interfaces.FormWidgetFactory;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.vijay.jsonwizard.utils.FormUtils.*;

/**
 * Created by vijay on 24-05-2015.
 */
public class ImagePickerFactory implements FormWidgetFactory {
    @Override
    public List<View> getViewsFromJson(String stepName, Context context, JSONObject jsonObject, CommonListener listener) throws Exception {
        List<View> views = new ArrayList<>(1);
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
        return views;
    }
}
