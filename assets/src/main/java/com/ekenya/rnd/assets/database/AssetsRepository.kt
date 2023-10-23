package com.ekenya.rnd.assets.database

import com.ekenya.rnd.baseapp.model.Assets
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AssetsRepository @Inject constructor(private val assetsDao: AssetsDao) {

    val allAssets: Flow<List<Assets>> = assetsDao.getAllAssets()

    //saving
    suspend fun addAsset(assets: Assets){
        assetsDao.addAsset(assets)
    }
}