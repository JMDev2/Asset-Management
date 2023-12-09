package com.ekenya.rnd.common.utils

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import javax.inject.Inject

object SharedPreferences {


    fun saveCountToSharedPreferences(context: Context, count: Int) {
        // You can modify the key or preferences name as needed
        val key = "totalAssetsCount"
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(key, count)
        editor.apply()
    }

    fun getSavedCountFromSharedPreferences(context: Context): Int {
        // You can modify the key or preferences name as needed
        val key = "totalAssetsCount"
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, 0) // 0 is the default value if the key is not found
    }

}