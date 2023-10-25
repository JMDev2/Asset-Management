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

    val allAssets: Flow<Resource<List<Assets>>> = repository.allAssets.map {
        Resource.success(it)
    }

    //saving assets
    fun saveAsset(assets: Assets){
        viewModelScope.launch {
            repository.addAsset(assets)
        }
    }


    //checking if the serial number exists
    suspend fun isSerialNumberUnique(serialNumber: String): Boolean {
        return repository.isSerialNumberUnique(serialNumber)
    }



}