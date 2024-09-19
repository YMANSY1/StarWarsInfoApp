package com.example.apiapplication

import android.os.Parcel
import android.os.Parcelable

data class ResultSW(
    val birth_year: String,
    val created: String,
    val edited: String,
    val eyeColor: String,
    val films: List<String>,
    val gender: String,
    val hair_color: String,
    val height: String,
    val homeworld: String,
    val mass: String,
    val name: String,
    val skin_color: String,
    val species: List<String>,
    val starships: List<String>,
    val url: String,
    val vehicles: List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.createStringArrayList() ?: arrayListOf(),
        parcel.readString() ?: "",
        parcel.createStringArrayList() ?: arrayListOf()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(birth_year)
        parcel.writeString(created)
        parcel.writeString(edited)
        parcel.writeString(eyeColor)
        parcel.writeStringList(films)
        parcel.writeString(gender)
        parcel.writeString(hair_color)
        parcel.writeString(height)
        parcel.writeString(homeworld)
        parcel.writeString(mass)
        parcel.writeString(name)
        parcel.writeString(skin_color)
        parcel.writeStringList(species)
        parcel.writeStringList(starships)
        parcel.writeString(url)
        parcel.writeStringList(vehicles)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultSW> {
        override fun createFromParcel(parcel: Parcel): ResultSW {
            return ResultSW(parcel)
        }

        override fun newArray(size: Int): Array<ResultSW?> {
            return arrayOfNulls(size)
        }
    }
}