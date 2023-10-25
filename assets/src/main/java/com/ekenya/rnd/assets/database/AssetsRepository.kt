package com.ekenya.rnd.assets.database

import com.ekenya.rnd.baseapp.model.Assets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AssetsRepository @Inject constructor(private val assetsDao: AssetsDao) {

    val allAssets: Flow<List<Assets>> = assetsDao.getAllAssets()

    //saving
    suspend fun addAsset(assets: Assets){
        assetsDao.addAsset(assets)
    }

    fun getAssets(assets: Assets): Assets {
        return assets
    }


    //checking if the erial number exists
    suspend fun isSerialNumberUnique(serialNumber: String): Boolean {
        return withContext(Dispatchers.IO) {
            val existingAsset = assetsDao.getAssetBySerialNumber(serialNumber)
            existingAsset == null
        }
    }
}