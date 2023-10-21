package com.ekenya.rnd.baseapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "assets")
data class Assets(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String?,
    val serial_number: String?,
    val department: String?,
    val city: String?,
    val model: String?,
    val other_attributes: String?,
    val description: String?
)
