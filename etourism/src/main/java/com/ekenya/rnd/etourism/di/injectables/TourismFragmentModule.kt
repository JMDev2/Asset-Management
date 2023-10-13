package com.ekenya.rnd.etourism.di.injectables

import androidx.lifecycle.ViewModel
import com.ekenya.rnd.baseapp.di.ViewModelKey
import com.ekenya.rnd.etourism.ui.dashboard.DashboardFragment
import com.ekenya.rnd.etourism.ui.dashboard.DashboardViewModel
import com.ekenya.rnd.etourism.ui.home.HomeFragment
import com.ekenya.rnd.etourism.ui.home.HomeViewModel
import com.ekenya.rnd.etourism.ui.login.LoginDialogFragment
import com.ekenya.rnd.etourism.ui.login.LoginViewModel
import com.ekenya.rnd.etourism.ui.notifications.NotificationsFragment
import com.ekenya.rnd.etourism.ui.notifications.NotificationsViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class TourismFragmentModule {

    @ContributesAndroidInjector(modules = [TourismLoginDialogFragmentModule::class])
    abstract fun contributeLoginDialogFragment(): LoginDialogFragment

    @Module
    abstract class TourismLoginDialogFragmentModule {
        @Binds
        @IntoMap
        @ViewModelKey(LoginViewModel::class)
        abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
    }


    @ContributesAndroidInjector(modules = [TourismHomeFragmentModule::class])
    abstract fun contributeHomeFragment(): HomeFragment

    @Module
    abstract class TourismHomeFragmentModule {
        @Binds
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
    }

    @ContributesAndroidInjector(modules = [TourismNotificationsFragmentModule::class])
    abstract fun contributeNotificationsFragment(): NotificationsFragment

    @Module
    abstract class TourismNotificationsFragmentModule {
        @Binds
        @IntoMap
        @ViewModelKey(NotificationsViewModel::class)
        abstract fun bindHomeViewModel(viewModel: NotificationsViewModel): ViewModel
    }

    @ContributesAndroidInjector(modules = [TourismDashboardFragmentModule::class])
    abstract fun contributeDashboardFragment(): DashboardFragment

    @Module
    abstract class TourismDashboardFragmentModule {
        @Binds
        @IntoMap
        @ViewModelKey(DashboardViewModel::class)
        abstract fun bindHomeViewModel(viewModel: DashboardViewModel): ViewModel
    }
    //LIST THE OTHER INJECTABLE FRAGMENTS AS ABOVE
}
