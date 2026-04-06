package com.org.connectivity.di

import com.org.connectivity.monitor.ConnectivityNetworkMonitor
import com.org.connectivity.monitor.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class NetworkMonitorModule {

    @Binds
    @Singleton
    abstract fun bindNetworkMonitor(
        connectivityNetworkMonitor: ConnectivityNetworkMonitor
    ): NetworkMonitor
}
