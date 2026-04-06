package com.org.network.di

import com.org.network.service.VpnWebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkServicesModule {

    @Provides
    @Singleton
    fun provideVpnWebService(retrofit: Retrofit): VpnWebService =
        retrofit.create(VpnWebService::class.java)
}
