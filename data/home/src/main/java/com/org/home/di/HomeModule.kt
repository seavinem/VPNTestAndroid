package com.org.home.di

import com.org.home.repository.HomeRepository
import com.org.home.repository.HomeRepositoryImpl
import com.org.home.service.HomeService
import com.org.home.service.HomeServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HomeModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    @Singleton
    abstract fun bindHomeService(homeServiceImpl: HomeServiceImpl): HomeService
}
