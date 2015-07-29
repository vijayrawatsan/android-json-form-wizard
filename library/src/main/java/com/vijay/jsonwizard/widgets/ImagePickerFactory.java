package com.vijay.jsonwizard.widgets;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.vijay.jsonwizard.R;
import com.vijay.jsonwizard.interfaces.CommonListener;
import com.vijay.jsonwizard.interfaces.FormWidgetFactory;
import com.vijay.jsonwizard.utils.ImageUtils;
import com.vijay.jsonwizard.utils.ValidationStatus;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.vijay.jsonwizard.utils.FormUtils.MATCH_PARENT;
import static com.vijay.jsonwizard.utils.FormUtils.WRAP_CONTENT;
import static com.vijay.jsonwizard.utils.FormUtils.dpToPixels;
import static com.vijay.jsonwizard.utils.FormUtils.getLayoutParams;

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

        JSONObject requiredObject = jsonObject.optJSONObject("v_required");
        if (requiredObject != null) {
            String requiredValue = requiredObject.getString("value");
            if (!TextUtils.isEmpty(requiredValue)) {
                imageView.setTag(R.id.v_required, requiredValue);
                imageView.setTag(R.id.error, requiredObject.optString("err"));
            }
        }

        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(getLayoutParams(MATCH_PARENT, dpToPixels(context, 200), 0, 0, 0, (int) context
                .getResources().getDimension(R.dimen.default_bottom_margin)));
        String imagePath = jsonObject.optString("value");
        if (!TextUtils.isEmpty(imagePath)) {
            imageView.setTag(R.id.imagePath, imagePath);
            imageView.setImageBitmap(ImageUtils.loadBitmapFromFile(imagePath, ImageUtils.getDeviceWidth(context), dpToPixels(context, 200)));
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

    public static ValidationStatus validate(ImageView imageView) {
        if (!(imageView.getTag(R.id.v_required) instanceof String) || !(imageView.getTag(R.id.error) instanceof String)) {
            return new ValidationStatus(true, null);
        }
        Boolean isRequired = Boolean.valueOf((String) imageView.getTag(R.id.v_required));
        if (!isRequired) {
            return new ValidationStatus(true, null);
        }
        Object path = imageView.getTag(R.id.imagePath);
        if (path instanceof String && !TextUtils.isEmpty((String) path)) {
            return new ValidationStatus(true, null);
        }
        return new ValidationStatus(false, (String) imageView.getTag(R.id.error));
    }
}
