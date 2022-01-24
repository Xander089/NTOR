package com.example.ntor.presentation.utils

import android.os.Parcel
import android.os.Parcelable

class RunParcelable(
    val distance: Double = 0.0,
    val time: Int = 0,
    val date: Long = 0,
    val pacing: Double = 0.0,
    val calories: Double = 0.0,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readDouble(),
        parcel.readDouble(),
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {

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