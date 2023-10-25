package com.ekenya.rnd.assets.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ekenya.rnd.baseapp.model.Assets
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetsDao {
    @Insert
    suspend fun addAsset(assets: Assets)

    @Query("SELECT * FROM assets")
    fun getAllAssets():
            Flow<List<Assets>>

    @Query("SELECT * FROM assets WHERE serial_number = :serialNumber")
    suspend fun getAssetBySerialNumber(serialNumber: String): Assets?

}