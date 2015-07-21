package com.vijay.jsonwizard.validators.edittext;

import com.rengwuxian.materialedittext.validation.METValidator;

/**
 * Created by vijay.rawat01 on 7/21/15.
 */
public class LengthValidator extends METValidator {

    int minLength = 0;
    int maxLength = Integer.MAX_VALUE;

    public LengthValidator(String errorMessage, int minLength, int maxLength) {
        super(errorMessage);
        this.minLength = minLength;
        this.maxLength = maxLength;
    }

    @Override
    public boolean isValid(CharSequence charSequence, boolean isEmpty) {
        if(!isEmpty) {
            if(charSequence.length() >= minLength && charSequence.length() <= maxLength) {
                return true;
            }
        }
        return false;
    }
}
