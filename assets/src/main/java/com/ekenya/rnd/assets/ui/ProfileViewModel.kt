package com.ekenya.rnd.assets.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ekenya.rnd.assets.database.ProfileRepository
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.baseapp.model.Profile
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val repository: ProfileRepository): ViewModel() {

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

}