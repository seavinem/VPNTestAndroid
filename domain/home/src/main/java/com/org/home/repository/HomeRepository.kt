package com.org.home.repository

import com.org.home.model.CountryDomain

interface HomeRepository {

    suspend fun getAvailableCountries() : List<CountryDomain>
}
