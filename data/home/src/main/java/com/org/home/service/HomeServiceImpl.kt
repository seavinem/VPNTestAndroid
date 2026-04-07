package com.org.home.service

import com.org.database.dao.VpnDao
import com.org.database.model.CountryEntity
import com.org.network.model.CountryDto
import com.org.network.service.VpnWebService
import javax.inject.Inject

class HomeServiceImpl @Inject constructor(
    private val vpnWebService: VpnWebService,
    private val vpnDao: VpnDao
) : HomeService {

    override suspend fun getAvailableCountries(): List<CountryDto> = vpnWebService.getAvailableCountries()

    override suspend fun getCachedCountries(): List<CountryEntity> = vpnDao.getCountries()

    override suspend fun cacheCountries(countries: List<CountryEntity>) = vpnDao.insertCountries(countries)
}
