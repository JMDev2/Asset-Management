package com.ekenya.rnd.assets.database

import com.ekenya.rnd.baseapp.model.Assets
import javax.inject.Inject

class AssetsRepository @Inject constructor(private val assetsDao: AssetsDao) {


    //saving
    suspend fun addAsset(assets: Assets){
        assetsDao.addAsset(assets)
    }
}