package com.firman.submissionandroidpemula

import android.os.Parcel
import android.os.Parcelable

data class Character(
    val name: String,
    val description: String,
    val photo: Int,
    val backgroundIndex: Int,
    val friendIndices: IntArray,
    val element: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.createIntArray() ?: intArrayOf(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeInt(photo)
        parcel.writeInt(backgroundIndex)
        parcel.writeIntArray(friendIndices)
        parcel.writeString(element)

    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<Character> {
        override fun createFromParcel(parcel: Parcel): Character {
            return Character(parcel)
        }

        override fun newArray(size: Int): Array<Character?> {
            return arrayOfNulls(size)
        }
    }
}
