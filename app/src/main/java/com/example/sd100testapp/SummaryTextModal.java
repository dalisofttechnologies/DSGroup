package com.example.sd100testapp;

import android.os.Parcel;
import android.os.Parcelable;

public class SummaryTextModal {

    String heading;
    String content;

    public SummaryTextModal(String heading, String content) {
        this.heading = heading;
        this.content = content;
    }

    protected SummaryTextModal(Parcel in) {
        heading = in.readString();
        content = in.readString();
    }

    public static final Parcelable.Creator<SummaryTextModal> CREATOR = new Parcelable.Creator<SummaryTextModal>() {
        @Override
        public SummaryTextModal createFromParcel(Parcel in) {
            return new SummaryTextModal(in);
        }

        @Override
        public SummaryTextModal[] newArray(int size) {
            return new SummaryTextModal[size];
        }
    };
//
//    @Override public int describeContents() { return 0; }
//
//    @Override public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(heading);
//        parcel.writeString(content); }

    @Override
    public String toString() {
        return content;
    }


}
