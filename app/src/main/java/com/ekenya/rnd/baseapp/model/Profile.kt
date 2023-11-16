package com.ekenya.rnd.baseapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String?,
    val country: String,
    val city: String,
    val email: String
) : Parcelable