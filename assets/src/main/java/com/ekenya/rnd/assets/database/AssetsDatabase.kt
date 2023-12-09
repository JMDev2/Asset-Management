package com.ekenya.rnd.assets.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ekenya.rnd.baseapp.model.Assets

@Database(entities = [Assets::class], version = 2, exportSchema = false)
abstract class AssetsDatabase : RoomDatabase() {

    abstract fun assetsDao(): AssetsDao

    companion object{
        private var INSTANCE : AssetsDatabase? = null

        fun getInstance(context: Context): AssetsDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AssetsDatabase::class.java,
                        "newassets"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}