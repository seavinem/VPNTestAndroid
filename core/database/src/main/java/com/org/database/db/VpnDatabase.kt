package com.org.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.org.database.dao.VpnDao
import com.org.database.model.CountryEntity

@Database(entities = [CountryEntity::class], version = 1)
abstract class VpnDatabase : RoomDatabase() {

    abstract fun vpnDao(): VpnDao
}
