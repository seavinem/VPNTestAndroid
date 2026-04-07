package com.org.database.di

import android.content.Context
import androidx.room.Room
import com.org.database.dao.VpnDao
import com.org.database.db.VpnDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class VpnDatabaseModule {

    @Provides
    @Singleton
    fun provideDataBase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        VpnDatabase::class.java, VPN_DB_NAME
    )
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()

    @Provides
    @Singleton
    fun provideVpnDao(db: VpnDatabase): VpnDao = db.vpnDao()

    private companion object {
        const val VPN_DB_NAME = "vpn_db"
    }
}
