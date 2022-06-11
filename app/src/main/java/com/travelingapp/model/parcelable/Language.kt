package com.travelingapp.model.parcelable

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
data class Language(val id: String, val lang: String) : Parcelable
