package com.example.ntor.presentation

import android.os.Parcel
import android.os.Parcelable

class RunParcelable() : Parcelable {
    constructor(parcel: Parcel) : this() {
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<RunParcelable> {
        override fun createFromParcel(parcel: Parcel): RunParcelable {
            return RunParcelable(parcel)
        }

        override fun newArray(size: Int): Array<RunParcelable?> {
            return arrayOfNulls(size)
        }
    }
}