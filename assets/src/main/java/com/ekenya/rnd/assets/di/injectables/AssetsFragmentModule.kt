package com.ekenya.rnd.assets.di.injectables

import androidx.lifecycle.ViewModel
import com.ekenya.rnd.assets.ITAssetsFragment
import com.ekenya.rnd.assets.ui.*
import com.ekenya.rnd.baseapp.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AssetsFragmentModule {

    @Module
    abstract class HomeFragmentModule{
        @Binds
        @IntoMap
        @ViewModelKey(AssetViewModel::class)
        abstract fun bindAssetViewModel(viewModel: AssetViewModel) : ViewModel
    }

//    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
//    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeAllAssetsFragment(): AllAssetsFragment

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeAllCategoriesFragment(): AllCategoriesFragment

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeITAssetsFragment(): ITAssetsFragment

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeMarketingAssetsFragment(): MarketingAssetsFragment

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeHrAssetsFragment(): HrAssetsFragment

    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeAddAssetsFragment(): AddAssetsFragment


    @ContributesAndroidInjector(modules = [HomeFragmentModule::class])
    abstract fun contributeTotalAssetsFragment(): TotalAssetsFragment


    @Module
    abstract class ProfileFragmentModule{
        @Binds
        @IntoMap
        @ViewModelKey(ProfileViewModel::class)
        abstract fun bindProfileViewModel(viewModel: ProfileViewModel) : ViewModel
    }

    @ContributesAndroidInjector(modules = [ProfileFragmentModule::class])
    abstract fun contributeProfileFragment(): ProfileFragment
//
    @ContributesAndroidInjector(modules = [ProfileFragmentModule::class])
    abstract fun contributeProfileHomeFragment(): HomeFragment

    @ContributesAndroidInjector(modules = [ProfileFragmentModule::class])
    abstract fun contributeAssetDetailsFragment(): AssestDetailsFragment

}