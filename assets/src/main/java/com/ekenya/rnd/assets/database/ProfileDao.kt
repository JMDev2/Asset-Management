package com.ekenya.rnd.assets.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ekenya.rnd.baseapp.model.Assets
import com.ekenya.rnd.baseapp.model.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Insert
    suspend fun saveUser(profile: Profile)

    //get user profile by id
    @Query("SELECT * FROM profile WHERE email = :profileEmail")
    suspend fun getProfileByEmail(profileEmail: String): Profile?

    //get user profile
    @Query("SELECT * FROM profile")
    fun getUserProfile():
            Flow<Profile>
}