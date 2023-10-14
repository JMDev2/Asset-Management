package com.ekenya.rnd.baseapp.di;

import android.app.Application;


import com.ekenya.rnd.baseapp.AssetApp;
import com.ekenya.rnd.baseapp.di.injectables.FragmentModule;
import com.ekenya.rnd.common.repo.SampleRepository;
import com.ekenya.rnd.baseapp.di.injectables.ActivityModule;
import com.ekenya.rnd.baseapp.di.injectables.ViewModelModule;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;

import org.jetbrains.annotations.NotNull;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,  AppModule.class,  ViewModelModule.class,
                ActivityModule.class, FragmentModule.class} )
public interface AppComponent extends AndroidInjector<AssetApp> {
    @NotNull
    SampleRepository sampleRepository();

    @NotNull
    Application getApp();

    @Component.Builder
    public abstract static class Builder extends AndroidInjector.Builder<AssetApp> {
        @NotNull
        public abstract AppComponent build();
    }
}
