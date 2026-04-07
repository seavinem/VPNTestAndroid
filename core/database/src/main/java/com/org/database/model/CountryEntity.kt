package com.org.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey
    val countryName: String,
    val capital: String,
    val flagUrl: String
)
