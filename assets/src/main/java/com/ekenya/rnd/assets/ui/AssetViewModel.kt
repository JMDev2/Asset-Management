package com.ekenya.rnd.assets.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekenya.rnd.assets.database.AssetsRepository
import com.ekenya.rnd.baseapp.model.Assets
import kotlinx.coroutines.launch
import javax.inject.Inject

class AssetViewModel @Inject constructor(private val repository: AssetsRepository) : ViewModel() {

    //saving assets
    fun saveAsset(assets: Assets){
        viewModelScope.launch {
            repository.addAsset(assets)
        }
    }
}