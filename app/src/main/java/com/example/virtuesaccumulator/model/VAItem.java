package com.example.virtuesaccumulator.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class VAItem implements Parcelable {
    public int id;
    public int resId;
    public int cost;
    public boolean isAcquired;
    public boolean isEquipped;
    public String name;

    protected VAItem(Parcel in) {
        id = in.readInt();
        resId = in.readInt();
        cost = in.readInt();
        name = in.readString();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            isAcquired = in.readBoolean();
            isEquipped = in.readBoolean();
        }
    }

    protected VAItem(int id, int resId, int cost, String name, boolean isAcquired, boolean isEquipped) {
        this.id = id;
        this.resId = resId;
        this.cost = cost;
        this.name = name;
        this.isAcquired = isAcquired;
        this.isEquipped = isEquipped;
    }

    protected VAItem() {}

    public static final Creator<VAItem> CREATOR = new Creator<VAItem>() {
        @Override
        public VAItem createFromParcel(Parcel in) {
            return new VAItem(in);
        }

        @Override
        public VAItem[] newArray(int size) {
            return new VAItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(resId);
        dest.writeInt(cost);
        dest.writeString(name);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dest.writeBoolean(isAcquired);
            dest.writeBoolean(isEquipped);
        }
    }
}
