package com.ekenya.rnd.assets.database

import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.baseapp.model.Profile
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val profileDao: ProfileDao) {

    val userProfile: Flow<Profile> = profileDao.getUserProfile()

    //saving
    suspend fun addUser(profile: Profile): Boolean{
        val existingProfile = profileDao.getProfileByEmail(profile.email)
        return if (existingProfile == null){
            profileDao.saveUser(profile)
            true
        }else{
            false
        }

    }

    // Function to retrieve a user by ID from the local database
    suspend fun getUserByEmail(profileEmail: String): Profile? {
        return profileDao.getProfileByEmail(profileEmail)
    }

    //update
    suspend fun updateProfileDetails(name: String, country: String, city: String,image: ByteArray, email: String) {
        profileDao.updateProfile(name, country, city,image, email)
    }
}