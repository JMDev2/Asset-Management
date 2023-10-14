package com.ekenya.rnd.assets.di.injectables

import androidx.lifecycle.ViewModel
import com.ekenya.rnd.assets.MainActivity
import com.ekenya.rnd.assets.ui.AssetViewModel
import com.ekenya.rnd.baseapp.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AssetsActivityModule {
    @ContributesAndroidInjector(modules = [HomeActivityModule::class])
    abstract fun contributeHomeActivity(): MainActivity

    @Module
    abstract class HomeActivityModule{
        @Binds
        @IntoMap
        @ViewModelKey(AssetViewModel::class)
        abstract fun bindAssetViewModel(viewModel: AssetViewModel): ViewModel
    }
}