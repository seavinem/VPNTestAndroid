package com.org.home.repository

import com.org.home.mapper.CountryDtoToDomainMapper
import com.org.home.model.CountryDomain
import com.org.network.service.VpnWebService
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val vpnWebService: VpnWebService,
    private val countryDtoToDomainMapper: CountryDtoToDomainMapper
) : HomeRepository {

    override suspend fun getAvailableCountries(): List<CountryDomain> = vpnWebService
        .getAvailableCountries()
            .map { countryDtoToDomainMapper(it) }
            .filter { it.countryName.isNotBlank() }
            .distinctBy { it.countryName }
            .sortedBy { it.countryName.lowercase() }
}
