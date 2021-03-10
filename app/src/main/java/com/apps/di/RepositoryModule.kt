package com.apps.di

import com.apps.data.remote.RepositorySingleton
import com.apps.data.remote.RetrofitInstance
import com.apps.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {
    @Provides
    @ActivityRetainedScoped
    fun provideMainRepository(retrofit: RetrofitInstance, network: NetworkHelper): RepositorySingleton {
        return RepositorySingleton(retrofit, network)
    }
}