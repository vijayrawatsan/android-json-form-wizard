package com.vijay.jsonwizard.widgets;

import android.content.Context;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rengwuxian.materialedittext.validation.RegexpValidator;
import com.rey.material.util.ViewUtil;
import com.vijay.jsonwizard.R;
import com.vijay.jsonwizard.customviews.GenericTextWatcher;
import com.vijay.jsonwizard.interfaces.CommonListener;
import com.vijay.jsonwizard.interfaces.FormWidgetFactory;
import com.vijay.jsonwizard.utils.ValidationStatus;
import com.vijay.jsonwizard.validators.edittext.MaxLengthValidator;
import com.vijay.jsonwizard.validators.edittext.MinLengthValidator;
import com.vijay.jsonwizard.validators.edittext.RequiredValidator;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 24-05-2015.
 */
public class EditTextFactory implements FormWidgetFactory {

    public static final int MIN_LENGTH = 0;
    public static final int MAX_LENGTH = 100;
    @Override
    public List<View> getViewsFromJson(String stepName, Context context, JSONObject jsonObject, CommonListener listener) throws Exception {
        int minLength = MIN_LENGTH;
        int maxLength= MAX_LENGTH;
        List<View> views = new ArrayList<>(1);
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

        //add validators
        JSONObject requiredObject = jsonObject.optJSONObject("v_required");
        if(requiredObject != null) {
            String requiredValue = requiredObject.getString("value");
            if (!TextUtils.isEmpty(requiredValue)) {
                if (Boolean.TRUE.toString().equalsIgnoreCase(requiredValue)) {
                    editText.addValidator(new RequiredValidator(requiredObject.getString("err")));
                }
            }
        }

        JSONObject minLengthObject = jsonObject.optJSONObject("v_min_length");
        if(minLengthObject != null) {
            String minLengthValue = minLengthObject.optString("value");
            if (!TextUtils.isEmpty(minLengthValue)) {
                minLength = Integer.parseInt(minLengthValue);
                editText.addValidator(new MinLengthValidator(minLengthObject.getString("err"), Integer.parseInt(minLengthValue)));
            }
        }

        JSONObject maxLengthObject = jsonObject.optJSONObject("v_max_length");
        if(maxLengthObject != null) {
            String maxLengthValue = maxLengthObject.optString("value");
            if (!TextUtils.isEmpty(maxLengthValue)) {
                maxLength = Integer.parseInt(maxLengthValue);
                editText.addValidator(new MaxLengthValidator(maxLengthObject.getString("err"), Integer.parseInt(maxLengthValue)));
            }
        }

        editText.setMaxCharacters(maxLength);
        editText.setMinCharacters(minLength);

        JSONObject regexObject = jsonObject.optJSONObject("v_regex");
        if(regexObject != null) {
            String regexValue = regexObject.optString("value");
            if (!TextUtils.isEmpty(regexValue)) {
                editText.addValidator(new RegexpValidator(regexObject.getString("err"), regexValue));
            }
        }

        JSONObject emailObject = jsonObject.optJSONObject("v_email");
        if(emailObject != null) {
            String emailValue = emailObject.optString("value");
            if (!TextUtils.isEmpty(emailValue)) {
                if (Boolean.TRUE.toString().equalsIgnoreCase(emailValue)) {
                    editText.addValidator(new RegexpValidator(emailObject.getString("err"), android.util.Patterns.EMAIL_ADDRESS.toString()));
                }
            }
        }

        JSONObject urlObject = jsonObject.optJSONObject("v_url");
        if(urlObject != null) {
            String urlValue = urlObject.optString("value");
            if (!TextUtils.isEmpty(urlValue)) {
                if (Boolean.TRUE.toString().equalsIgnoreCase(urlValue)) {
                    editText.addValidator(new RegexpValidator(urlObject.getString("err"), Patterns.WEB_URL.toString()));
                }
            }
        }

        JSONObject numericObject = jsonObject.optJSONObject("v_numeric");
        if(numericObject != null) {
            String numericValue = numericObject.optString("value");
            if (!TextUtils.isEmpty(numericValue)) {
                if (Boolean.TRUE.toString().equalsIgnoreCase(numericValue)) {
                    editText.addValidator(new RegexpValidator(numericObject.getString("err"), "[0-9]+"));
                }
            }
        }

        // edit type check
        String editType = jsonObject.optString("edit_type");
        if (!TextUtils.isEmpty(editType)) {
            if (editType.equals("number")) {
                editText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            }
        }

        editText.addTextChangedListener(new GenericTextWatcher(stepName, editText));
        views.add(editText);
        return views;
    }

    public static ValidationStatus validate(MaterialEditText editText) {
        boolean validate = editText.validate();
        if(!validate) {
            return new ValidationStatus(false, editText.getError().toString());
        }
        return new ValidationStatus(true, null);
    }

}
