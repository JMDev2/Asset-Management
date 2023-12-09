package com.ekenya.rnd.assets.di.injectables

import android.content.Context
import com.ekenya.rnd.assets.database.AssetsDao
import com.ekenya.rnd.assets.database.AssetsDatabase
import com.ekenya.rnd.assets.database.ProfileDao
import com.ekenya.rnd.assets.database.ProfileDatabase
import com.ekenya.rnd.baseapp.di.ModuleScope
import dagger.Module
import dagger.Provides


@Module
class RoomModule (private val context: Context) {
    // Add Room database-related providers here
    @Provides
    @ModuleScope
    fun provideContext(): Context {
        return context
    }
    @Provides
    @ModuleScope
    fun provideAppDatabase(context: Context): AssetsDatabase {
        return AssetsDatabase.getInstance(context)
    }

    @Provides
    @ModuleScope
    fun provideUserDao(database: AssetsDatabase): AssetsDao {
        return database.assetsDao()
    }

    @Provides
    @ModuleScope
    fun provideProfileDatabase(context: Context): ProfileDatabase {
        return ProfileDatabase.getInstance(context)
    }

    @Provides
    @ModuleScope
    fun provideProfileDao(database: ProfileDatabase): ProfileDao {
        return database.profileDao()
    }
}