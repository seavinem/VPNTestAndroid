package com.org.home.repository

import com.org.home.mapper.CountryDtoToDomainMapper
import com.org.home.model.CountryDomain
import com.org.network.service.VpnWebService
import javax.inject.Inject

private val featuredServerNames = listOf(
    "Germany",
    "United States",
    "Japan",
    "Netherlands"
)

class HomeRepositoryImpl @Inject constructor(
    private val vpnWebService: VpnWebService,
    private val countryDtoToDomainMapper: CountryDtoToDomainMapper
) : HomeRepository {

    override suspend fun getAvailableCountries(): List<CountryDomain> {
        val countriesByName = vpnWebService
            .getAvailableCountries()
            .map { countryDtoToDomainMapper(it) }
            .filter { it.countryName.isNotBlank() }
            .distinctBy { it.countryName }
            .associateBy { it.countryName }

        val featuredCountries = featuredServerNames.map { featuredCountryName ->
            countriesByName[featuredCountryName] ?: CountryDomain(
                countryName = featuredCountryName,
                capital = "",
                flagUrl = ""
            )
        }

        val featuredServerNamesSet = featuredServerNames.toSet()
        val remainingCountries = countriesByName.values
            .filterNot { country -> country.countryName in featuredServerNamesSet }
            .sortedBy { it.countryName.lowercase() }

        return featuredCountries + remainingCountries
    }
}
