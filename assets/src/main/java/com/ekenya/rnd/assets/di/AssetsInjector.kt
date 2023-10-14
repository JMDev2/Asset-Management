package com.ekenya.rnd.assets.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.ekenya.rnd.assets.di.injectables.RoomModule
import com.ekenya.rnd.baseapp.AssetApp
import com.ekenya.rnd.baseapp.di.BaseModuleInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class AssetsInjector : BaseModuleInjector {

    @Inject
    lateinit var activityInjector : DispatchingAndroidInjector<Activity>

    @Inject
    lateinit var fragmentInjector : DispatchingAndroidInjector<Fragment>


    override fun inject(app: AssetApp) {
        DaggerAssetsComponent.builder()
            .appComponent(app.appComponent)
           // .roomModule(RoomModule(app.applicationContext))
            .build()
            .inject(this)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return activityInjector
    }

    override fun fragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return fragmentInjector
    }
}