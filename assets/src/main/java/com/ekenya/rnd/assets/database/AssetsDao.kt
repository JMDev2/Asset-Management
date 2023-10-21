package com.ekenya.rnd.assets.database

import androidx.room.Dao
import androidx.room.Insert
import com.ekenya.rnd.baseapp.model.Assets

@Dao
interface AssetsDao {
    @Insert
    suspend fun addAsset(assets: Assets)
}