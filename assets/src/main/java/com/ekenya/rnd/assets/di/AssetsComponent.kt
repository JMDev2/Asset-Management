package com.ekenya.rnd.assets.di

import com.ekenya.rnd.assets.di.injectables.AssetsActivityModule
import com.ekenya.rnd.assets.di.injectables.AssetsFragmentModule
import com.ekenya.rnd.baseapp.di.AppComponent
import com.ekenya.rnd.baseapp.di.ModuleScope
import com.ekenya.rnd.baseapp.di.injectables.ViewModelModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@ModuleScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        AssetsActivityModule::class,
        AssetsFragmentModule::class,
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
       // RoomModule::class

    ]
)
interface AssetsComponent{
    fun inject(injector: AssetsInjector)
}