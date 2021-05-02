package com.example.pokeapp.models

import android.os.Parcel
import android.os.Parcelable

data class Pokemon(val name: String, val imageURL: String, val type: String, val weakness: String,
                   val description: String, val evolution1: String,val evolution1imageURL: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString() ?: "") {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(imageURL)
        parcel.writeString(type)
        parcel.writeString(weakness)
        parcel.writeString(description)
        parcel.writeString(evolution1)
        parcel.writeString(evolution1imageURL)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pokemon> {
        override fun createFromParcel(parcel: Parcel): Pokemon {
            return Pokemon(parcel)
        }

        override fun newArray(size: Int): Array<Pokemon?> {
            return arrayOfNulls(size)
        }
    }
}
