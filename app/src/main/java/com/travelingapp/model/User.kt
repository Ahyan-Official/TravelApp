package com.travelingapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val score: Int,
    val userid: String,
    val countryid: String
    ): Parcelable