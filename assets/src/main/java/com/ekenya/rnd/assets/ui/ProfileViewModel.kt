package com.ekenya.rnd.assets.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekenya.rnd.assets.database.ProfileRepository
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.baseapp.model.Profile
import com.ekenya.rnd.common.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val repository: ProfileRepository): ViewModel() {

    val _user: Flow<Resource<Profile>> = repository.userProfile.map {
        Resource.success(it)
    }
    //saving user
    fun saveUser(profile: Profile) {
        viewModelScope.launch {
            repository.addUser(profile)
        }
    }

    // Function to retrieve a user by ID
//    fun getUserById(profileEmail: String) {
//        viewModelScope.launch {
//            repository.getUserById(profileEmail)
//        }
//    }

    suspend fun checkUserExists(email: String): Boolean {
        return repository.getUserByEmail(email) != null
    }

    //update
    suspend fun updateProfileDetails(name: String, country: String, city: String,image: ByteArray, email: String) {
        repository.updateProfileDetails(name, country, city,image, email)
    }

}