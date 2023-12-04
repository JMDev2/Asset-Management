package com.ekenya.rnd.assets.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.common.utils.Resource
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

    //query assets by department
    @Query("SELECT * FROM assets WHERE department = :department")
    fun getAssetsByDepartment(department: String): Flow<List<Assets>>

    @Query("SELECT COUNT(*) FROM assets")
    suspend fun getAssetsCount(): Int

    @Query("SELECT COUNT(*) FROM assets WHERE department = :department")
    fun getAssetCountByDepartment(department: String): Flow<Int>

}