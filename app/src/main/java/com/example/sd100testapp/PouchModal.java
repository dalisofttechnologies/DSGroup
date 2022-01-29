package com.example.sd100testapp;

import android.os.Parcel;
import android.os.Parcelable;

public class PouchModal implements Parcelable {

    String heading;
    String entry;

    public PouchModal(String heading, String entry) {
        this.heading = heading;
        this.entry = entry;
    }

    protected PouchModal(Parcel in) {
        heading = in.readString();
        entry = in.readString();
    }

    public static final Creator<PouchModal> CREATOR = new Creator<PouchModal>() {
        @Override
        public PouchModal createFromParcel(Parcel in) {
            return new PouchModal(in);
        }

        @Override
        public PouchModal[] newArray(int size) {
            return new PouchModal[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(heading);
        parcel.writeString(entry);
    }

    @Override
    public String toString() {
        return entry;
    }

}