package com.ekenya.rnd.baseapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "assets")
data class Assets(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String?= null,
    val serial_number: String?= null,
    val department: String?= null,
    val city: String?= null,
    val model: String?= null,
    val other_attributes: String?= null,
    val description: String?= null,
    var image: ByteArray?= null

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createByteArray()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(serial_number)
        parcel.writeString(department)
        parcel.writeString(city)
        parcel.writeString(model)
        parcel.writeString(other_attributes)
        parcel.writeString(description)
        parcel.writeByteArray(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Assets> {
        override fun createFromParcel(parcel: Parcel): Assets {
            return Assets(parcel)
        }

        override fun newArray(size: Int): Array<Assets?> {
            return arrayOfNulls(size)
        }
    }
}