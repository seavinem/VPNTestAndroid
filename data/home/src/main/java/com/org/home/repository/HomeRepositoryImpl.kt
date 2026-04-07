package com.org.home.repository

import com.org.home.mapper.CountryDomainToEntityMapper
import com.org.home.mapper.CountryDtoToDomainMapper
import com.org.home.mapper.CountryEntityToDomainMapper
import com.org.home.model.CountryDomain
import com.org.home.service.HomeService
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val countryDtoToDomainMapper: CountryDtoToDomainMapper,
    private val countryEntityToDomainMapper: CountryEntityToDomainMapper,
    private val countryDomainToEntityMapper: CountryDomainToEntityMapper,
    private val homeService: HomeService
) : HomeRepository {

    override suspend fun getAvailableCountries(): List<CountryDomain> {
        val cached = homeService.getCachedCountries()
        if (cached.isNotEmpty()) {
            return cached.map { countryEntityToDomainMapper(it) }
        }

        val countries = fetchAndSortCountries()
        homeService.cacheCountries(countries.map { countryDomainToEntityMapper(it) })
        return countries
    }

    private suspend fun fetchAndSortCountries(): List<CountryDomain> {
        val countriesByName = homeService
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

    private companion object {
        val featuredServerNames = listOf(
            "Germany",
            "United States",
            "Japan",
            "Netherlands"
        )
    }
}
