package com.ekenya.rnd.assets.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekenya.rnd.assets.database.AssetsRepository
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class AssetViewModel @Inject constructor(private val repository: AssetsRepository) : ViewModel() {

    private val _filteredAssets = MutableLiveData<List<Assets>>()
    val filteredAssets: LiveData<List<Assets>> get() = _filteredAssets


    private val _assetsCount = MutableLiveData<Int>()
    val assetsCount: LiveData<Int>
        get() = _assetsCount


    //Display all assets
    val allAssets: Flow<Resource<List<Assets>>> = repository.allAssets.map {
        Resource.success(it)
    }


    //saving assets
    fun saveAsset(assets: Assets) {
        viewModelScope.launch {
            repository.addAsset(assets)
        }
    }


    //get assets count
    fun retrieveAssetsCount() {
        viewModelScope.launch {
            val count = repository.getAssetsCount()
            _assetsCount.value = count
        }
    }

    //get total assets by department
    fun getAssetCountByDepartment(department: String): Flow<Int> {
        return repository.getAssetCountByDepartment(department)
    }

    //delete assets
    fun delete(assets: Assets){
        viewModelScope.launch {
            repository.delete(assets)
        }
    }


    //checking if the serial number exists
    suspend fun isSerialNumberUnique(serialNumber: String): Boolean {
        return repository.isSerialNumberUnique(serialNumber)
    }

    //get IT assets
    suspend fun getITAssets(): Flow<Resource<List<Assets>>> {
        return repository.getAssetsByDepartment("IT")
            .map { assets ->
                if (assets.isEmpty()) {
                    Resource.error("No IT assets found", assets)
                } else {
                    Resource.success(assets)
                }
            }
    }

    //get marketing department assets
    suspend fun getMarketingAssests(): Flow<Resource<List<Assets>>> {
        return repository.getAssetsByDepartment("Marketing")
            .map { assets ->
                if (assets.isEmpty()) {
                    Resource.error("No Marketing assets found", assets)
                } else {
                    Resource.success(assets)
                }
            }
    }

    //get hr assets
    suspend fun getHrAssets(): Flow<Resource<List<Assets>>>{
        return repository.getAssetsByDepartment("Hr")
            .map { assets ->
                if (assets.isEmpty()){
                    Resource.error("No Hr assets found", assets)
                }else{
                   Resource.success(assets)
                }
            }
    }


}