package com.vijay.jsonwizard.viewstates;

import android.os.Parcel;

import com.vijay.jsonwizard.mvp.ViewState;

/**
 * Created by vijay on 5/14/15.
 */
public class JsonFormFragmentViewState extends ViewState implements android.os.Parcelable {
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public JsonFormFragmentViewState() {
    }

    private JsonFormFragmentViewState(Parcel in) {
        super(in);
    }

    public static final Creator<JsonFormFragmentViewState> CREATOR = new Creator<JsonFormFragmentViewState>() {
                                                                       public JsonFormFragmentViewState createFromParcel(
                                                                               Parcel source) {
                                                                           return new JsonFormFragmentViewState(source);
                                                                       }

                                                                       public JsonFormFragmentViewState[] newArray(
                                                                               int size) {
                                                                           return new JsonFormFragmentViewState[size];
                                                                       }
                                                                   };
}
