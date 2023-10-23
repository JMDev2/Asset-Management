package com.ekenya.rnd.common.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import javax.inject.Inject

object SharedPreferences {


    // Function to set the phone number in SharedPreferences
    fun setRingTone(context: Context, phoneNumber: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("phoneNumber", phoneNumber)
        editor.apply()
    }

    // Function to get the phone number from SharedPreferences
    fun getRingTone(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("phoneNumber", "")
    }


    // Function to set the date and time "from" in SharedPreferences
    fun setDateTimeFrom(context: Context, dateTimeFrom: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("dateTimeFrom", dateTimeFrom)
        editor.apply()
    }

    // Function to get the date and time "from" from SharedPreferences
    fun getDateTimeFrom(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("dateTimeFrom", null)
    }

    // Function to set the date and time "to" in SharedPreferences
    fun setDateTimeTo(context: Context, dateTimeTo: String) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("dateTimeTo", dateTimeTo)
        editor.apply()
    }

    // Function to get the date and time "to" from SharedPreferences
    fun getDateTimeTo(context: Context): String? {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getString("dateTimeTo", null)
    }
}