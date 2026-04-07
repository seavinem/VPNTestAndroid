package com.org.home.service

import com.org.database.model.CountryEntity
import com.org.network.model.CountryDto

interface HomeService {

    suspend fun getAvailableCountries(): List<CountryDto>

    suspend fun getCachedCountries(): List<CountryEntity>

    suspend fun cacheCountries(countries: List<CountryEntity>)
}
